package br.com.climb.test.controller.java;

import br.com.climb.cdi.annotations.Inject;
import br.com.climb.commons.annotations.RequestMapping;
import br.com.climb.commons.annotations.RestController;
import br.com.climb.commons.annotations.mapping.GetMapping;
import br.com.climb.commons.annotations.param.PathVariable;
import br.com.climb.commons.annotations.param.RequestBody;
import br.com.climb.commons.annotations.param.RequestParam;
import br.com.climb.commons.security.Response;
import br.com.climb.commons.security.Security;
import br.com.climb.test.model.Cliente;
import br.com.climb.test.restclient.RestClient;
import br.com.climb.test.rpc.GenericRpcClient;


@RestController
@RequestMapping("/get/rpc")
public class TesteGetControllerRest {

    @Inject
    private GenericRpcClient rpcClient;

    public void setRpcClient(GenericRpcClient rpcClient) {
        this.rpcClient = rpcClient;
    }

    @GetMapping("/soma/valora/{valora}/valorb/{valorb}/")
    public String getSoma(@PathVariable("valora") Integer valora, @PathVariable("valorb") Integer valorb)  {
        Integer result = rpcClient.somar(valora,valorb);
        return result.toString();
    }


}
