package br.com.climb.cdi;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static br.com.climb.cdi.ClimbCdiContainer.generateInstanceBase;
import static br.com.climb.framework.utils.ReflectionUtils.getAnnotedClass;



@Component
class Pessoa {

    public String getNome() {
        return "Thiago";
    }

    @Inject
    private Endereco endereco;

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

@Component
class Endereco {

    public String getNome() {
        return "Rua 31 de março";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

@Component
class Carro {

    public String getNome() {
        return "fsuca";
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

class Controller {

    @Inject
    private Pessoa pessoa;

    @Inject
    private Carro carro;

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
        System.out.println(pessoa.getNome());
        System.out.println(carro.getNome());
    }

}


public class Main {



    public static void main(String[] args) throws IOException {

        Controller controller = (Controller) generateInstanceBase(Controller.class);
        System.out.println(controller.getPessoa().getEndereco().getNome());
        controller.executar();


    }

}
