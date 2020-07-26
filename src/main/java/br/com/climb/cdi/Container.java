package br.com.climb.cdi;


import br.com.climb.cdi.clazz.TypeOfClass;
import br.com.climb.cdi.clazz.TypeOfClassManager;
import br.com.climb.cdi.disposes.Disposes;
import br.com.climb.cdi.disposes.DisposesManager;
import br.com.climb.cdi.instances.InstancesManager;
import br.com.climb.commons.configuration.ConfigFile;

public class Container extends ContainerInitializer {

    public Container(ConfigFile configFile) {
        try {
            super.setConfigFile(configFile);
            startFactories();
            startInterceptors();
            startDisposesClass();
            initialiseConcreteInterfaceClasses();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ManagerContext createManager() {
        final Disposes disposes = DisposesManager.create(this);
        final TypeOfClass typeOfClass = TypeOfClassManager.create(this);
        return new Manager(this, disposes, typeOfClass, InstancesManager.create(this, disposes, typeOfClass));
    }

    @Override
    public ConfigFile getConfigFile() {
        return super.configFile;
    }
}
