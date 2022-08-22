package dev.edurevsky.contacts.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.edurevsky.contacts.utils.JWTUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/refreshToken")
public class RefreshTokenController {

    private final JWTUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    public RefreshTokenController(JWTUtils jwtUtils, UserDetailsService userDetailsService, ObjectMapper objectMapper) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public void getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String refreshToken = request.getHeader("Authorization");
        String jwt = jwtUtils.getJwt(refreshToken);
        if (!jwtUtils.isValid(jwt)) {
            throw new RuntimeException("Refresh Token Expired");
        }
        String username = jwtUtils.parseClaims(jwt).getBody().getSubject();
        UserDetails user = userDetailsService.loadUserByUsername(username);
        String newToken = jwtUtils.generateToken(user.getUsername(), user.getAuthorities());
        response.addHeader("Authorization", "Bearer " + newToken);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("refreshToken", refreshToken.substring(7));
        objectMapper.writeValue(response.getOutputStream(), tokenMap);
    }
}
