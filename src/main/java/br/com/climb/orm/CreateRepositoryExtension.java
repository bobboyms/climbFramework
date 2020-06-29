package br.com.climb.orm;


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
