package br.com.climb.cdi;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Singleton;
import br.com.climb.cdi.model.Capsule;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Manager implements AutoCloseable, ManagerContext {

    private Map<Class, List<Class>> concreteInterfaceClasses;
    private Map<Class, Capsule> factoriesClasses;
    private Map<Class, Object> singletonsObjects = new HashMap<>();

    public Manager(Map<Class, List<Class>> concreteInterfaceClasses, Map<Class, Capsule> factoriesClasses) {
        this.concreteInterfaceClasses = concreteInterfaceClasses;
        this.factoriesClasses = factoriesClasses;
    }

    protected Class getQualifierClass(Field field) {

        final List<Class> listConcreteClassInterface = concreteInterfaceClasses.get(field.getType());

        if (listConcreteClassInterface != null && listConcreteClassInterface.size() > 1) {
            final String qualifier  = field.getAnnotation(Inject.class).value();
            final List<Class> listClazzTemp = listConcreteClassInterface.stream().filter(aClass -> ((Component) aClass.getAnnotation(Component.class)).value().equals(qualifier))
                    .collect(Collectors.toList());
            return listClazzTemp.get(0);
        }

        return concreteInterfaceClasses.get(field.getType()).get(0);

    }

    protected Class getClassOfField(Field field) {

        if (field.getType().isInterface()) {
            return getQualifierClass(field);
        }

        return field.getType();

    }

    protected Object getSingletonObject(Capsule capsule) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        if (capsule.getMethod().getAnnotation(Singleton.class) != null) {

            final Object singleton = singletonsObjects.get(capsule.getMethod().getReturnType());

            if (singleton != null) {
                return singleton;
            } else {
                final Object instance = capsule.getClassFactory().getDeclaredConstructor().newInstance();
                injectObjecstInComponentClass(capsule.getClassFactory(), instance);
                final Object resultInvoke = capsule.getMethod().invoke(instance);
                singletonsObjects.put(capsule.getMethod().getReturnType(), resultInvoke);
                return resultInvoke;
            }

        }

        return null;
    }

    protected Object generateInstanceByTheFactory(Field field) {

        final Capsule capsule = factoriesClasses.get(field.getType());

        if (capsule == null) {
            return null;
        }

        try {

            final Object singleton = getSingletonObject(capsule);

            if (singleton != null) {
                return singleton;
            }

            final Object instance = capsule.getClassFactory().getDeclaredConstructor().newInstance();
            injectObjecstInComponentClass(capsule.getClassFactory(), instance);
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

    protected Object generateInstance(Field field) {

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


    protected void injectInstanceField(Object main, Field field, Object instance) {

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

    private void injectObjecstInComponentClass(Class clazz, Object instance) {
        Arrays.asList(clazz.getDeclaredFields()).stream()
                .filter(field -> field.getAnnotation(Inject.class) != null)
                .forEach(field -> injectInstanceField(instance, field, generateInstance(field)));
    }

    public Object generateInstanceBase(Class clazz) {

        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> proxy.invokeSuper(obj, args));

        final Object base = enhancer.create();
        injectObjecstInComponentClass(clazz, base);

        return base;

    }

    @Override
    public Object generateInstance(Class aClass) {
        return generateInstanceBase(aClass);
    }

    @Override
    public void close() throws Exception {
        System.out.println("fechou as instancias");
    }
}
