package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.ReCreate;
import br.com.climb.cdi.annotations.Session;
import br.com.climb.cdi.teste.model.factory.ArquivoTexto;

@Session
@Component
public class CadastroCarroController {

    private Long valor = 0l;

    public Long getValor() {
        return valor++;
    }

    @Inject
    private Carro carro;

    @ReCreate
    @Inject("carro")
    private Repository carroRepository;

    @Inject
    private ArquivoTexto arquivoTexto;


    public void setCarroRepository(Repository carroRepository) {
        this.carroRepository = carroRepository;
    }

    public void setArquivoTexto(ArquivoTexto arquivoTexto) {
        this.arquivoTexto = arquivoTexto;
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
        carroRepository.salvar(new Pessoa());
        System.out.println(carroRepository.processar("o valor da vida"));
        arquivoTexto.gerar();
    }

}
