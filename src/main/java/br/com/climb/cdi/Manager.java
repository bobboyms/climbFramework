package br.com.climb.cdi;

import br.com.climb.cdi.clazz.TypeOfClass;
import br.com.climb.cdi.disposes.Disposes;
import br.com.climb.cdi.instances.Instances;

public class Manager implements ManagerContext {

    private final Initializer initializer;
    private final Disposes disposes;
    private final TypeOfClass typeOfClass;
    private final Instances instances;

    public Manager(Initializer initializer, Disposes disposes, TypeOfClass typeOfClass, Instances instances) {
        this.initializer = initializer;
        this.disposes = disposes;
        this.typeOfClass = typeOfClass;
        this.instances = instances;
    }

    @Override
    public void disposeObjects() {
        disposes.disposeObjects();
    }

    @Override
    public Object generateInstance(Class<?> aClass) {
        return instances.generateInstanceBase(aClass);
    }

    @Override
    public Object generateInstance(Class<?> aClass, String sessionid) {
        return instances.generateInstanceBase(aClass, sessionid);
    }

    @Override
    public void close() throws Exception {
        disposeObjects();
    }
}
