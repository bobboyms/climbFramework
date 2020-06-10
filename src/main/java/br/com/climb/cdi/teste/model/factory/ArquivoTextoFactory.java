package br.com.climb.cdi.teste.model.factory;

import br.com.climb.cdi.annotations.Disposes;
import br.com.climb.cdi.annotations.Factory;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Produces;
import br.com.climb.cdi.teste.model.Pessoa;

@Factory
public class ArquivoTextoFactory {

    @Inject
    private Pessoa pessoa;

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Produces
    public ArquivoTexto getArquivoTexto() {
        System.out.println("Tem pessoa? : " + pessoa);
        return new ArquivoTexto();
    }

    @Disposes
    public void fecharArquivo(ArquivoTexto arquivoTexto) {
        arquivoTexto.close();
    }


}
