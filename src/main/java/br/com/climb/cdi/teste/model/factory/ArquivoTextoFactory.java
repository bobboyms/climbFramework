package br.com.climb.cdi.teste.model.factory;

import br.com.climb.cdi.annotations.*;
import br.com.climb.cdi.teste.model.Pessoa;

@Factory
public class ArquivoTextoFactory {

    @Inject
    private Pessoa pessoa;

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Produces
    @Singleton
    public ArquivoTexto getArquivoTexto() {
        System.out.println("Criou arquivo de texto via factory");
        System.out.println("Injetou pessoa na fabrica: " + pessoa);
        return new ArquivoTexto();
    }

    @Disposes
    public void fecharArquivo(ArquivoTexto arquivoTexto) {
        arquivoTexto.close();
    }


}
