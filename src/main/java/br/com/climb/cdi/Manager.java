package br.com.climb.cdi;

import br.com.climb.cdi.clazz.TypeOfClass;
import br.com.climb.cdi.clazz.TypeOfClassManager;
import br.com.climb.cdi.disposes.Disposes;
import br.com.climb.cdi.disposes.DisposesManager;
import br.com.climb.cdi.instances.Instances;
import br.com.climb.cdi.instances.InstancesManager;

public class Manager implements ManagerContext {

    private Initializer initializer;
    private Disposes disposes;
    private TypeOfClass typeOfClass;
    private Instances instances;

    public Manager(Initializer initializer) {
        this.initializer = initializer;
        this.disposes = DisposesManager.create(initializer);
        this.typeOfClass = TypeOfClassManager.create(initializer);
        this.instances = InstancesManager.create(initializer,disposes, typeOfClass);
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
    public void close() throws Exception {
        disposeObjects();
    }
}
