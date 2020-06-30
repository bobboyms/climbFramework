package br.com.climb.commons.discovery.model;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

public interface DiscoveryResponse extends Serializable {

    static final int OK = 200;

    Integer getStatusCode();

}
