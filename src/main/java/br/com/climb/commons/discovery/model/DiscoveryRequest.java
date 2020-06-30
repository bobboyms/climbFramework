package br.com.climb.commons.discovery.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public interface DiscoveryRequest extends Serializable {
    Map<String, Set<String>> getUrls();
    String getIpAddress();
    String getPort();

}
