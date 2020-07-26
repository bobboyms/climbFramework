package br.com.climb.rpc.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RpcClient {
    String controllerName() default "";
    String methodName() default "";
}
