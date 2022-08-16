package dev.edurevsky.contacts.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.edurevsky.contacts.models.ApplicationUser;
import dev.edurevsky.contacts.utils.JWTUtils;
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

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public JWTLoginFilter(JWTUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Credentials credentials = new ObjectMapper().readValue(request.getInputStream(), Credentials.class);
            var token = new UsernamePasswordAuthenticationToken(credentials.username, credentials.password);
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException("Authentication Exception");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ApplicationUser userDetails = ((ApplicationUser) authResult.getDetails());
        String token = jwtUtils.generateToken(userDetails.getUsername(), userDetails.getAuthorities());
        response.addHeader("Authorization", "Bearer " + token);
    }

    private record Credentials(
            String username,
            String password
    ) { }
}
