package br.com.climb.orm.configuration;

import br.com.climb.cdi.annotations.*;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.exception.SgdbException;

import java.io.Serializable;


@Factory
public class ClimbConnectionFactory implements Serializable {

    @Inject
    private ManagerFactory managerFactory;

    public void setManagerFactory(ManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    @Produces
    @Singleton
    public ClimbConnection getConnection() throws SgdbException {
        return managerFactory.getConnection();
    }

    @Disposes
    public void closeConnection(ClimbConnection connection) {
        connection.close();
    }

}
