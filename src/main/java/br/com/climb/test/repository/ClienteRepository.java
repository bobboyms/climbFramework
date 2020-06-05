package br.com.climb.test.repository;

import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ResultIterator;
import br.com.climb.framework.annotations.Repository;
import br.com.climb.orm.annotation.Transaction;
import br.com.climb.orm.repository.ClimbRepository;
import br.com.climb.test.model.Cliente;

import javax.inject.Inject;

@Repository
public class ClienteRepository extends ClimbRepository<Cliente> {

    @Inject
    private ClimbConnection climbConnection;

    @Transaction
    public void executarRegra() {

        System.out.println("EXECUTA REGRA");
        Cliente cliente = new Cliente();
        climbConnection.save(cliente);
        System.out.println(cliente.getId());
    }

}
