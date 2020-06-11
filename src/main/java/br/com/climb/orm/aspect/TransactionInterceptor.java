package br.com.climb.orm.aspect;

import br.com.climb.cdi.interceptor.Context;
import br.com.climb.cdi.interceptor.MethodIntercept;
import br.com.climb.cdi.annotations.Inject;
import br.com.climb.cdi.annotations.Interceptor;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.orm.annotation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Transaction
@Interceptor
public class TransactionInterceptor implements MethodIntercept {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Inject
    private ClimbConnection climbConnection;

    public void setClimbConnection(ClimbConnection climbConnection) {
        this.climbConnection = climbConnection;
    }

    @Override
    public Object interceptorMethod(Context ctx) throws Exception {

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
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return object;
    }


}
