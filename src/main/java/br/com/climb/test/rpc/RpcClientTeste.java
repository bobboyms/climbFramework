package br.com.climb.test.rpc;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.rpc.annotation.RpcClient;

@Component
public class RpcClientTeste {

    @RpcClient(controllerName = "rpcClientController", methodName = "somar")
    public Integer somar(int a, int b) {
        return null;
    }

    @RpcClient(controllerName = "rpcClientController", methodName = "subtrair")
    public Integer subtrair(int a, int b) {
        return null;
    }

}
