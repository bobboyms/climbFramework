package br.com.climb.test.controller;

import br.com.climb.cdi.annotations.Inject;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.framework.annotations.RequestMapping;
import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.annotations.mapping.GetMapping;
import br.com.climb.framework.annotations.param.PathVariable;
import br.com.climb.framework.annotations.param.RequestBody;
import br.com.climb.framework.annotations.param.RequestParam;
import br.com.climb.framework.utils.JwtUtil;
import br.com.climb.test.model.Cliente;
import br.com.climb.test.model.Response;
import br.com.climb.test.repository.ClienteRepository;


@RestController
@RequestMapping("/get")
public class TesteGetController {

//    @GetMapping("/{id}/")
//    public Response teste1(@PathVariable("id") Long id)  {
//
//        Response response = new Response();
//        String token = JwtUtil.create(id.toString());
//
//        response.setToken(token);
//        response.setUserName(id.toString());
//
//        return response;
//    }

    @GetMapping("/id/{id}/")
    public String teste2(@PathVariable("id") Long id, @RequestBody Cliente cliente)  {
        return "Olá mundo " + cliente.getNome();
    }

    @GetMapping("/pesoa/id/peso/")
    public String teste3(@RequestParam("id") String id, @RequestParam("peso") Double peso)  {
        return id + " " + peso;
    }

    @GetMapping("/pesoa/id/peso/pessoa/")
    public String teste3(@RequestParam("id") String id, @RequestParam("peso") Double peso, @RequestBody Cliente cliente)  {
        System.out.println(cliente);
        return id + " " + peso;
    }

    @GetMapping("/nome/{nome}/peso/{peso}/altura/{altura}/idade/{idade}/casado/{casado}/")
    public Cliente teste4(
            @PathVariable("nome") String nome,
            @PathVariable("peso") Double peso,
            @PathVariable("altura") Float altura,
            @PathVariable("idade") Long idade,
            @PathVariable("casado") Boolean casado
    ) {

        Cliente cliente = new Cliente();
        cliente.setCasado(casado);
        cliente.setNome(nome);
        cliente.setPeso(peso);
        cliente.setAltura(altura);
        cliente.setIdade(idade);

        return cliente;
    }

}
