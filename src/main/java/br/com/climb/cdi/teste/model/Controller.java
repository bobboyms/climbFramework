package br.com.climb.cdi.teste.model;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Message;
import br.com.climb.cdi.teste.model.factory.ArquivoTexto;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.framework.messagesclient.MessageClientProducer;
import br.com.climb.framework.messagesclient.restclient.exceptions.CreateTopicException;
import br.com.climb.test.model.BaixarEstoque;

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
    private ClimbConnection climbConnection;

    @Inject
    private ArquivoTexto arquivoTexto;

    @Message(topicName = "cliente")
    private MessageClientProducer messageClient;


    public void setMessageClient(MessageClientProducer messageClient) {
        this.messageClient = messageClient;
    }

    public void setClimbConnection(ClimbConnection climbConnection) {
        this.climbConnection = climbConnection;
    }

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

    public String intercpt() {
        return carro.inteceptou();
    }

    public void executar() throws CreateTopicException {

        System.out.println("****** iniciou executar ********");

        System.out.println("Enviando mensagem");

        BaixarEstoque baixarEstoque = new BaixarEstoque();
        baixarEstoque.setNomeProduto("arroz soltinho");
        baixarEstoque.setPreco(12.60f);
        baixarEstoque.setQuantidade(100l);

        messageClient.sendMessage(baixarEstoque);

        System.out.println(pessoa.getNome());
        System.out.println("Endereco: " + getCarro().getEndereco().getNome());
//        System.out.println("Endereco: " + getPessoa().getEndereco().getNome());
        System.out.println(carro.getNome());
        System.out.println("Interceptou: " + carro.inteceptou());
//        carroRepository.salvar(carro);
        pessoaRepository.salvar(new Pessoa());
        System.out.println(pessoaRepository.processar("o valor da vida"));
        arquivoTexto.gerar();

        System.out.println("climb connection: " + climbConnection);

    }

}
