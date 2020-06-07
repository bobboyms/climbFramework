package br.com.climb.framework.entrypoint;

import javax.servlet.http.HttpServletResponse;

public interface ReceiveHttpResponse {

    void setHttpResponse(HttpServletResponse response);

}
