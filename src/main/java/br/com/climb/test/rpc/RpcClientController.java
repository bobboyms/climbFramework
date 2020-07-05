package br.com.climb.test.rpc;

import br.com.climb.rpc.annotation.RpcController;
import br.com.climb.rpc.annotation.RpcMethod;

@RpcController("rpcClientController")
public class RpcClientController {

    @RpcMethod(methodName = "somar")
    public Integer somar(int a, int b) {
        return a + b;
    }

    @RpcMethod(methodName = "subtrair")
    public Integer subtrair(int a, int b) {
        return a - b;
    }



}
