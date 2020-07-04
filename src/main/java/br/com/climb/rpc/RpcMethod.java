package br.com.climb.rpc;

import br.com.climb.framework.exceptions.MethodCallException;

public interface RpcMethod {
    Object methodCall(Object[] args) throws MethodCallException;
}
