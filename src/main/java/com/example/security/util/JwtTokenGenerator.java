package com.example.security.util;

import com.example.security.transfer.JwtUserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * convenience class to generate a token for testing your requests.
 * Make sure the used secret here matches the on in your application.properties
 *
 * Created by sungmen999 on 10/10/2016 AD.
 */
public class JwtTokenGenerator {
    private static long nowMillis = System.currentTimeMillis();
    // private static long ttlMillis = 3600000; // 1 hours
    private static long ttlMillis = 30000; // 0.5 minutes

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public static String generateToken(JwtUserDto u, String secret) {
        Claims claims = Jwts.claims()
                .setIssuedAt(currentDate())
                .setExpiration(expirationDate())
                .setSubject(u.getUsername());
        claims.put("userId", u.getId() + "");
        claims.put("role", u.getRole());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private static Date currentDate() {
        return new Date(nowMillis);
    }

    private static Date expirationDate() {
        long expMillis = nowMillis + ttlMillis;
        return new Date(expMillis);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        JwtUserDto user = new JwtUserDto();
        user.setId(123L);
        user.setUsername("Pascal");
        user.setRole("admin");
        System.out.println("**************************************\n\n" + generateToken(user, "my-very-secret-key") + "\n\n**************************************");
    }
}
