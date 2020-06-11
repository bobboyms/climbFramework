package br.com.climb.cdi.teste.model.factory;

import br.com.climb.cdi.annotations.Disposes;
import br.com.climb.cdi.annotations.Factory;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Produces;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.exception.SgdbException;

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
