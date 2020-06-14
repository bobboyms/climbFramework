package br.com.climb.cdi.instances;

import br.com.climb.cdi.Initializer;
import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.ReCreate;
import br.com.climb.cdi.clazz.TypeOfClass;
import br.com.climb.cdi.disposes.Disposes;
import br.com.climb.cdi.interceptor.InterceptorMethod;
import br.com.climb.cdi.model.Capsule;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class InstancesManager implements Instances, InjectInstance, Singleton {

    private Initializer initializer;
    private Disposes disposes;
    private TypeOfClass typeOfClass;

    private Map<Class<?>, Object> singletonsObjects = new HashMap<>();

    protected InstancesManager(Initializer initializer,
                             Disposes disposes,
                             TypeOfClass typeOfClass) {
        this.initializer = initializer;
        this.disposes = disposes;
        this.typeOfClass = typeOfClass;
    }

    public static Instances create(Initializer initializer,
                                   Disposes disposes,
                                   TypeOfClass typeOfClass) {
        return new InstancesManager(initializer,disposes, typeOfClass);
    }

    @Override
    public Object generateInstanceByTheFactory(Field field) {

        final Capsule capsule = initializer.getFactoriesClasses().get(field.getType());

        if (capsule == null) {
            return null;
        }

        try {

            final Object singletonInstance = getSingletonObject(capsule);

            if (singletonInstance != null) {
                return singletonInstance;
            }

            final Object instance = capsule.getClassFactory().getDeclaredConstructor().newInstance();
            injectObjecstInComponentClass(capsule.getClassFactory(), instance);

            final Object resultInvoke = capsule.getMethod().invoke(instance);

            disposes.addDisposeList(capsule, resultInvoke);

            return resultInvoke;

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

    @Override
    public Object generateInstance(Field field) {

        final Object localInstance = generateInstanceByTheFactory(field);

        if (localInstance != null) {
            return localInstance;
        }

        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(typeOfClass.getClassOfField(field));
        enhancer.setCallback(new InterceptorMethod(typeOfClass, this));

        return enhancer.create();

    }

    @Override
    public void injectObjecstInComponentClass(Class<?> clazz, Object instance) {
        Arrays.asList(clazz.getDeclaredFields()).stream()
                .filter(field -> field.getAnnotation(Inject.class) != null)
                .forEach(field -> injectInstanceField(instance, field, generateInstance(field)));
    }

    @Override
    public void injectInstanceField(Object main, Field field, Object instance) {

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

    @Override
    public Object getSingletonObject(Capsule capsule) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        if (capsule.getMethod().getAnnotation(br.com.climb.cdi.annotations.Singleton.class) != null) {

            final Object singleton = singletonsObjects.get(capsule.getMethod().getReturnType());

            if (singleton != null) {
                return singleton;
            } else {
                final Object instance = capsule.getClassFactory().getDeclaredConstructor().newInstance();
                injectObjecstInComponentClass(capsule.getClassFactory(), instance);
                final Object resultInvoke = capsule.getMethod().invoke(instance);
                singletonsObjects.put(capsule.getMethod().getReturnType(), resultInvoke);
                disposes.addDisposeList(capsule, resultInvoke);
                return resultInvoke;
            }

        }

        return null;
    }

    private void validaComponent(Class clazz) {
        if (clazz.getDeclaredAnnotation(Component.class) == null) {
            new Error("Não é um componente valido. Classe: " + clazz);
        }
    }

    @Override
    public Object generateInstanceBase(Class<?> clazz, String sessionid) {

        validaComponent(clazz);

        Map<String, Map<Class<?>, Object>> sessionMap = initializer.getSessionObjects();
        Map<Class<?>, Object> sesseionInstance = sessionMap.get(sessionid);

        Object instance = null;

        if (sesseionInstance != null) {

            Object base = sesseionInstance.get(clazz);

            if (base != null) {
                final Object finalBase = base;
                Arrays.asList(clazz.getDeclaredFields()).stream()
                        .filter(field -> field.getAnnotation(ReCreate.class) != null)
                        .forEach(field -> injectInstanceField(finalBase, field, generateInstance(field)));
            }

            if (base == null) {
                base = generateInstanceBase(clazz);
                sesseionInstance.put(clazz, base);
                sessionMap.put(sessionid, sesseionInstance);
            }

            return base;

        } else {

            final Object base = generateInstanceBase(clazz);

            sesseionInstance = new HashMap<>();
            sesseionInstance.put(clazz, base);
            sessionMap.put(sessionid, sesseionInstance);

            return base;

        }
    }

    @Override
    public Object generateInstanceBase(Class<?> clazz) {

        validaComponent(clazz);

        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> proxy.invokeSuper(obj, args));

        final Object base = enhancer.create();
        injectObjecstInComponentClass(clazz, base);

        return base;

    }

}
