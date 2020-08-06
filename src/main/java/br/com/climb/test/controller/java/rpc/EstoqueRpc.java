package br.com.climb.test.controller.java.rpc;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.rpc.annotation.RpcClient;

@Component
public class EstoqueRpc {

    @RpcClient(chanelName = "estoqueRpc", className = "teste" , methodName = "consultarEstoqueProduto")
    public Float consultarEstoqueProduto(Long codigoProduto) {
        return null;
    }

}
