package br.com.climb.test.controller.java;

import br.com.climb.cdi.annotations.Inject;
import br.com.climb.commons.annotations.RequestMapping;
import br.com.climb.commons.annotations.RestController;
import br.com.climb.commons.annotations.mapping.GetMapping;
import br.com.climb.commons.annotations.param.PathVariable;
import br.com.climb.test.rpc.RpcClientController;
import br.com.climb.test.rpc.RpcClientTeste;


@RestController
@RequestMapping("/get/rpc")
public class TesteGetControllerRest {

    @Inject
    private RpcClientTeste rpcClient;

    public void setRpcClient(RpcClientTeste rpcClient) {
        this.rpcClient = rpcClient;
    }

    @GetMapping("/soma/valora/{valora}/valorb/{valorb}/")
    public String somar(@PathVariable("valora") Integer valora, @PathVariable("valorb") Integer valorb)  {
        Integer result = rpcClient.somar(valora,valorb);
        return result.toString();
    }

    @GetMapping("/subtrair/valora/{valora}/valorb/{valorb}/")
    public String subtrair(@PathVariable("valora") Integer valora, @PathVariable("valorb") Integer valorb)  {
        Integer result = rpcClient.subtrair(valora,valorb);
        return result.toString();
    }


}
