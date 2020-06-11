package br.com.climb.cdi.clazz;

import br.com.climb.cdi.ContainerInitializer;
import br.com.climb.cdi.Initializer;
import br.com.climb.cdi.interceptor.Context;
import br.com.climb.cdi.teste.model.interceptors.PessoaInterceptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class TypeOfClassManagerTest {

    TypeOfClassManager typeOfClassManager;

    @Test
    @Order(1)
    void create() {
        Initializer initializer = ContainerInitializer.newInstance();
        typeOfClassManager = new  TypeOfClassManager(initializer);
        Assertions.assertSame(true, typeOfClassManager != null);
    }

    @Test
    @Order(2)
    void getInterceptorClass() throws NoSuchMethodException {

    }

    @Test
    @Order(3)
    void getQualifierClass() {
    }

    @Test
    @Order(4)
    void getClassOfField() {
    }
}