package ao.co.celsodesousa.helpDesk.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,JwtUtil jwtUtil,UserDetailsService userDetailsService) {
        super(authenticationManager);

        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        if(header != null)
        {
            if(!header.isBlank() && header.startsWith("Bearer "))
            {

                UsernamePasswordAuthenticationToken authenticationToken = null;
                try {
                    authenticationToken = getAuthentication(header.replaceAll("Bearer ","").trim());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if(authenticationToken != null)
                {
                    //SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    SecurityContext ctx = SecurityContextHolder.createEmptyContext();
                    SecurityContextHolder.setContext(ctx);
                    ctx.setAuthentication(authenticationToken);
                }

                chain.doFilter(request, response);

            }
        }


    }
    private UsernamePasswordAuthenticationToken getAuthentication(String token) throws Exception {

        if(jwtUtil.tokenValido(token)) {

            String username = jwtUtil.getUsername(token);

            UserDetails details = this.userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(details.getUsername(), null,details.getAuthorities());

        }

        return null;

    }
}
