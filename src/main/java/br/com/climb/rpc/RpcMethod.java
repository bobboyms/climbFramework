package br.com.climb.rpc;

import br.com.climb.framework.exceptions.MethodCallException;
import br.com.climb.rpc.exceptions.RpcException;

public interface RpcMethod {
    Object methodCall(Object[] args) throws RpcException;
}
