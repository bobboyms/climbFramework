package br.com.climb.test.controller.java;

import br.com.climb.framework.annotations.RequestMapping;
import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.annotations.mapping.PutMapping;
import br.com.climb.framework.annotations.param.PathVariable;
import br.com.climb.framework.annotations.param.RequestBody;
import br.com.climb.test.model.Cliente;

@RestController
@RequestMapping("/put")
public class TestePutController {

    @PutMapping("/pessoa/")
    public Cliente teste1(@RequestBody Cliente cliente) {
        return cliente;
    }

    @PutMapping("/pessoa/{id}/")
    public Cliente teste2(@PathVariable("id") Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        return cliente;
    }


}
