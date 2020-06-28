package br.com.climb.webserver.servlets.filters;

import br.com.climb.framework.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Do not necessary impl
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

//            if(req.getRequestURI().startsWith("/api/login")){
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }

        String token = req.getHeader(JwtUtil.TOKEN_HEADER);

        if (token == null || token.trim().isEmpty()) {
            res.setStatus(401);
            return;
        }

        try {
            Jws<Claims> parser = JwtUtil.decode(token);
            logger.info("User request: {}", parser.getBody().getSubject());
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (SignatureException e) {
            res.setStatus(401);
        } catch (MalformedJwtException e) {
            res.setStatus(401);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destroy() {
        // Do not necessary impl
    }
}
