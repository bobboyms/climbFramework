package br.com.climb.restclient.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface RestClient {
    String method() default "";
    String url() default "";
}
