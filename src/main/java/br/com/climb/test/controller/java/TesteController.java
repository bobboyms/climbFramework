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
import br.com.climb.rpc.annotation.RpcClient;
import br.com.climb.test.controller.java.component.VendaService;
import br.com.climb.test.model.Cliente;


@RestController
@RequestMapping("/v1/teste/component")
public class TesteController {

    @Inject
    private VendaService vendaService;

    public void setVendaService(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @GetMapping("/")
    public String teste1()  {
        vendaService.efetuarVenda(100l);
        return "ok";
    }

}
