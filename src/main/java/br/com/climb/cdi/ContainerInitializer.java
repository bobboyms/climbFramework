package br.com.climb.cdi;

import br.com.climb.cdi.annotations.*;
import br.com.climb.cdi.model.Capsule;
import br.com.climb.framework.utils.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

public abstract class ContainerInitializer implements Initializer {

    protected final Map<Class<?>, List<Class<?>>> concreteInterfaceClasses = new HashMap<>();
    protected final Map<Class<?>, Capsule> factoriesClasses = new HashMap<>();
    protected final Map<Class<?>, Class<?>> interceptorClasses = new HashMap<>();
    protected final Map<Class<?>, Capsule> disposesMethods = new HashMap<>();
    protected final Map<String, Map<Class<?>, Object>> sessionObjects = new HashMap<>();

    @Override
    public Map<Class<?>, Capsule> getDisposesMethods() {
        return disposesMethods;
    }

    @Override
    public Map<Class<?>, Capsule> getFactoriesClasses() {
        return factoriesClasses;
    }

    @Override
    public Map<Class<?>, List<Class<?>>> getConcreteInterfaceClasses() {
        return concreteInterfaceClasses;
    }

    @Override
    public Map<Class<?>, Class<?>> getInterceptorClasses(){
        return interceptorClasses;
    }

    @Override
    public Map<String, Map<Class<?>, Object>> getSessionObjects() {
        return sessionObjects;
    }

    public static ContainerInitializer newInstance() {
        return new Container();
    }

    protected void startInterceptors() throws IOException {
        Arrays.asList(ReflectionUtils.getAnnotedClass(Interceptor.class, "br.com.")).parallelStream()
                .forEach(classes -> {
                    classes.stream().forEach(interceptorClass -> {
                        List<Class> annotationType =  Arrays.asList(interceptorClass.getAnnotations()).stream().filter(annotation -> annotation.annotationType() != Interceptor.class)
                                .map(annotation -> annotation.annotationType())
                                .collect(Collectors.toList());

                        interceptorClasses.put(annotationType.get(0), interceptorClass);
                    });
                });
    }

    protected void startDisposesClass() throws IOException {
        Arrays.asList(ReflectionUtils.getAnnotedClass(Factory.class, "br.com.")).parallelStream()
                .forEach(classes -> {
                    classes.stream().forEach(factoryClass -> {
                        Arrays.asList(factoryClass.getMethods()).stream().forEach(method -> {
                            if (method.getAnnotation(Disposes.class) != null) {

                                Capsule capsule = new Capsule();
                                capsule.setClassFactory(factoryClass);
                                capsule.setMethod(method);

                                if (method.getParameters().length == 1) {
                                    Parameter parameter = Arrays.asList(method.getParameters()).get(0);
                                    disposesMethods.put(parameter.getType(), capsule);

                                } else {
                                    try {
                                        throw new IOException("the method with the annotation dispose needs an argument");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        });
                    });
                });
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
