package br.com.climb.orm.aspect;

import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.orm.annotation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Arrays;

@Transaction
@Interceptor
public class TransactionInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private ClimbConnection climbConnection;

    @AroundInvoke
    public Object interceptorMethod(InvocationContext ctx) throws Exception {

//        Arrays.asList(ctx.getParameters()).forEach(System.out::println);

        System.out.println("******** CONTROLA TRANSACTION *******");
        System.out.println("interceptor: " + climbConnection);
        Object object = null;

        try {
            climbConnection.getTransaction().start();
            object = ctx.proceed();
            climbConnection.getTransaction().commit();
        } catch (Exception e) {
            climbConnection.getTransaction().rollback();
            logger.error("TransactionInterceptor ERROR: {}", e);
        }

        return object;
    }


}
