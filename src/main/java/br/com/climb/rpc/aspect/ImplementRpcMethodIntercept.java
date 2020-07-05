package br.com.climb.rpc.aspect;

import br.com.climb.cdi.annotations.Interceptor;
import br.com.climb.cdi.interceptor.Context;
import br.com.climb.cdi.interceptor.MethodIntercept;
import br.com.climb.commons.model.rpc.RpcResponse;
import br.com.climb.framework.ClimbApplication;
import br.com.climb.rpc.RpcMethod;
import br.com.climb.rpc.RpcSendManager;
import br.com.climb.rpc.annotation.RpcClient;
import br.com.climb.rpc.annotation.RpcController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RpcClient
@Interceptor
public class ImplementRpcMethodIntercept implements MethodIntercept {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object interceptorMethod(Context ctx) throws Exception {

        try {

            RpcClient rpcClient = ctx.getMethod().getDeclaredAnnotation(RpcClient.class);
            RpcMethod rpcMethod = new RpcSendManager(rpcClient.controllerName(), rpcClient.methodName(), ClimbApplication.configFile);
            RpcResponse object = (RpcResponse) rpcMethod.methodCall(ctx.getArgs());

            return object.getResponse();

        } catch (Exception e) {
            logger.error("ImplementRpcMethodIntercept ERROR: {}", e);
        }

        return null;
    }

}
