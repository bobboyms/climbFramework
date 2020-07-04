package br.com.climb.test.rpc;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.rpc.annotation.RpcClient;

@Component
public class GenericRpcClient {

    @RpcClient(methodName = "somar")
    public Integer somar(int a, int b) {
        return null;
    }

}
