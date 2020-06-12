package br.com.climb.cdi.instances;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.cdi.Initializer;
import br.com.climb.cdi.clazz.TypeOfClass;
import br.com.climb.cdi.clazz.TypeOfClassManager;
import br.com.climb.cdi.disposes.Disposes;
import br.com.climb.cdi.disposes.DisposesManager;
import br.com.climb.cdi.model.Capsule;
import br.com.climb.cdi.teste.model.Carro;
import br.com.climb.cdi.teste.model.Controller;
import br.com.climb.cdi.teste.model.Endereco;
import br.com.climb.cdi.teste.model.Pessoa;
import br.com.climb.cdi.teste.model.factory.ArquivoTextoFactory;
import br.com.climb.cdi.teste.model.factory.PessoaFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class InstancesManagerTest {

    private Initializer initializer;
    private Disposes disposes;
    private TypeOfClass typeOfClass;
    private InstancesManager instances;

    public InstancesManager getInstance() {
        initializer = ContainerInitializer.newInstance();
        disposes = DisposesManager.create(initializer);
        typeOfClass = TypeOfClassManager.create(initializer);
        return new InstancesManager(initializer,disposes, typeOfClass);
    }

    @Test
    void generateInstanceByTheFactory() throws NoSuchFieldException {
        Field pessoa = ArquivoTextoFactory.class.getDeclaredField("pessoa");
        Field carro = Controller.class.getDeclaredField("carro");

        Object result =  getInstance().generateInstanceByTheFactory(pessoa);
        Assertions.assertSame(false, Objects.isNull(result));
        Assertions.assertSame(Pessoa.class, result.getClass());

        result =  getInstance().generateInstanceByTheFactory(carro);
        Assertions.assertSame(true, Objects.isNull(result));
    }

    @Test
    void generateInstance() throws NoSuchFieldException {
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
    void generateInstanceBase() {
        Controller result =  (Controller) getInstance().generateInstanceBase(Controller.class);
        Assertions.assertSame(false, Objects.isNull(result));
        Assertions.assertSame(Controller.class, result.getClass().getSuperclass());
    }

    @Test
    void injectObjecstInComponentClass() throws NoSuchFieldException {

        Field pessoaField = ArquivoTextoFactory.class.getDeclaredField("pessoa");
        Field enderecoField = Pessoa.class.getDeclaredField("endereco");

        Pessoa pessoa =  (Pessoa) getInstance().generateInstance(pessoaField);
        Endereco endereco =  (Endereco) getInstance().generateInstance(enderecoField);

        getInstance().injectObjecstInComponentClass(pessoaField.getType(), pessoa);
        Assertions.assertSame(false, Objects.isNull(pessoa.getEndereco()));

    }

    @Test
    void injectInstanceField() throws NoSuchFieldException {

        Field pessoaField = ArquivoTextoFactory.class.getDeclaredField("pessoa");
        Field enderecoField = Pessoa.class.getDeclaredField("endereco");

        Pessoa pessoa =  (Pessoa) getInstance().generateInstance(pessoaField);
        Endereco endereco =  (Endereco) getInstance().generateInstance(enderecoField);

        getInstance().injectInstanceField(pessoa, enderecoField, endereco);
        Assertions.assertSame(false, Objects.isNull(pessoa.getEndereco()));

    }

    @Test
    void getSingletonObject() throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

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