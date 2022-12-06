package ao.co.celsodesousa.helpDesk.security;

import ao.co.celsodesousa.helpDesk.domain.dto.CredencialDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private  AuthenticationManager authenticationManager;

    private  JwtUtil jwtUtil;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try
        {

            CredencialDTO dto = new ObjectMapper().readValue(request.getInputStream(),CredencialDTO.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(),dto.getSenha(),new ArrayList<>());

            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            return authentication;

        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {


        String userName = ((UserSecurity) authResult.getPrincipal()).getUsername();

        String token = jwtUtil.generateToken(authResult);
        response.setHeader("access-control-expose-headers", "Authorization");
        response.setHeader("Authorization", "Bearer "+token);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {


        System.out.println("TESTE");

        response.setStatus(401);
        response.setContentType("application/json");
        response.getWriter().append(jsonError());

    }

    private String jsonError() {

        long data = new Date().getTime();

        return "{" +
                "\"timestamp\":" +data+
                "\"status\":401" +
                "\"error:\"Nao Autorizado\"" +
                "\"message\":\"Email ou senha invalido\"" +
                "\"path\":\"/login\""+
                "}";
    }
}
