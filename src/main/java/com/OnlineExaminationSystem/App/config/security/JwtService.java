package com.OnlineExaminationSystem.App.config.security;

import com.OnlineExaminationSystem.App.entity.users.User;
import com.OnlineExaminationSystem.App.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Autowired
    public UserRepository userRepository;

    private static final String SECRET_KEY = "5A7134743777397A24432646294A404E635266556A586E3272357538782F4125";

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Africa/Cairo")).plusHours(1);

        Optional<User> user = this.userRepository.findUserByEmail(userDetails.getUsername());
        extraClaims.put("userId", user.map(User::getId).orElse(0L));
        extraClaims.put("permissions", userDetails.getAuthorities());
        extraClaims.put("username", userDetails.getUsername());


        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(zonedDateTime.toInstant()))
                .setExpiration(Date.from(zonedDateTime.plusDays(1).toInstant()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())
                && !IsTokenExpired(token));
    }



    private boolean IsTokenExpired(String token) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Africa/Cairo")).plusHours(1);
        return extractExpiration(token).before(Date.from(zonedDateTime.toInstant()));
    }

    private Date extractExpiration(String token) {
        return extractClaim((token), Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}
