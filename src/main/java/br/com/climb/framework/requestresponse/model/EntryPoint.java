package br.com.climb.framework.requestresponse.model;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ApplicationScoped
public class EntryPoint implements ReceiveHttpRequest, ReceiveHttpResponse {

    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    public void setHttpRequest(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public void setHttpResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Produces
    @Singleton
    public HttpServletRequest getRequest() {
        return request;
    }

    @Produces
    @Singleton
    public HttpServletResponse getResponse() {
        return response;
    }



}
