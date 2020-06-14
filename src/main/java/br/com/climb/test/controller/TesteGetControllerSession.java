package br.com.climb.test.controller;

import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Session;
import br.com.climb.cdi.teste.model.factory.ArquivoTexto;
import br.com.climb.framework.annotations.RequestMapping;
import br.com.climb.framework.annotations.RestController;
import br.com.climb.framework.annotations.mapping.GetMapping;

@Session
@RestController
@RequestMapping("/get/session")
public class TesteGetControllerSession {

    private Long contador = 0l;

    @Inject
    private ArquivoTexto arquivoTexto;

    public void setArquivoTexto(ArquivoTexto arquivoTexto) {
        this.arquivoTexto = arquivoTexto;
    }

    @GetMapping("/")
    public Long getContador() {
        return contador++;
    }
}
