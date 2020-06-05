package br.com.climb.orm;


import br.com.climb.framework.annotations.Repository;
import br.com.climb.test.model.Cliente;
import br.com.climb.test.repository.ClienteRepository;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
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

public class CreateRepositoryExtension implements Extension {

    public void afterBean(final @Observes AfterBeanDiscovery afterBeanDiscovery) throws IOException {

//        Set<Class<?>> clazzs = getAnnotedClass(Repository.class, "br.com.");
//
//            clazzs.stream().forEach(classe -> {
//
//
//                Enhancer enhancer = new Enhancer();
////                enhancer.setInterfaces(new Class[] { classe });
//                enhancer.setSuperclass(classe);
//                enhancer.setCallback(NoOp.INSTANCE);
//                Object o = enhancer.create();
//
//                System.out.println("qual classe? " + classe);
//
//                afterBeanDiscovery
//                        .addBean()
//                        .scope(ApplicationScoped.class)
////                        .types(classe)
//                        .id("Created by " + CreateRepositoryExtension.class)
//                        .createWith(e -> o);
//
//            });


    }
}
