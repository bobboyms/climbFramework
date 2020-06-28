package br.com.climb.webserver.servlets;

import br.com.climb.cdi.ManagerContext;
import br.com.climb.commons.reqrespmodel.Response;
import br.com.climb.framework.execptions.NotFoundException;
import br.com.climb.commons.reqrespmodel.ObjectRequest;
import br.com.climb.commons.reqrespmodel.Request;
import br.com.climb.framework.requestresponse.interfaces.LoaderMethod;
import br.com.climb.framework.requestresponse.LoaderMethodRestController;
import br.com.climb.framework.requestresponse.model.Capsule;
import br.com.climb.webserver.tcpclient.TCPClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

import static br.com.climb.webserver.ClimbApplication.containerInitializer;

public class ControllerServlet extends HttpServlet {

    private static final String TEXT_PLAIN = "text/plain";
    private static final Logger logger = LoggerFactory.getLogger(ControllerServlet.class);

    private Request getLocalRequest(HttpServletRequest request) throws IOException {

        return new ObjectRequest(request.getMethod(),
                request.getPathInfo(),
                request.getContentType(),
                request.getParameterMap(),
                request.getReader().lines().collect(Collectors.joining()).getBytes());
    }

    private void responseForClient(Capsule capsule, HttpServletResponse response, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {


    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            final AsyncContext asyncContext = request.startAsync();

            new Thread(()->{

                final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
                final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();



            }).start();
        } catch (Exception e) {
            logger.error("THREAD doPut { }", e);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            final AsyncContext asyncContext = request.startAsync();

            new Thread(()->{

                final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
                final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();


            }).start();
        } catch (Exception e) {
            logger.error("THREAD doDelete { }", e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            final AsyncContext asyncContext = request.startAsync();

            new Thread(()->{

                final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
                final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();



            }).start();

        } catch (Exception e) {
            logger.error("THREAD doPost { }", e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            final AsyncContext asyncContext = request.startAsync();

            new Thread(() -> {

                final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
                final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

                try {
                    TCPClient client = new TCPClient();
                    client.run();
                    Response objectResponse = client.getResponse();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();

        } catch (Exception e) {
            logger.error("THREAD DoGet { }", e);
        }

    }
}