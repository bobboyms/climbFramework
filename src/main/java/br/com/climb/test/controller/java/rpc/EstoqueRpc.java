package br.com.climb.test.controller.java.rpc;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.rpc.annotation.RpcClient;

@Component
public class EstoqueRpc {

    @RpcClient(controllerName = "estoqueRpc", methodName = "consultarEstoqueProduto")
    public Float consultarEstoqueProduto(Long codigoProduto) {
        return null;
    }

}
