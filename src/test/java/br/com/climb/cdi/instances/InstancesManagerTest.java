package br.com.climb.cdi.instances;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.cdi.Initializer;
import br.com.climb.cdi.clazz.TypeOfClass;
import br.com.climb.cdi.clazz.TypeOfClassManager;
import br.com.climb.cdi.disposes.Disposes;
import br.com.climb.cdi.disposes.DisposesManager;
import br.com.climb.cdi.exception.ValidationException;
import br.com.climb.cdi.model.Capsule;
import br.com.climb.cdi.teste.model.*;
import br.com.climb.cdi.teste.model.factory.ArquivoTextoFactory;
import br.com.climb.cdi.teste.model.factory.PessoaFactory;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.commons.execptions.ConfigFileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

class InstancesManagerTest {

    private Initializer initializer;
    private Disposes disposes;
    private TypeOfClass typeOfClass;
    private InstancesManager instances;

    public InstancesManager getInstance() throws IOException, ConfigFileException {
        ConfigFile configFile = new FactoryConfigFile().getConfigFile("framework.properties");
        initializer = ContainerInitializer.newInstance(configFile);
        disposes = DisposesManager.create(initializer);
        typeOfClass = TypeOfClassManager.create(initializer);
        return new InstancesManager(initializer,disposes, typeOfClass);
    }

    @Test
    void generateInstanceByTheFactory() throws NoSuchFieldException, IOException, ConfigFileException {
        Field pessoa = ArquivoTextoFactory.class.getDeclaredField("pessoa");
        Field carro = Controller.class.getDeclaredField("carro");

        Object result =  getInstance().generateInstanceByTheFactory(pessoa);
        Assertions.assertSame(false, Objects.isNull(result));
        Assertions.assertSame(Pessoa.class, result.getClass());

        result =  getInstance().generateInstanceByTheFactory(carro);
        Assertions.assertSame(true, Objects.isNull(result));
    }

    @Test
    void generateInstance() throws NoSuchFieldException, IOException, ConfigFileException {
        Field pessoa = ArquivoTextoFactory.class.getDeclaredField("pessoa");
        Field carro = Controller.class.getDeclaredField("carro");

        Object result =  getInstance().generateInstance(pessoa);
        Assertions.assertSame(false, Objects.isNull(result));
        Assertions.assertSame(Pessoa.class, result.getClass());

        result =  getInstance().generateInstance(carro);
        Assertions.assertSame(Carro.class, result.getClass().getSuperclass());
        Assertions.assertSame(false, Objects.isNull(result));
    }

    @Test
    void generateInstanceBase() throws IOException, ConfigFileException {
        Controller result =  (Controller) getInstance().generateInstanceBase(Controller.class);
        Assertions.assertSame(false, Objects.isNull(result));
        Assertions.assertSame(Controller.class, result.getClass().getSuperclass());
    }

    @Test
    void generateInstanceBaseSession() throws ValidationException, IOException, ConfigFileException {

        Instances instances = getInstance();

        CadastroClienteController instance1 =  (CadastroClienteController) instances.generateInstanceBase(CadastroClienteController.class,"1234");
        Assertions.assertSame(false, Objects.isNull(instance1));
        Assertions.assertSame(CadastroClienteController.class, instance1.getClass().getSuperclass());
        PessoaRepository pessoaRepository1 = (PessoaRepository) instance1.getPessoaRepository();

        CadastroClienteController instance2 =  (CadastroClienteController) instances.generateInstanceBase(CadastroClienteController.class,"1234");
        Assertions.assertSame(false, Objects.isNull(instance2));
        PessoaRepository pessoaRepository2 = (PessoaRepository) instance1.getPessoaRepository();

        Assertions.assertSame(instance1, instance2);
        Assertions.assertSame(false, pessoaRepository1.equals(pessoaRepository2));

        Assertions.assertSame(0l, instance1.getValor());
        Assertions.assertSame(1l, instance2.getValor());
        Assertions.assertSame(2l, instance1.getValor());
        Assertions.assertSame(3l, instance2.getValor());
        Assertions.assertSame(4l, instance1.getValor());
        Assertions.assertSame(5l, instance2.getValor());

        CadastroCarroController instance3 =  (CadastroCarroController) instances.generateInstanceBase(CadastroCarroController.class,"1234");
        Assertions.assertSame(false, Objects.isNull(instance2));

        Assertions.assertSame(0l, instance3.getValor());
        Assertions.assertSame(1l, instance3.getValor());

        instance1 =  (CadastroClienteController) instances.generateInstanceBase(CadastroClienteController.class,"1234");
        Assertions.assertSame(6l, instance1.getValor());

        instance3 =  (CadastroCarroController) instances.generateInstanceBase(CadastroCarroController.class,"1234");
        Assertions.assertSame(2l, instance3.getValor());


    }

    @Test
    void injectObjecstInComponentClass() throws NoSuchFieldException, IOException, ConfigFileException {

        Field pessoaField = ArquivoTextoFactory.class.getDeclaredField("pessoa");
        Field enderecoField = Pessoa.class.getDeclaredField("endereco");

        Pessoa pessoa =  (Pessoa) getInstance().generateInstance(pessoaField);
        Endereco endereco =  (Endereco) getInstance().generateInstance(enderecoField);

        getInstance().injectObjecstInComponentClass(pessoaField.getType(), pessoa);
        Assertions.assertSame(false, Objects.isNull(pessoa.getEndereco()));

    }

    @Test
    void injectInstanceField() throws NoSuchFieldException, IOException, ConfigFileException {

        Field pessoaField = ArquivoTextoFactory.class.getDeclaredField("pessoa");
        Field enderecoField = Pessoa.class.getDeclaredField("endereco");

        Pessoa pessoa =  (Pessoa) getInstance().generateInstance(pessoaField);
        Endereco endereco =  (Endereco) getInstance().generateInstance(enderecoField);

        getInstance().injectInstanceField(pessoa, enderecoField, endereco);
        Assertions.assertSame(false, Objects.isNull(pessoa.getEndereco()));

    }

    @Test
    void getSingletonObject() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, IOException, ConfigFileException {

        Capsule capsule = new Capsule();
        capsule.setClassFactory(PessoaFactory.class);
        capsule.setMethod(PessoaFactory.class.getDeclaredMethod("getPessoa"));

        instances = getInstance();

        Object instance1 = instances.getSingletonObject(capsule);
        Object instance2 = instances.getSingletonObject(capsule);
        Object instance3 = instances.getSingletonObject(capsule);
        Object instance4 = instances.getSingletonObject(capsule);

        Assertions.assertSame(instance1, instance3);
        Assertions.assertSame(instance1, instance2);
        Assertions.assertSame(instance3, instance2);
        Assertions.assertSame(instance4, instance1);

    }
}