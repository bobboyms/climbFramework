package br.com.climb.cdi.instances;

import br.com.climb.cdi.Initializer;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Message;
import br.com.climb.cdi.annotations.ReCreate;
import br.com.climb.cdi.clazz.TypeOfClass;
import br.com.climb.cdi.disposes.Disposes;
import br.com.climb.cdi.interceptor.InterceptorMethodCdi;
import br.com.climb.cdi.model.Capsule;
import br.com.climb.framework.messagesclient.MessageClientManager;
import net.sf.cglib.proxy.Enhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class InstancesManager implements Instances, InjectInstance, Singleton {

    private final Logger logger = LoggerFactory.getLogger(InstancesManager.class);

    private final Initializer initializer;
    private final Disposes disposes;
    private final TypeOfClass typeOfClass;

    private final Map<Class<?>, Object> singletonsObjects = new HashMap<>();

    public InstancesManager(Initializer initializer,
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

        if (isNull(capsule)) {
            return null;
        }

        try {

            final Object singletonInstance = getSingletonObject(capsule);

            if (nonNull(singletonInstance)) {
                return singletonInstance;
            }

            final Object instance = generateInstanceBase(capsule.getClassFactory());//capsule.getClassFactory().getDeclaredConstructor().newInstance();
            //injectObjecstInComponentClass(capsule.getClassFactory(), instance);

            final Object resultInvoke = capsule.getMethod().invoke(instance);

            disposes.addDisposeList(capsule, resultInvoke);

            return resultInvoke;

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException  e) {
            logger.error("{}", e);
        }

        return null;

    }

    @Override
    public Object generateInstance(Field field) {

        final Object localInstance = generateInstanceByTheFactory(field);

        if (localInstance != null) {
            return localInstance;
        }

        return generateInstanceBase(typeOfClass.getClassOfField(field));

    }

    /**
     * Cria instancia para tipos especiais.
     * Nesse caso, para o tipo menssageria
     * @param field
     * @return
     */
    @Override
    public Object generateInstanceMessage(Field field) {
        final Message message = field.getDeclaredAnnotation(Message.class);
        return new MessageClientManager(message.topicName(), initializer.getConfigFile());
    }

    @Override
    public void injectObjecstInComponentClass(Class<?> clazz, Object instance) {

        Arrays.asList(clazz.getDeclaredFields()).stream()
                .filter(field -> field.getAnnotation(Inject.class) != null)
                .forEach(field -> injectInstanceField(instance, field, generateInstance(field)));

        Arrays.asList(clazz.getDeclaredFields()).stream()
                .filter(field -> field.getAnnotation(Message.class) != null)
                .forEach(field -> injectInstanceField(instance, field, generateInstanceMessage(field)));

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
            logger.error("{}", e);
        }

    }

    @Override
    public Object getSingletonObject(Capsule capsule) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        if (capsule.getMethod().getAnnotation(br.com.climb.cdi.annotations.Singleton.class) != null) {

            final Object singleton = singletonsObjects.get(capsule.getMethod().getReturnType());

            if (nonNull(singleton)) {
                return singleton;
            } else {
                final Object instance = generateInstanceBase(capsule.getClassFactory());//capsule.getClassFactory().getDeclaredConstructor().newInstance();
                //injectObjecstInComponentClass(capsule.getClassFactory(), instance);
                final Object resultInvoke = capsule.getMethod().invoke(instance);
                singletonsObjects.put(capsule.getMethod().getReturnType(), resultInvoke);
                disposes.addDisposeList(capsule, resultInvoke);
                return resultInvoke;
            }

        }

        return null;
    }



    @Override
    public Object generateInstanceBase(Class<?> clazz, String sessionid) {

        final Map<String, Map<Class<?>, Object>> sessionMap = initializer.getSessionObjects();
        Map<Class<?>, Object> sessionInstance = sessionMap.get(sessionid);

        Object base;

        if (nonNull(sessionInstance)) {

            base = sessionInstance.get(clazz);

            if (nonNull(base)) {
                final Object finalBase = base;
                Arrays.asList(clazz.getDeclaredFields()).stream()
                        .filter(field -> field.getAnnotation(ReCreate.class) != null)
                        .forEach(field -> injectInstanceField(finalBase, field, generateInstance(field)));
            } else {
                base = generateInstanceBase(clazz);
                sessionInstance.put(clazz, base);
                sessionMap.put(sessionid, sessionInstance);
            }

        } else {

            base = generateInstanceBase(clazz);

            sessionInstance = new HashMap<>();
            sessionInstance.put(clazz, base);
            sessionMap.put(sessionid, sessionInstance);

        }

        return base;
    }

    @Override
    public Object generateInstanceBase(Class<?> clazz) {


        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new InterceptorMethodCdi(typeOfClass, this));

        final Object base = enhancer.create();
        injectObjecstInComponentClass(clazz, base);

        return base;

    }

}
