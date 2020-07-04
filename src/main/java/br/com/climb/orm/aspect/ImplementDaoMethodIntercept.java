package br.com.climb.orm.aspect;

import br.com.climb.cdi.interceptor.Context;
import br.com.climb.cdi.interceptor.MethodIntercept;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Interceptor;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.orm.annotation.ImplementDaoMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@ImplementDaoMethod
@Interceptor
public class ImplementDaoMethodIntercept implements MethodIntercept {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private ClimbConnection climbConnection;

    public void setClimbConnection(ClimbConnection climbConnection) {
        this.climbConnection = climbConnection;
    }

    private Class<?> getTypeInterface(String className) {

        try {
            return Class.forName(className.replace("class ", "").trim());
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

    @Override
    public Object interceptorMethod(Context ctx) throws Exception {

        System.out.println("****** Dao Method Intercept ******");

        try {

            Object object = null;

            if (ctx.getArgs().length > 0) {
                object = ctx.getArgs()[0];
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
                final Type type = findClassParameterType(getTypeInterface(ctx.getaClass().toString()));
                return findOne(type, (Long)object);
            }

            if (ctx.getMethod().getName().equals("find")) {
                final Type type = findClassParameterType(getTypeInterface(ctx.getaClass().toString()));
                return find(type);
            }

            return object;

        } catch (Exception e) {
            logger.error("ImplementRestIntercept ERROR: {}", e);
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
