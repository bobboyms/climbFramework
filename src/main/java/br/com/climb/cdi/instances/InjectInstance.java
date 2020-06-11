package br.com.climb.cdi.instances;

import java.lang.reflect.Field;

public interface InjectInstance {
    void injectObjecstInComponentClass(Class<?> clazz, Object instance);
    void injectInstanceField(Object main, Field field, Object instance);
}
