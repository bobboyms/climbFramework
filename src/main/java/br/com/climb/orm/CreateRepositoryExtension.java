package br.com.climb.orm;


import br.com.climb.framework.annotations.Repository;
import br.com.climb.test.model.Cliente;
import br.com.climb.test.repository.ClienteRepository;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Set;

import static br.com.climb.framework.utils.ReflectionUtils.getAnnotedClass;

/**
 * Essa classe tem a função de criar dinamicamente dos beans do tipo @Respository.
 * Como o tipo repository é uma interface, é criada a implementação
 * dinamicamente, em tempo de execução atraves do CGLIB com:
 *
 * Enhancer enhancer = new Enhancer();
 * enhancer.setSuperclass(classe);
 */

public class CreateRepositoryExtension {

}
