package br.com.climb.orm.configuration;

import br.com.climb.core.ClimbORM;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.exception.SgdbException;
import br.com.climb.framework.JettyServer;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;


public class ClimbConnectionConfiguration implements Serializable {

    @Inject
    private ManagerFactory managerFactory;

    @Produces
    @Singleton
    public ClimbConnection getConnection() throws SgdbException {
        return managerFactory.getConnection();
    }

    public void closeConnection(@Disposes ClimbConnection connection) {
        System.out.println("Fechou");
        System.out.println("fx: " + connection);
        connection.close();
    }

}
