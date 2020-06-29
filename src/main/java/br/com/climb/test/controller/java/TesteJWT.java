package br.com.climb.test.controller.java;

import br.com.climb.commons.annotations.RequestMapping;
import br.com.climb.commons.annotations.RestController;
import br.com.climb.commons.annotations.mapping.GetMapping;

@RestController
@RequestMapping("/api")
public class TesteJWT {

    @GetMapping("/teste/")
    public void teste1() {
        System.out.println("url jwt");
    }

}
