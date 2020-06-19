package br.com.climb.cdi;


import br.com.climb.framework.configuration.ConfigFile;

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
        return new Manager(this);
    }


}
