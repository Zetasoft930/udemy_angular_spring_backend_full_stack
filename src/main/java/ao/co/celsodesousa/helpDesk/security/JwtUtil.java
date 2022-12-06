package ao.co.celsodesousa.helpDesk.security;

import ao.co.celsodesousa.helpDesk.domain.Pessoa;
import ao.co.celsodesousa.helpDesk.domain.dto.CredencialDTO;
import com.fasterxml.jackson.core.json.JsonReadContext;
import com.nimbusds.jose.crypto.impl.RSA_OAEP_256;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;



@Component
public class JwtUtil {

    @Value("${jwt.expiration}")
    private  Long jwtExpiration;

    @Value("${jwt.secret}")
    private  String jwtSecret;
@Autowired
    private  JwtEncoder encoder;
@Autowired
private JwtDecoder jwtDecoder;

    @Autowired
    private  RsaKeyProperties rsaKeys;
@Autowired
private JwtDecoderFactory<String> jwtDecoderFactory;

    public String generateToken(String email)
    {


        Date data  = new Date(System.currentTimeMillis()+jwtExpiration);


        return Jwts.builder().setSubject(email).setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes()).compact();
    }
    public String generateToken(Authentication authentication)
    {
        Instant now = Instant.now();
        System.out.println("teste --- now"+now);
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope",scope)
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();

    }

    public boolean tokenValido(String token) throws Exception {

        token = token.replaceAll("Bearer"," ").trim();





        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        String payload1 = new String(decoder.decode(chunks[2]));



        JSONParser parser = new JSONParser(payload);
        LinkedHashMap json = (LinkedHashMap) parser.parse();
       Long datetime = Long.parseLong( String.valueOf(json.get("exp")));


        Instant now = Instant.now();
        Instant instant = Instant.ofEpochSecond( datetime );
        ZoneId zoneId = ZoneId.of( "Africa/Luanda" );
        ZonedDateTime zdt = ZonedDateTime.ofInstant( instant , zoneId );



       /* SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(jwtSecret.getBytes(), sa.getJcaName());

        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];


        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa,secretKeySpec );

        System.out.println("tokenWithoutSignature >> "+tokenWithoutSignature);
        System.out.println("validator >> "+validator);
        if (!validator.isValid(token, signature)) {
            throw new Exception("Could not verify JWT token integrity!");
        }

*/
        boolean flag = now.isAfter(instant);
        System.out.println("now >> "+now);
        System.out.println("instant >> "+instant);
        System.out.println("flag >> "+flag);
        return true;

    }
    private Claims getClaims(String token) {

        try {


            return Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();

        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    public JWT decode(String token) throws JwtException {


        JWT jwt = null;
        try {
            jwt = JWTParser.parse(token);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return null;

    }
    public String getUsername(String token) {

        token = token.replaceAll("Bearer"," ").trim();

        String[] chunks = token.split("\\.");

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        JSONParser parser = new JSONParser(payload);
        LinkedHashMap json = null;
        try {
            json = (LinkedHashMap) parser.parse();
            return String.valueOf(json.get("sub"));
        } catch (org.apache.tomcat.util.json.ParseException e) {
            throw new RuntimeException(e);
        }


    }

    public boolean validateToken()
    {
        return false;
    }

    private Jwt createJwt(String token) {
        try {

            JWT parsedJwt = this.decode(token);

            Map<String, Object> headers = new LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
            Map<String, Object> claims = parsedJwt.getJWTClaimsSet().getClaims();
            Jwt.Builder finalJwt = Jwt.withTokenValue(token)
                    .headers(h -> h.putAll(headers))
                    .claims(c -> c.putAll(claims));
            finalJwt.expiresAt(((Date) claims.get("exp")).toInstant());
            return finalJwt.build();
        } catch (Exception ex) {
            if (ex.getCause() instanceof ParseException) {
                throw new JwtException("There is a problem parsing the JWT: " + ex.getMessage());
            } else {
                throw new JwtException("There is a problem decoding the JWT: " + ex.getMessage());
            }
        }
    }
}
