package br.com.climb.cdi;

import br.com.climb.cdi.model.Capsule;
import br.com.climb.commons.configuration.ConfigFile;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Initializer {

    Map<Class<?>, Capsule> getDisposesMethods();
    Map<Class<?>, Capsule> getFactoriesClasses();
    Map<Class<?>, List<Class<?>>> getConcreteInterfaceClasses();
    Map<Class<?>, Class<?>> getInterceptorClasses();
    Map<String, Map<Class<?>, Object>> getSessionObjects();
    ConfigFile getConfigFile();

}
