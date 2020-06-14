package br.com.climb.cdi;


import br.com.climb.cdi.exception.ValidationException;

public interface ManagerContext extends AutoCloseable {

    void disposeObjects();
    Object generateInstance(Class<?> aClass);
    Object generateInstance(Class<?> aClass, String sessionid);

}
