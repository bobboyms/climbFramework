package br.com.climb.commons.security;

import br.com.climb.test.model.Response;
import io.jsonwebtoken.*;

public class JwtUtil {

    private static final String key = "1VLruSCiWJ1oOrfipnnrQDoL7MTtCcW9wGqJsypnvMYjQbWK0nc6p1T37j7s";

    public static final String TOKEN_HEADER = "Authentication";

    public synchronized static String create(Response response) {

        return Jwts.builder()
                .setSubject(response.getSubject())
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public synchronized static Jws<Claims> decode(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
    }
}
