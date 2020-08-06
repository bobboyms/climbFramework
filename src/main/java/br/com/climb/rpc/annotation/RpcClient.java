package br.com.climb.rpc.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RpcClient {
    String chanelName() default "";
    String className() default "";
    String methodName() default "";
}
