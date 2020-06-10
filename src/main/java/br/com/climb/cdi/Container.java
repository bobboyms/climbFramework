package br.com.climb.cdi;


import java.util.Set;

public class Container extends ContainerInitializer {

    public Container() {
        try {
            startFactories();
            startInterceptors();
            startDisposesClass();
            initialiseConcreteInterfaceClasses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ManagerContext createManager() {
        return new Manager(this);
    }


}
