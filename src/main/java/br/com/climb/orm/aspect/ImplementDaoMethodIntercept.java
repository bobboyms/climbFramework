package br.com.climb.orm.aspect;

import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.orm.annotation.ImplementDaoMethod;
import br.com.climb.orm.annotation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

@ImplementDaoMethod
@Interceptor
public class ImplementDaoMethodIntercept {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private ClimbConnection climbConnection;

    private Class<?> getTypeInterface(String className) {

        String classNameTemp = className.split("\\$")[0];

        try {
            return Class.forName(classNameTemp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Type findClassParameterType(Class<?> classe) {

        Type genericInterface = classe.getGenericSuperclass();
        if (genericInterface instanceof ParameterizedType) {
            Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
            for (Type genericType : genericTypes) {
                return genericType;
            }
        }

        return null;
    }

    @AroundInvoke
    public Object interceptorMethod(InvocationContext ctx) throws Exception {

        System.out.println("****** Dao Method Intercept ******");

        try {

            Object object = null;

            if (ctx.getParameters().length > 0) {
                object = ctx.getParameters()[0];
            }

            if (ctx.getMethod().getName().equals("save")) {
                save(object);
            }

            if (ctx.getMethod().getName().equals("update")) {
                update(object);
            }

            if (ctx.getMethod().getName().equals("delete")) {
                delete(object);
            }

            if (ctx.getMethod().getName().equals("delete")) {
                delete(object);
            }

            if (ctx.getMethod().getName().equals("findOne")) {
                final Type type = findClassParameterType(getTypeInterface(ctx.getTarget().toString()));
                return findOne(type, (Long)object);
            }

            if (ctx.getMethod().getName().equals("find")) {
                final Type type = findClassParameterType(getTypeInterface(ctx.getTarget().toString()));
                return find(type);
            }

            return object;

        } catch (Exception e) {
            logger.error("ImplementDaoMethodIntercept ERROR: {}", e);
        }

        return null;
    }

    private Object findOne(Type type, Long id) {
        return climbConnection.findOne((Class) type, id);
    }

    private Object find(Type type) {
        return climbConnection.find((Class) type);
    }

    private void delete(Object object) {
        climbConnection.delete(object);
    }

    private void update(Object object) {
        climbConnection.update(object);
    }

    public void save(Object object) {
        climbConnection.save(object);
    }


}
