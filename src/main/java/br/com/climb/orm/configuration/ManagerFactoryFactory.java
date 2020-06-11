package br.com.climb.orm.configuration;

import br.com.climb.cdi.annotations.Factory;
import br.com.climb.cdi.annotations.Produces;
import br.com.climb.core.ClimbORM;


@Factory
public class ManagerFactoryFactory {

    @Produces
    public br.com.climb.core.interfaces.ManagerFactory getManagerFactory() {
        return ClimbORM.createManagerFactory("climb.properties");
    }

}
