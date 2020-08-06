package br.com.climb.test.rpc;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.rpc.annotation.RpcClient;

@Component
public class RpcClientTeste {

    @RpcClient(chanelName = "rpcClientController", className = "RpcClientTeste", methodName = "somar")
    public Integer somar(int a, int b) {
        return null;
    }

    @RpcClient(chanelName = "rpcClientController", className = "RpcClientTeste", methodName = "subtrair")
    public Integer subtrair(int a, int b) {
        return null;
    }

}
