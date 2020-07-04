package br.com.climb.test.restclient;

import br.com.climb.commons.annotations.param.PathVariable;
import br.com.climb.restclient.annotation.RestClient;
import br.com.climb.test.model.Cliente;

public abstract class AbstractClienteRest {

    @RestClient(method = "GET", url = "/get/nome/{nome}/peso/{peso}/altura/{altura}/idade/{idade}/casado/{casado}/")
    public String getCliente(
            @PathVariable("nome") String nome,
            @PathVariable("peso") Double peso,
            @PathVariable("altura") Float altura,
            @PathVariable("idade") Long idade,
            @PathVariable("casado") Boolean casado
    ) {
        return null;
    }

}
