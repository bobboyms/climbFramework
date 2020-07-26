package br.com.climb.cdi.interceptor;

import br.com.climb.cdi.clazz.TypeOfClass;
import br.com.climb.cdi.instances.Instances;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

import static java.util.Objects.nonNull;

public class InterceptorMethod implements MethodInterceptor {

    private final TypeOfClass typeOfClass;
    private final Instances instances;

    public InterceptorMethod(TypeOfClass typeOfClass, Instances instances) {
        this.typeOfClass = typeOfClass;
        this.instances = instances;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

        final Class<?> interceptClass = typeOfClass.getInterceptorClass(method);

        if (nonNull(interceptClass)) {
            final Object instanceInterceptor = interceptClass.cast(instances.generateInstanceBase(interceptClass));
            return ((MethodIntercept)instanceInterceptor).interceptorMethod(new InvocationContext(obj,method,args,proxy));
        }

//        if (method.getName().contains("get")) {
//
//            final Class<?> returnType = method.getReturnType();
//            final Object result = proxy.invokeSuper(obj, args);
//
//            if (result == null && returnType.getAnnotation(Component.class) != null) {
//                return instances.generateInstanceBase(method.getReturnType());
//            }
//
//            return result;
//        }

        return proxy.invokeSuper(obj, args);

    }
}
