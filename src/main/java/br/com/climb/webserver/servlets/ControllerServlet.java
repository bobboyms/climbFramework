package br.com.climb.webserver.servlets;

import br.com.climb.commons.reqrespmodel.Response;
import br.com.climb.commons.reqrespmodel.ObjectRequest;
import br.com.climb.commons.reqrespmodel.Request;
import br.com.climb.webserver.tcpclient.TcpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class ControllerServlet extends HttpServlet {

    private static final String TEXT_PLAIN = "text/plain";
    private static final Logger logger = LoggerFactory.getLogger(ControllerServlet.class);

    private Request getLocalRequest(HttpServletRequest request) throws IOException {

        return new ObjectRequest(request.getMethod(),
                request.getPathInfo(),
                request.getContentType(),
                request.getParameterMap(),
                request.getReader().lines().collect(Collectors.joining()).getBytes(),
                request.getSession().getId());
    }

    private synchronized void responseForClient(HttpServletResponse response, HttpServletRequest request) throws IOException {

        TcpClient client = new TcpClient(getLocalRequest(request));
        client.initialize();
        Response objectResponse = client.getResponse();
        client.closeConnection();

        response.setContentType(objectResponse.getContentType());
        response.setCharacterEncoding(objectResponse.getCharacterEncoding());
        response.setStatus(objectResponse.getStatus());

        ServletOutputStream out = response.getOutputStream();
        out.write(objectResponse.getBody());
        out.flush();
        out.close();

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            final AsyncContext asyncContext = request.startAsync();

            new Thread(()->{

                final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
                final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

                try {

                    responseForClient(res, req);

                } catch (Exception e) {

                    logger.error("DoPut {}", e);

                    res.setContentType(TEXT_PLAIN);
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                } finally {
                    asyncContext.complete();
                }

            }).start();
        } catch (Exception e) {
            logger.error("THREAD doPut {}", e);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            final AsyncContext asyncContext = request.startAsync();

            new Thread(()->{

                final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
                final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

                try {

                    responseForClient(res, req);

                } catch (Exception e) {

                    logger.error("DoDelete {}", e);

                    res.setContentType(TEXT_PLAIN);
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                } finally {
                    asyncContext.complete();
                }

            }).start();
        } catch (Exception e) {
            logger.error("THREAD doDelete {}", e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            final AsyncContext asyncContext = request.startAsync();

            new Thread(()->{

                final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
                final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

                try {

                    responseForClient(res, req);

                } catch (Exception e) {

                    logger.error("DoPost {}", e);

                    res.setContentType(TEXT_PLAIN);
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                } finally {
                    asyncContext.complete();
                }

            }).start();

        } catch (Exception e) {
            logger.error("THREAD doPost {}", e);
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

                    responseForClient(res, req);

                } catch (Exception e) {

                    logger.error("DoGet {}", e);

                    res.setContentType(TEXT_PLAIN);
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

                } finally {
                    asyncContext.complete();
                }

            }).start();

        } catch (Exception e) {
            logger.error("THREAD DoGet {}", e);
        }

    }
}