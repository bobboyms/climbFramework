package br.com.climb.cdi.instances;

import br.com.climb.cdi.exception.ValidationException;

import java.lang.reflect.Field;

public interface Instances {

    Object generateInstance(Field field);
    Object generateInstanceByTheFactory(Field field);
    Object generateInstanceBase(Class<?> clazz);
    Object generateInstanceMessage(Field field);
    Object generateInstanceBase(Class<?> clazz, String sessionid);

}
