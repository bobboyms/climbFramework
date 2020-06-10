package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.teste.model.factory.ArquivoTexto;

@Component
public class Controller {

    @Inject
    private Pessoa pessoa;

    @Inject
    private Carro carro;

    @Inject("pessoa")
    private Repository pessoaRepository;

    @Inject("carro")
    private Repository carroRepository;

    @Inject
    private ArquivoTexto arquivoTexto;

    public void setArquivoTexto(ArquivoTexto arquivoTexto) {
        this.arquivoTexto = arquivoTexto;
    }

    public void setCarroRepository(Repository carroRepository) {
        this.carroRepository = carroRepository;
    }

    public void setPessoaRepository(Repository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Carro getCarro() {
        return carro;
    }

    public void executar() {
        System.out.println("****** iniciou executar ********");
        System.out.println(pessoa.getNome());
        System.out.println(carro.getNome());
        carroRepository.salvar(carro);
        pessoaRepository.salvar(pessoa);
        arquivoTexto.gerar();
    }

}
