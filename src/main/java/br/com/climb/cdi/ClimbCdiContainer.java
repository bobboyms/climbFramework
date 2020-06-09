package br.com.climb.cdi;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ClimbCdiContainer {

    protected static Object generateInstance(Class field) {

        final Enhancer enhancer = new Enhancer();
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

}
