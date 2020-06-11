package br.com.climb.cdi.teste.model.factory;

import br.com.climb.cdi.annotations.Factory;
import br.com.climb.cdi.annotations.Produces;
import br.com.climb.core.ClimbORM;
import br.com.climb.core.interfaces.ManagerFactory;


@Factory
public class ManagerFactoryConfiguration {

    @Produces
    public ManagerFactory getManagerFactory() {
        return ClimbORM.createManagerFactory("climb.properties");
    }

}
