package br.com.climb.test.controller;

import br.com.climb.core.interfaces.ResultIterator;
import br.com.climb.framework.annotations.RequestMapping;
import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.annotations.mapping.GetMapping;
import br.com.climb.framework.annotations.param.PathVariable;
import br.com.climb.framework.annotations.param.RequestBody;
import br.com.climb.framework.utils.JwtUtil;
import br.com.climb.test.model.Cliente;
import br.com.climb.test.model.Response;
import br.com.climb.test.repository.ClienteRepository;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/get")
public class TesteGetController {

    @Inject
    private ClienteRepository clienteRepository;

    @Inject
    private HttpServletRequest request;

    @GetMapping("/")
    public Response teste1()  {

        System.out.println("request: " + request.getPathInfo());

        String tk = request.getHeader(JwtUtil.TOKEN_HEADER);
        System.out.println(tk);
        System.out.println(request.getRequestURL().toString());

        String userName = "thiago";

        Response response = new Response();

        String token = JwtUtil.create(userName);

        response.setToken(token);
        response.setUserName(userName);

        return response;
    }

    @GetMapping("/{id}/")
    public String teste1(@PathVariable("id") Long id, @RequestBody Cliente cliente)  {
        return "Olá mundo " + cliente.getNome();
    }

    @GetMapping("/nome/{nome}/peso/{peso}/altura/{altura}/idade/{idade}/casado/{casado}/")
    public Cliente teste2(
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

        ResultIterator resultIterator = clienteRepository.find();

//        while (resultIterator.next()) {
//            System.out.println((Cliente)resultIterator.getObject());
//        }

        clienteRepository.save(cliente);
        System.out.println("Fora: " + cliente);
        clienteRepository.executarRegra();

        return clienteRepository.findOne(30l);
    }

}
