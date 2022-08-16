package dev.edurevsky.contacts.utils;

import dev.edurevsky.contacts.models.Role;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JWTUtils {

    private final String applicationSecret = "secret";
    private final Integer expirationLimit = 60 * 60 * 2 * 1000; // 2 Hours

    private final UserDetailsService userDetailsService;

    public JWTUtils(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        String typ = Header.JWT_TYPE;
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + expirationLimit))
                .signWith(signatureAlgorithm, applicationSecret)
                .setHeaderParam("typ", typ)
                .compact();
    }

    public Boolean isValid(String jwtToken) {
        try {
            parseClaims(jwtToken);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String jwtToken) {
        String username = parseClaims(jwtToken).getBody().getSubject();
        UserDetails user = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    private Jws<Claims> parseClaims(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(applicationSecret)
                .parseClaimsJws(jwtToken);
    }
}
