package br.com.climb.cdi;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Factory;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Produces;
import br.com.climb.cdi.model.Capsule;
import br.com.climb.framework.utils.ReflectionUtils;

import java.io.IOException;
import java.util.*;

public abstract class ContainerInitializer {

    protected final Map<Class<?>, List<Class<?>>> concreteInterfaceClasses = new HashMap<>();
    protected final Map<Class<?>, Capsule> factoriesClasses = new HashMap<>();

    public static ContainerInitializer newInstance() {
        return new Container();
    }

    protected void startFactories() throws IOException {
        Arrays.asList(ReflectionUtils.getAnnotedClass(Factory.class, "br.com.")).parallelStream()
                .forEach(classes -> {
                    classes.stream().forEach(factoryClass -> {
                        Arrays.asList(factoryClass.getMethods()).stream().forEach(method -> {
                            if (method.getAnnotation(Produces.class) != null) {
                                Capsule capsule = new Capsule();
                                capsule.setClassFactory(factoryClass);
                                capsule.setMethod(method);
                                factoriesClasses.put(method.getReturnType(), capsule);
                            }
                        });
                    });
                });
    }

    protected void initialiseConcreteInterfaceClasses() throws IOException {

        Arrays.asList(ReflectionUtils.getAnnotedClass(Component.class, "br.com.")).parallelStream()
                .forEach(classes -> {
                    classes.stream().forEach(aClass -> {
                        Arrays.asList(aClass.getInterfaces()).parallelStream()
                                .forEach(iface -> {

                                    List<Class<?>> clazzs = concreteInterfaceClasses.get(iface);

                                    if (clazzs == null) {
                                        clazzs = new ArrayList<>();
                                        clazzs.add(aClass);
                                        concreteInterfaceClasses.put(iface, clazzs);
                                    } else {
                                        clazzs.add(aClass);
                                    }

                                });
                    });
                });
    }

    /**
     * Cria o gerenciador de contexto da aplicação
     * @return
     */
    public abstract ManagerContext createManager();

}
