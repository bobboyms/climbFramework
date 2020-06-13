package br.com.climb.cdi.clazz;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.cdi.teste.model.*;
import br.com.climb.cdi.teste.model.interceptors.PessoaInterceptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

class TypeOfClassManagerTest {

    private TypeOfClassManager typeOfClassManager;

    @Test
    @Order(1)
    void create() {
        typeOfClassManager = new TypeOfClassManager(ContainerInitializer.newInstance());
        Assertions.assertSame(false, Objects.isNull(typeOfClassManager));
    }

    @Test
    @Order(2)
    void getInterceptorClass() throws NoSuchMethodException {
        Method method = PessoaRepository.class.getMethod("salvar", Pessoa.class);

        typeOfClassManager = new TypeOfClassManager(ContainerInitializer.newInstance());
        Class result = typeOfClassManager.getInterceptorClass(method);

        Assertions.assertSame(PessoaInterceptor.class, result);

    }

    @Test
    @Order(3)
    void getQualifierClass() throws NoSuchFieldException {

       Field pessoaRespository = Controller.class.getDeclaredField("pessoaRepository");
       Field carroRepository = Controller.class.getDeclaredField("carroRepository");

        typeOfClassManager = new TypeOfClassManager(ContainerInitializer.newInstance());
       Class result = typeOfClassManager.getQualifierClass(pessoaRespository);
       Assertions.assertSame(PessoaRepository.class, result);

       result = typeOfClassManager.getQualifierClass(carroRepository);
       Assertions.assertSame(CarroRepository.class, result);

    }

    @Test
    @Order(4)
    void getClassOfField() throws NoSuchFieldException {

        Field pessoaRespository = Controller.class.getDeclaredField("pessoaRepository");
        Field carro = Controller.class.getDeclaredField("carro");

        typeOfClassManager = new TypeOfClassManager(ContainerInitializer.newInstance());

        Class result = typeOfClassManager.getClassOfField(carro);
        Assertions.assertSame(Carro.class, result);

        result = typeOfClassManager.getClassOfField(pessoaRespository);
        Assertions.assertSame(PessoaRepository.class, result);

    }
}