package br.com.climb.test.controller.java;

import br.com.climb.commons.annotations.RequestMapping;
import br.com.climb.commons.annotations.RestController;
import br.com.climb.commons.annotations.mapping.DeleteMapping;
import br.com.climb.commons.annotations.param.PathVariable;
import br.com.climb.commons.annotations.param.RequestBody;
import br.com.climb.test.model.Cliente;

@RestController
@RequestMapping("/delete")
public class TesteDeleteController {

    @DeleteMapping("/pessoa/")
    public Cliente teste1(@RequestBody Cliente cliente) {
        return cliente;
    }

    @DeleteMapping("/pessoa/{id}/")
    public Cliente teste2(@PathVariable("id") Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        return cliente;
    }


}
