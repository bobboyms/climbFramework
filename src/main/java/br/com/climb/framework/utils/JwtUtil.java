package br.com.climb.framework.utils;

import io.jsonwebtoken.*;

import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    private static String key = "1VLruSCiWJ1oOrfipnnrQDoL7MTtCcW9wGqJsypnvMYjQbWK0nc6p1T37j7s";

    public static final String TOKEN_HEADER = "Authentication";

    public static String create(String subject) {

        Map<String, Object> teste = new HashMap<>();
        teste.put("Nome", "Thiago Luiz Rodrigues");

        return Jwts.builder()
                .setSubject(subject)
//                .setHeader(teste)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static Jws<Claims> decode(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }
}
