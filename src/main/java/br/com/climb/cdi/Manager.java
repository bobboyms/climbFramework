package br.com.climb.cdi;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Interceptor;
import br.com.climb.cdi.annotations.Singleton;
import br.com.climb.cdi.model.Capsule;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class Manager implements ManagerContext {

    private Map<Class<?>, Object> singletonsObjects = new HashMap<>();
    private Set<Object> disposesObjects = new HashSet<>();
    private Initializer initializer;

    public Manager(Initializer initializer) {
        this.initializer = initializer;
    }

    protected Class<?> getQualifierClass(Field field) {

        final List<Class<?>> listConcreteClassInterface = initializer.getConcreteInterfaceClasses().get(field.getType());

        if (listConcreteClassInterface != null && listConcreteClassInterface.size() > 1) {
            final String qualifier  = field.getAnnotation(Inject.class).value();
            final List<Class<?>> listClazzTemp = listConcreteClassInterface.stream().filter(aClass -> ((Component) aClass.getAnnotation(Component.class)).value().equals(qualifier))
                    .collect(Collectors.toList());
            return listClazzTemp.get(0);
        }

        return initializer.getConcreteInterfaceClasses().get(field.getType()).get(0);

    }

    protected Class<?> getClassOfField(Field field) {

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
                addDisposeList(capsule, resultInvoke);
                return resultInvoke;
            }

        }

        return null;
    }

    public void addDisposeList(Capsule capsule, Object resultInvoke) {

        if (isDisposes(resultInvoke.getClass())) {
            disposesObjects.add(resultInvoke);
        }
    }

    protected Object generateInstanceByTheFactory(Field field) {

        final Capsule capsule = initializer.getFactoriesClasses().get(field.getType());

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

            final Object resultInvoke = capsule.getMethod().invoke(instance);

            addDisposeList(capsule, resultInvoke);

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

    private Class getInterceptorClass(Method method) {

        List<Class> listInterceptorClass =  Arrays.asList(method.getAnnotations()).stream()
                .map(annotation -> annotation.annotationType())
                .filter(aClass -> initializer.getInterceptorClasses().get(aClass) != null)
                .map(aClass -> initializer.getInterceptorClasses().get(aClass)).collect(Collectors.toList());

        if (listInterceptorClass != null && listInterceptorClass.size() > 0) {
            return listInterceptorClass.get(0);
        }

        return null;


    }

    protected Object generateInstance(Field field) {

        final Object localInstance = generateInstanceByTheFactory(field);

        if (localInstance != null) {
            return localInstance;
        }

        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(getClassOfField(field));
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {

            final Class<?> interceptClass = getInterceptorClass(method);

            if (interceptClass != null) {
                final Object instanceInterceptor = interceptClass.cast(generateInstanceBase(interceptClass));
                return ((MethodIntercept)instanceInterceptor).interceptorMethod(new InvocationContext(obj,method,args,proxy));

//                proxy.invokeSuper(obj, args);
            }

            if (method.getName().contains("get")) {

                final Class<?> returnType = method.getReturnType();
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

    private void injectObjecstInComponentClass(Class<?> clazz, Object instance) {
        Arrays.asList(clazz.getDeclaredFields()).stream()
                .filter(field -> field.getAnnotation(Inject.class) != null)
                .forEach(field -> injectInstanceField(instance, field, generateInstance(field)));
    }

    public Object generateInstanceBase(Class<?> clazz) {

        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> proxy.invokeSuper(obj, args));

        final Object base = enhancer.create();
        injectObjecstInComponentClass(clazz, base);

        return base;

    }

    /**
     * Verifica se é um tipo que vai ser dispensado quando chamar o method
     * close
     * @param clazz
     * @return
     */
    private boolean isDisposes(Class clazz) {

        final Object result = initializer.getDisposesMethods().get(clazz);

        if (result != null) {
            return true;
        }

        final List<Class> classes = Arrays.asList(clazz.getInterfaces()).stream()
                .filter(aClass -> initializer.getDisposesMethods().get(aClass) != null).collect(Collectors.toList());

        if (classes.size() > 0) {
            return true;
        }

        final List<Type> ifaces = Arrays.asList(clazz.getSuperclass().getGenericInterfaces()).stream()
                .filter(aClass -> initializer.getDisposesMethods().get(aClass) != null).collect(Collectors.toList());

        if (ifaces.size() > 0) {
            return true;
        }

        return false;
    }

    @Override
    public Object generateInstance(Class<?> aClass) {
        return generateInstanceBase(aClass);
    }

    @Override
    public void disposeObjects() {

        disposesObjects.stream().forEach(object -> {

            Capsule capsule = initializer.getDisposesMethods().get(object.getClass());

            if (capsule == null) {

                final List<Type> ifaces = Arrays.asList(object.getClass().getSuperclass().getGenericInterfaces()).stream()
                        .filter(aClass -> initializer.getDisposesMethods().get(aClass) != null).collect(Collectors.toList());

                if (ifaces.size() > 0) {
                    capsule = initializer.getDisposesMethods().get(ifaces.get(0));
                }
            }

            try {

                final Object instance = capsule.getClassFactory().getDeclaredConstructor().newInstance();
                capsule.getMethod().invoke(instance,object);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public void close() throws Exception {
        disposeObjects();
    }
}
