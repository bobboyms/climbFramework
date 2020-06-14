package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.ReCreate;
import br.com.climb.cdi.annotations.Session;
import br.com.climb.cdi.teste.model.factory.ArquivoTexto;
import br.com.climb.core.interfaces.ClimbConnection;

@Session
@Component
public class CadastroClienteController {

    private Long valor = 0l;

    public Long getValor() {
        return valor++;
    }

    @Inject
    private Carro carro;

    @ReCreate
    @Inject("pessoa")
    private Repository pessoaRepository;


    @Inject
    private ArquivoTexto arquivoTexto;

    public Repository getPessoaRepository() {
        return pessoaRepository;
    }

    public void setArquivoTexto(ArquivoTexto arquivoTexto) {
        this.arquivoTexto = arquivoTexto;
    }


    public void setPessoaRepository(Repository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }


    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Carro getCarro() {
        return carro;
    }

    public void executar() {
        System.out.println("****** iniciou executar ********");
        System.out.println(carro.getNome());
        pessoaRepository.salvar(new Pessoa());
        System.out.println(pessoaRepository.processar("o valor da vida"));
        arquivoTexto.gerar();
    }

}
