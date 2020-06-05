package br.com.climb.test.controller;

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
        cliente.setNome("passou aqui");
        return cliente;
    }

    @PutMapping("/pessoa/{id}/")
    public Cliente teste2(@PathVariable("id") Long id, @RequestBody Cliente cliente) {
        cliente.setNome("Pessou por aki " + id);
        return cliente;
    }


}
