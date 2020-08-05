package br.com.climb.cdi.teste.model.interceptors;

import br.com.climb.cdi.interceptor.Context;
import br.com.climb.cdi.interceptor.MethodIntercept;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Interceptor;
import br.com.climb.cdi.teste.model.Pessoa;

@Logar
@Interceptor
public class PessoaInterceptor implements MethodIntercept {

    @Inject
    private Pessoa pessoa;

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public Object interceptorMethod(Context ctx) throws Throwable {
        System.out.println("********** Interceptou ************** ");
        return "Valor interceptado";
    }
}
