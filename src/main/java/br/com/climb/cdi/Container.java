package br.com.climb.cdi;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.model.Capsule;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Container extends ContainerInitializer {

    public Container() {
        try {
            startFactories();
            initialiseConcreteInterfaceClasses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ManagerContext createManager() {
        return new Manager(concreteInterfaceClasses, factoriesClasses);
    }


}
