package br.com.climb.framework.servlets;

import br.com.climb.framework.execptions.NotFoundException;
import br.com.climb.framework.requestresponse.interfaces.LoaderMethod;
import br.com.climb.framework.requestresponse.LoaderMethodRestController;
import br.com.climb.framework.requestresponse.model.Capsule;
import br.com.climb.framework.requestresponse.model.EntryPoint;
import br.com.climb.framework.requestresponse.model.ReceiveHttpRequest;
import br.com.climb.framework.requestresponse.model.ReceiveHttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.se.SeContainer;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import static br.com.climb.framework.utils.WeldContainer.initializer;

public class ControllerServlet extends HttpServlet {

    private static final String TEXT_PLAIN = "text/plain";

    private static final Logger logger = LoggerFactory.getLogger(ControllerServlet.class);

    private synchronized void responseForClient(Capsule capsule, HttpServletResponse response, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {

        try(SeContainer weldContainer = initializer.initialize()) {

            final EntryPoint entryPoint = weldContainer.select(EntryPoint.class).get();
            ((ReceiveHttpRequest)entryPoint).setHttpRequest(request);
            ((ReceiveHttpResponse)entryPoint).setHttpResponse(response);

            final Object instance = weldContainer.select(capsule.getMethod().getDeclaringClass()).get();
            final Object result = capsule.getMethod().invoke(instance, capsule.getArgs());

            if (result != null) {

                ObjectMapper mapper = new ObjectMapper();
                final String json = mapper.writeValueAsString(result);

                response.setContentType("application/json; charset=UTF-8;");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);

                ServletOutputStream out = response.getOutputStream();
                out.write(json.getBytes()); // "UTF-8"
                out.flush();
                out.close();

            }

        } catch (IOException e) {
            logger.error("responseForClient { }", e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            final AsyncContext asyncContext = request.startAsync();

            new Thread(()->{

                final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
                final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

                try {

                    final LoaderMethod loaderMethod = new LoaderMethodRestController();
                    final Capsule capsule = loaderMethod.getMethodForCall(req);

                    responseForClient(capsule, res,req);

                } catch (NotFoundException e) {

                    try {
                        res.setContentType(TEXT_PLAIN);
                        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        PrintWriter out = response.getWriter();
                        out.print(e.getMessage());
                        out.flush();
                    }catch (Exception ex) {
                        logger.error("{ }", ex);
                    }

                    logger.error("{ }", e);

                } catch (Exception e) {
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    logger.error("{ }", e);
                } finally {
                    asyncContext.complete();
                }

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

                try {

                    final LoaderMethod loaderMethod = new LoaderMethodRestController();
                    final Capsule capsule = loaderMethod.getMethodForCall(req);

                    responseForClient(capsule, res, req);

                } catch (NotFoundException e) {

                    try {
                        res.setContentType(TEXT_PLAIN);
                        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        PrintWriter out = response.getWriter();
                        out.print(e.getMessage());
                        out.flush();
                    } catch (Exception ex) {
                        logger.error("message { }", e);
                    }

                } catch (Exception e) {
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    logger.error("{ }", e);
                } finally {
                    asyncContext.complete();
                }

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

                try {

                    final LoaderMethod loaderMethod = new LoaderMethodRestController();
                    final Capsule capsule = loaderMethod.getMethodForCall(req);

                    responseForClient(capsule, res, req);

                } catch (NotFoundException e) {

                    try {
                        res.setContentType(TEXT_PLAIN);
                        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        PrintWriter out = response.getWriter();

                        out.print(e.getMessage());
                        out.flush();
                    } catch (Exception ex) {
                        logger.error("{ }", ex);
                    }

                    logger.error("{ }", e);

                } catch (Exception e) {
                    res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    logger.error("{ }", e);
                } finally {
                    asyncContext.complete();
                }

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

                    if (req.getPathInfo().equals("/favicon.ico")) {
                        return;
                    }

                    final LoaderMethod loaderMethod = new LoaderMethodRestController();
                    final Capsule capsule = loaderMethod.getMethodForCall(req);

                    responseForClient(capsule, res, req);

                } catch (NotFoundException e) {

                    res.setContentType(TEXT_PLAIN);
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    PrintWriter out = null;

                    try {
                        out = response.getWriter();
                    } catch (IOException ioException) {
                        logger.error("{ }", ioException);
                    }
                    out.print(e.getMessage());
                    out.flush();
                    logger.error("{ }", e);
                } catch (Exception e) {
                    res.setContentType(TEXT_PLAIN);
                    res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    PrintWriter out = null;

                    try {
                        out = response.getWriter();
                    } catch (IOException ioException) {
                        logger.error("{ }", ioException);
                    }
                    out.println("***** ERROR ****** ");
                    out.println(e.toString());
                    out.flush();
                    logger.error("{ }", e);
                } finally {
                    asyncContext.complete();
                }
            }).start();

        } catch (Exception e) {
            logger.error("THREAD DoGet { }", e);
        }

    }
}