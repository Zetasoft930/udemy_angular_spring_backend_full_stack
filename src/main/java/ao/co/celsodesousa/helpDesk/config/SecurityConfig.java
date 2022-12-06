package ao.co.celsodesousa.helpDesk.config;

import ao.co.celsodesousa.helpDesk.domain.Pessoa;
import ao.co.celsodesousa.helpDesk.domain.dto.CredencialDTO;
import ao.co.celsodesousa.helpDesk.security.JwtAuthenticationFilter;
import ao.co.celsodesousa.helpDesk.security.JwtAuthorizationFilter;
import ao.co.celsodesousa.helpDesk.security.JwtUtil;
import ao.co.celsodesousa.helpDesk.security.RsaKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private  RsaKeyProperties rsaKeys;
    private final String[] PUBLIC_MATCHRS = {"/h2-console/**"};


    @Autowired
    private Environment env;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    private final String DECODING_ERROR_MESSAGE_TEMPLATE="ERRO";


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if(Arrays.asList(env.getActiveProfiles()).contains("test"))
        {
            http.headers().frameOptions().disable();
        }

        http.cors().and()
                .csrf().disable();
        http.addFilter(new JwtAuthenticationFilter(authenticationManager(),jwtUtil));
        http.addFilter(new JwtAuthorizationFilter(authenticationManager(),jwtUtil,userDetailsService));
        http.authorizeRequests()
                        .antMatchers(PUBLIC_MATCHRS).permitAll()
                      /*  .antMatchers("/tecnico").hasAnyRole("ADMIN")
                        .antMatchers("/cliente").hasAnyRole("TECNICO")*/
                        .anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());


    }

    @Bean
    CorsConfigurationSource corsConfigurationSource()
    {

        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;

    }

    @Bean
    JwtDecoder jwtDecoder()
    {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder()
    {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
    @Bean
    public JwtDecoderFactory<String> jwtDecoderFactory() {

        final JwtDecoder decoder = new JwtDecoder() {

            @SneakyThrows
            @Override
            public Jwt decode(String token) throws JwtException {
                JWT jwt = JWTParser.parse(token);
                return createJwt(token, jwt);
            }

            private Jwt createJwt(String token, JWT parsedJwt) {
                try {
                    Map<String, Object> headers = new LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
                    Map<String, Object> claims = parsedJwt.getJWTClaimsSet().getClaims();
                    return Jwt.withTokenValue(token)
                            .headers(h -> h.putAll(headers))
                            .claims(c -> c.putAll(claims))
                            .build();
                } catch (Exception ex) {
                    if (ex.getCause() instanceof ParseException) {
                        throw new JwtException(String.format(DECODING_ERROR_MESSAGE_TEMPLATE, "Malformed payload"));
                    } else {
                        throw new JwtException(String.format(DECODING_ERROR_MESSAGE_TEMPLATE, ex.getMessage()), ex);
                    }
                }
            }
        };
        return context -> decoder;
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
