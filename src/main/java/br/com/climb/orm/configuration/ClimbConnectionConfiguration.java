package br.com.climb.orm.configuration;

import br.com.climb.cdi.annotations.*;
import br.com.climb.core.ClimbORM;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.exception.SgdbException;
import br.com.climb.framework.JettyServer;

import java.io.Serializable;


@Factory
public class ClimbConnectionConfiguration implements Serializable {

    @Inject
    private ManagerFactory managerFactory;

    public void setManagerFactory(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    @Produces
    public ClimbConnection getConnection() throws SgdbException {
        return managerFactory.getConnection();
    }

    @Disposes
    public void closeConnection(ClimbConnection connection) {
        System.out.println("Fechou");
        System.out.println("fx: " + connection);
        connection.close();
    }

}
