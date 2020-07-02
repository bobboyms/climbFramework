package br.com.climb.framework.requestresponse.interfaces;

import br.com.climb.commons.model.DiscoveryRequest;

import java.util.Set;

public interface Storage {
    Storage storageRestControllers(final Set<Class<?>> clazzs);
    void storageMessageControllers(final Set<Class<?>> clazzs);
    DiscoveryRequest generateDiscoveryRequest(String ipAddress, String port);
}
