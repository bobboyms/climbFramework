package br.com.climb.framework.entrypoint;

import javax.servlet.http.HttpServletRequest;

public interface ReceiveHttpRequest {

    void setHttpRequest(HttpServletRequest request);

}
