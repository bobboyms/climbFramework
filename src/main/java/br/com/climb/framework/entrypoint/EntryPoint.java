package br.com.climb.framework.entrypoint;

import br.com.climb.cdi.annotations.Factory;
import br.com.climb.cdi.annotations.Produces;
import br.com.climb.cdi.annotations.Singleton;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Factory
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
