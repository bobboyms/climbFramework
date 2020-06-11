package br.com.climb.cdi.clazz;

import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface TypeOfClass {

    Class<?> getInterceptorClass(Method method);

    Class<?> getQualifierClass(Field field);

    Class<?> getClassOfField(Field field);

}
