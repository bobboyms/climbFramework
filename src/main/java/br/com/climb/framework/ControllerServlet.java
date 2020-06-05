package br.com.climb.framework;

import br.com.climb.framework.execptions.NotFoundException;
import br.com.climb.framework.requestresponse.interfaces.LoaderMethod;
import br.com.climb.framework.requestresponse.LoaderMethodRestController;
import br.com.climb.framework.requestresponse.model.Capsule;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.inject.se.SeContainer;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static br.com.climb.framework.utils.WeldContainer.initializer;

public class ControllerServlet extends HttpServlet {

    private synchronized void responseForClient(Capsule capsule, HttpServletResponse response) throws Exception {

        try(SeContainer WELD_CONTAINER = initializer.initialize()) {

            final Object instance = WELD_CONTAINER.select(capsule.getMethod().getDeclaringClass()).get();
            final Object result = capsule.getMethod().invoke(instance, capsule.getArgs());

            if (result != null) {

                ObjectMapper mapper = new ObjectMapper();
                final String json = mapper.writeValueAsString(result);

                response.setContentType("application/json; charset=UTF-8;");
                response.setCharacterEncoding("UTF-8");
                response.setStatus(HttpServletResponse.SC_OK);

                ServletOutputStream out = response.getOutputStream();
                out.write(json.getBytes("UTF-8"));
                out.flush();
                out.close();

            }

        }


    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final AsyncContext asyncContext = request.startAsync();

        new Thread(()->{

            final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
            final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

            try {

                final LoaderMethod loaderMethod = new LoaderMethodRestController();
                final Capsule capsule = loaderMethod.getMethodForCall(req);

                responseForClient(capsule, res);

            } catch (NotFoundException e) {

                res.setContentType("text/plain");
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                PrintWriter out = null;

                try {
                    out = response.getWriter();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                out.print(e.getMessage());
                out.flush();
            } catch (Exception e) {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            } finally {
                asyncContext.complete();
            }

        }).start();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final AsyncContext asyncContext = request.startAsync();

        new Thread(()->{

            final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
            final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

            try {

                final LoaderMethod loaderMethod = new LoaderMethodRestController();
                final Capsule capsule = loaderMethod.getMethodForCall(req);

                responseForClient(capsule, res);

            } catch (NotFoundException e) {
                res.setContentType("text/plain");
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                PrintWriter out = null;

                try {
                    out = response.getWriter();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                out.print(e.getMessage());
                out.flush();
            } catch (Exception e) {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            } finally {
                asyncContext.complete();
            }

        }).start();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final AsyncContext asyncContext = request.startAsync();

        new Thread(()->{

            final HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
            final HttpServletResponse res = (HttpServletResponse) asyncContext.getResponse();

            try {

                final LoaderMethod loaderMethod = new LoaderMethodRestController();
                final Capsule capsule = loaderMethod.getMethodForCall(req);

                responseForClient(capsule, res);

            } catch (NotFoundException e) {
                res.setContentType("text/plain");
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                PrintWriter out = null;

                try {
                    out = response.getWriter();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                out.print(e.getMessage());
                out.flush();

            } catch (Exception e) {
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            } finally {
                asyncContext.complete();
            }

        }).start();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

                responseForClient(capsule, res);

            } catch (NotFoundException e) {

                res.setContentType("text/plain");
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                PrintWriter out = null;

                try {
                    out = response.getWriter();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                out.print(e.getMessage());
                out.flush();
            } catch (Exception e) {
                res.setContentType("text/plain");
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                PrintWriter out = null;

                try {
                    out = response.getWriter();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                out.println("***** ERROR ****** ");
                out.println(e.toString());
                out.flush();
                e.printStackTrace();
            } finally {
                asyncContext.complete();
            }
        }).start();

    }
}