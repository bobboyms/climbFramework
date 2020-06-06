package br.com.climb.framework;

import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.requestresponse.LoaderClassRestController;
import br.com.climb.framework.requestresponse.interfaces.Storage;
import br.com.climb.framework.servlets.ControllerServlet;
import br.com.climb.framework.servlets.filters.CorsFilter;
import br.com.climb.framework.servlets.filters.JwtFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.util.*;

import static br.com.climb.framework.utils.ReflectionUtils.getAnnotedClass;

public class JettyServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);

    public void start() throws Exception {

        final Server server = new Server(8080);

        ServletContextHandler servletContextHandler = new ServletContextHandler ();
        servletContextHandler.setContextPath("/");

        servletContextHandler.addServlet(ControllerServlet.class, "/*");
        servletContextHandler.addEventListener(new MyContextListener());

        servletContextHandler.addFilter(JwtFilter.class,"/api/*",
                EnumSet.of(DispatcherType.REQUEST));

        servletContextHandler.addFilter(CorsFilter.class,"/*",
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


    public static void main(String[] args) throws Exception {
        new JettyServer().start();
    }

}
