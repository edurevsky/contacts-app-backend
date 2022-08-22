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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public JWTLoginFilter(JWTUtils jwtUtils, AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Credentials credentials = objectMapper.readValue(request.getInputStream(), Credentials.class);
            var token = new UsernamePasswordAuthenticationToken(credentials.username, credentials.password);
            return authenticationManager.authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException("Authentication Exception");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        ApplicationUser userDetails = ((ApplicationUser) authResult.getPrincipal());
        String token = jwtUtils.generateToken(userDetails.getUsername(), userDetails.getAuthorities());
        String refreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("refreshToken", refreshToken);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), tokenMap);
    }

    private record Credentials(
            String username,
            String password
    ) { }
}
