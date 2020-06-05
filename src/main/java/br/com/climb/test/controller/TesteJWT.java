package br.com.climb.test.controller;

import br.com.climb.framework.annotations.RequestMapping;
import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.annotations.mapping.GetMapping;
import br.com.climb.framework.annotations.mapping.PutMapping;
import br.com.climb.framework.annotations.param.PathVariable;
import br.com.climb.framework.annotations.param.RequestBody;
import br.com.climb.test.model.Cliente;

@RestController
@RequestMapping("/api")
public class TesteJWT {

    @GetMapping("/teste/")
    public void teste1() {
        System.out.println("url jwt");
    }



}
