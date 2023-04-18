package com.practice.test.market.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Date;

@Component
public class JWTGenerator {

    public String generateToken(Authentication authentication) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION);
//
//        SecretKey key =  Keys.secretKeyFor(SignatureAlgorithm.HS512).getAlgorithm();
//        Key k = new SecretKeySpec()
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SecurityConstants.JWT_SECRET),
                SignatureAlgorithm.HS512.getJcaName());
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(hmacKey, SignatureAlgorithm.HS512)
                //.signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET/*generateSafeToken()*/ )
                .compact();
        return token;
    }

    public String getUsernameFromJWT(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SecurityConstants.JWT_SECRET),
                SignatureAlgorithm.HS512.getJcaName());
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(hmacKey).build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {

            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SecurityConstants.JWT_SECRET),
                    SignatureAlgorithm.HS512.getJcaName());
            Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

    public static SecretKey getKey()
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(Keys.secretKeyFor(SignatureAlgorithm.HS512).getAlgorithm());
        KeySpec spec = new PBEKeySpec(SecurityConstants.JWT_SECRET.toCharArray(), "@$#baelDunG@#^$*".getBytes(), 65536, 256);
        SecretKey originalKey = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "HS512");
        return originalKey;
    }
}
