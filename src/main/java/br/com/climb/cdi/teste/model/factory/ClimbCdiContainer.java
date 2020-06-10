package br.com.climb.cdi.teste.model.factory;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Factory;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Produces;
import br.com.climb.cdi.model.Capsule;
import br.com.climb.framework.utils.ReflectionUtils;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ClimbCdiContainer {

    private static final Map<Class, List<Class>> concreteInterfaceClasses = new HashMap<>();
    private static final Map<Class, Capsule> factoriesClasses = new HashMap<>();

    protected static Class getQualifierClass(Field field) {

        final List<Class> listConcreteClassInterface = concreteInterfaceClasses.get(field.getType());

        if (listConcreteClassInterface != null && listConcreteClassInterface.size() > 1) {
            final String qualifier  = field.getAnnotation(Inject.class).value();
            final List<Class> listClazzTemp = listConcreteClassInterface.stream().filter(aClass -> ((Component) aClass.getAnnotation(Component.class)).value().equals(qualifier))
                    .collect(Collectors.toList());
            return listClazzTemp.get(0);
        }

        return concreteInterfaceClasses.get(field.getType()).get(0);

    }

    protected static Class getClassOfField(Field field) {

        if (field.getType().isInterface()) {
            return getQualifierClass(field);
        }

        return field.getType();

    }

    protected static Object generateInstanceByTheFactory(Field field) {

        final Capsule capsule = factoriesClasses.get(field.getType());

        if (capsule == null) {
            return null;
        }

        try {

            final Object instance = capsule.getClassFactory().getDeclaredConstructor().newInstance();

            injectObjectInFields(capsule.getClassFactory(), instance);
            return capsule.getMethod().invoke(instance);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        throw new Error("unexpected error in this operation");
    }

    protected static Object generateInstance(Field field) {

        final Object localInstance = generateInstanceByTheFactory(field);

        if (localInstance != null) {
            return localInstance;
        }

        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getClassOfField(field));
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

    private static void injectObjectInFields(Class clazz, Object instance) {
        Arrays.asList(clazz.getDeclaredFields()).parallelStream()
                .filter(field -> field.getAnnotation(Inject.class) != null)
                .forEach(field -> injectInstanceField(instance, field, generateInstance(field)));
    }

    public synchronized static Object generateInstanceBase(Class clazz) {

        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> proxy.invokeSuper(obj, args));

        final Object base = enhancer.create();
        injectObjectInFields(clazz, base);

        return base;

    }

    /**
     * Inicia todas as fabricas
     * @throws IOException
     */
    private static void startFactories() throws IOException {
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

    public static void start() throws IOException {

        startFactories();

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
