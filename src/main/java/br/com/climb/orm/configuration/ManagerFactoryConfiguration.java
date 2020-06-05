package br.com.climb.orm.configuration;

import br.com.climb.core.ClimbORM;
import br.com.climb.core.interfaces.ManagerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Singleton
public class ManagerFactoryConfiguration {

    @Produces
    public ManagerFactory getManagerFactory() {
        return ClimbORM.createManagerFactory("climb.properties");
    }

}
