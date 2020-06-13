package br.com.climb.test.controller;

import br.com.climb.framework.annotations.RequestMapping;
import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.annotations.mapping.GetMapping;
import br.com.climb.framework.annotations.mapping.PostMapping;
import br.com.climb.framework.annotations.param.PathVariable;
import br.com.climb.framework.annotations.param.RequestBody;
import br.com.climb.framework.annotations.param.RequestParam;
import br.com.climb.test.model.Cliente;

@RestController
@RequestMapping("/post")
public class TestePostController {

    @PostMapping("/pessoa/")
    public Cliente teste1(@RequestBody Cliente cliente) {
        return cliente;
    }

    @PostMapping("/pessoa/{id}/")
    public Cliente teste2(@PathVariable("id") Long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        return cliente;
    }


}
