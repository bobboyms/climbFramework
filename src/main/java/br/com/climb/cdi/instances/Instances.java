package br.com.climb.cdi.instances;

import java.lang.reflect.Field;

public interface Instances {

    Object generateInstance(Field field);
    Object generateInstanceByTheFactory(Field field);
    Object generateInstanceBase(Class<?> clazz);

}
