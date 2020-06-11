package br.com.climb.cdi.instances;

import br.com.climb.cdi.model.Capsule;

import java.lang.reflect.InvocationTargetException;

public interface Singleton {

    Object getSingletonObject(Capsule capsule) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException;
}
