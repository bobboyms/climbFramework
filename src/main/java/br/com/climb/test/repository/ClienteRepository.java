package br.com.climb.test.repository;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.commons.annotations.Repository;
import br.com.climb.orm.annotation.Transaction;
import br.com.climb.orm.repository.ClimbRepository;
import br.com.climb.test.model.Cliente;


@Component
public class ClienteRepository extends ClimbRepository<Cliente> {

    @Inject
    private ClimbConnection climbConnection;

    @Transaction
    public void executarRegra() {

//        System.out.println("EXECUTA REGRA");
//        Cliente cliente = new Cliente();
//        climbConnection.save(cliente);
//        System.out.println(cliente.getId());
    }

}
