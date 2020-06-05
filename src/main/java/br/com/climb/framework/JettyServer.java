package br.com.climb.framework;

import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.requestresponse.LoaderClassRestController;
import br.com.climb.framework.requestresponse.interfaces.Storage;
import io.jsonwebtoken.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static br.com.climb.framework.utils.ReflectionUtils.getAnnotedClass;

public class JettyServer {

    private Server server;

    public void start() throws Exception {

        server = new Server(8080);

        ServletContextHandler servletContextHandler = new ServletContextHandler ();
        servletContextHandler.setContextPath("/");

        servletContextHandler.addServlet(ControllerServlet.class, "/*");
        servletContextHandler.addEventListener(new MyContextListener());

        servletContextHandler.addFilter(JWTFilter.class,"/api/*",
                EnumSet.of(DispatcherType.REQUEST));

        servletContextHandler.addFilter(CORSFilter.class,"/*",
                EnumSet.of(DispatcherType.REQUEST));



        HandlerList handlers = new HandlerList();
        handlers.addHandler(servletContextHandler);
//        handlers.addHandler(servletHandler);

        Set<Class<?>> clazzs = getAnnotedClass(RestController.class, "br.com.");
        Storage storage = new LoaderClassRestController();
        storage.storage(clazzs);

        System.out.println("***** INICIOU O SERVIDOR *****");

        server.setHandler(handlers);
        server.start();

    }

    public static class MyContextListener implements ServletContextListener
    {
        @Override
        public void contextInitialized(ServletContextEvent sce)
        {
            System.err.printf("MyContextListener.contextInitialized(%s)%n", sce);
            sce.getServletContext().addListener(new MyRequestListener());
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce)
        {
            System.err.printf("MyContextListener.contextDestroyed(%s)%n", sce);
        }
    }

    public static class MyRequestListener implements ServletRequestListener
    {
        @Override
        public void requestDestroyed(ServletRequestEvent sre)
        {
            System.err.printf("MyRequestListener.requestDestroyed(%s)%n", sre);
        }

        @Override
        public void requestInitialized(ServletRequestEvent sre)
        {
            System.err.printf("MyRequestListener.requestInitialized(%s)%n", sre);
        }
    }

    public static class CORSFilter implements Filter {

        @Override
        public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

            System.out.println("CORSFilter ");

            HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            chain.doFilter(req, res);
        }

        @Override
        public void init(FilterConfig filterConfig) {
            System.out.println("INICIOU O FILTRO");
        }

        public void destroy() {}

    }

    public static class JWTFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

            System.out.println("CORSFilter ");

            HttpServletRequest req = (HttpServletRequest) servletRequest;
            HttpServletResponse res = (HttpServletResponse) servletResponse;

//            if(req.getRequestURI().startsWith("/api/login")){
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }

            String token = req.getHeader(JWTUtil.TOKEN_HEADER);

            if(token == null || token.trim().isEmpty()){
                res.setStatus(401);
                return;
            }

            try {
                Jws<Claims> parser = JWTUtil.decode(token);
                System.out.println("User request: "+ parser.getBody().getSubject());
                filterChain.doFilter(servletRequest, servletResponse);
            } catch (SignatureException e) {
                res.setStatus(401);
            }

        }

        @Override
        public void destroy() {

        }

    }

    public static class JWTUtil {

        private static String key = "1VLruSCiWJ1oOrfipnnrQDoL7MTtCcW9wGqJsypnvMYjQbWK0nc6p1T37j7s";

        public static final String TOKEN_HEADER = "Authentication";

        public static String create(String subject) {
            return Jwts.builder()
                    .setSubject(subject)
                    .signWith(SignatureAlgorithm.HS512, key)
                    .compact();
        }

        public static Jws<Claims> decode(String token){
            return Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        }

    }

    public static void main(String[] args) throws Exception {
        new JettyServer().start();
    }

}
