package br.com.climb.framework.annotations;

import javax.inject.Named;
import java.lang.annotation.*;

@Inherited
@Named
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Repository {
}
