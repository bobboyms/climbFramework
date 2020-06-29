package br.com.climb.cdi.clazz;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.cdi.teste.model.*;
import br.com.climb.cdi.teste.model.interceptors.PessoaInterceptor;
import br.com.climb.commons.configuration.ConfigFile;
import br.com.climb.commons.configuration.FactoryConfigFile;
import br.com.climb.framework.execptions.ConfigFileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

class TypeOfClassManagerTest {

    public TypeOfClassManager getTypeOfClassManager() throws IOException, ConfigFileException {
        ConfigFile configFile = new FactoryConfigFile().getConfigFile("framework.properties");
        return new TypeOfClassManager(ContainerInitializer.newInstance(configFile));
    }

    @Test
    @Order(1)
    void create() throws IOException, ConfigFileException {

        Assertions.assertSame(false, Objects.isNull(getTypeOfClassManager()));
    }

    @Test
    @Order(2)
    void getInterceptorClass() throws NoSuchMethodException, IOException, ConfigFileException {
        Method method = PessoaRepository.class.getMethod("salvar", Pessoa.class);
        Class result = getTypeOfClassManager().getInterceptorClass(method);
        Assertions.assertSame(PessoaInterceptor.class, result);

    }

    @Test
    @Order(3)
    void getQualifierClass() throws NoSuchFieldException, IOException, ConfigFileException {

       Field pessoaRespository = Controller.class.getDeclaredField("pessoaRepository");
       Field carroRepository = Controller.class.getDeclaredField("carroRepository");

       TypeOfClassManager typeOfClassManager = getTypeOfClassManager();
       Class result = typeOfClassManager.getQualifierClass(pessoaRespository);
       Assertions.assertSame(PessoaRepository.class, result);

       result = typeOfClassManager.getQualifierClass(carroRepository);
       Assertions.assertSame(CarroRepository.class, result);

    }

    @Test
    @Order(4)
    void getClassOfField() throws NoSuchFieldException, IOException, ConfigFileException {

        Field pessoaRespository = Controller.class.getDeclaredField("pessoaRepository");
        Field carro = Controller.class.getDeclaredField("carro");

        TypeOfClassManager typeOfClassManager = getTypeOfClassManager();

        Class result = typeOfClassManager.getClassOfField(carro);
        Assertions.assertSame(Carro.class, result);

        result = typeOfClassManager.getClassOfField(pessoaRespository);
        Assertions.assertSame(PessoaRepository.class, result);

    }
}