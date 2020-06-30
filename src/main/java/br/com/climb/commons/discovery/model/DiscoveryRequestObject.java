package br.com.climb.commons.discovery.model;

import java.util.Map;
import java.util.Set;

public class DiscoveryRequestObject implements DiscoveryRequest {

    private String ipAddress;
    private String port;

    //METHOD, URLS
    private Map<String, Set<String>> urls;

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setUrls(Map<String, Set<String>> urls) {
        this.urls = urls;
    }

    @Override
    public Map<String, Set<String>> getUrls() {
        return urls;
    }

    @Override
    public String getIpAddress() {
        return ipAddress;
    }

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "DiscoveryRequestObject{" +
                "ipAddress='" + ipAddress + '\'' +
                ", port='" + port + '\'' +
                ", urls=" + urls +
                '}';
    }
}
