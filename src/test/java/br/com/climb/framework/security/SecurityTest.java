package br.com.climb.framework.security;

import br.com.climb.commons.security.JwtUtil;
import br.com.climb.commons.security.Security;
import br.com.climb.test.model.Response;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SecurityTest {

    @Test
    void build() {
        String subject = "Thiago Rodrigues";
        Response response = Security.create().OK().subject(subject).build();

        Assertions.assertSame(true, JwtUtil.decode(response.getToken()).getBody().getSubject().equals(subject));

        Response response1 = Security.create().ERROR().subject(subject).build();

        try {
            Assertions.assertSame(true, response1.getToken().equals("not authorized"));
            JwtUtil.decode(response1.getToken());
        } catch (MalformedJwtException e) {
            Assertions.assertSame(true, e.getMessage().equals("JWT strings must contain exactly 2 period characters. Found: 0"));
        }

    }
}