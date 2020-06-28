package br.com.climb.framework.security;

import br.com.climb.framework.utils.JwtUtil;
import br.com.climb.test.model.Response;

import java.util.HashMap;
import java.util.Map;

public class Security {

    private Response response;

    public static Security create() {
        return new Security();
    }

    public Response build() {
        if (response.getStatus().equals(Response.OK)) {
            response.setToken(JwtUtil.create(response));
        } else {
            response.setToken("not authorized");
        }

        return response;
    }

    public Security() {
        response = new Response();
    }

    public Security subject(String value) {
        response.setSubject(value);
        return this;
    }

    public Security OK() {
        response.setStatus(Response.OK);
        return this;
    }

    public Security ERROR() {
        response.setStatus(Response.ERROR);
        return this;
    }

}