package br.com.climb.cdi;

import br.com.climb.framework.utils.ReflectionUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ClimbCdiContainer {

    protected static Object generateInstance(Class field) {

        final Enhancer enhancer = new Enhancer();

        if (field.isInterface()) {
            field = concreteInterfaceClasses.get(field).get(0);
        }

        enhancer.setSuperclass(field);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {

            if (method.getName().contains("get")) {

                final Class returnType = method.getReturnType();
                final Object result = proxy.invokeSuper(obj, args);

                if (result == null && returnType.getAnnotation(Component.class) != null) {
                    return generateInstanceBase(method.getReturnType());
                }

                return result;
            }

            return proxy.invokeSuper(obj, args);

        });

        return enhancer.create();

    }


    protected static void injectInstanceField(Object main, Field field, Object instance) {

        final String methodName = "set" + field.getName().substring(0, 1)
                .toUpperCase() + field.getName()
                .substring(1);

        try {

            Method method = main.getClass().getMethod(methodName, field.getType());
            method.invoke(main, instance);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized static Object generateInstanceBase(Class clazz) {

        final Object base = generateInstance(clazz);

        Arrays.asList(clazz.getDeclaredFields()).stream()
                .filter(field -> field.getAnnotation(Inject.class) != null)
                .forEach(field -> injectInstanceField(base, field, generateInstance(field.getType())));

        return base;

    }

    private static final Map<Class, List<Class>> concreteInterfaceClasses = new HashMap<>();

    public static void start() throws IOException {
        Arrays.asList(ReflectionUtils.getAnnotedClass(Component.class, "br.com.")).parallelStream()
                .forEach(classes -> {
                    classes.stream().forEach(aClass -> {

                        Arrays.asList(aClass.getInterfaces()).parallelStream()
                                .forEach(iface -> {

                                    List<Class> clazzs = concreteInterfaceClasses.get(iface);

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

}
