package br.com.climb.test.controller.java.component;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.test.controller.java.rpc.EstoqueRpc;

@Component
public class VendaService {

    @Inject
    private EstoqueRpc estoqueRpc;

    public void setEstoqueRpc(EstoqueRpc estoqueRpc) {
        this.estoqueRpc = estoqueRpc;
    }

    public EstoqueRpc getEstoqueRpc() {
        return estoqueRpc;
    }

    public void efetuarVenda(Long id) {
        getEstoqueRpc().consultarEstoqueProduto(id);
        System.out.println("Venda: " + id);
    }

}
