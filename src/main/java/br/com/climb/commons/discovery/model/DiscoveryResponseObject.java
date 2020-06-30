package br.com.climb.commons.discovery.model;

public class DiscoveryResponseObject implements DiscoveryResponse {

    private Integer statusCode;

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public Integer getStatusCode() {
        return statusCode;
    }
}
