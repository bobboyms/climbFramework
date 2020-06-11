package br.com.climb.cdi.clazz;

import br.com.climb.cdi.Initializer;
import br.com.climb.cdi.annotations.Component;
import br.com.climb.cdi.annotations.Inject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TypeOfClassManager implements TypeOfClass {

    private Initializer initializer;

    private TypeOfClassManager(Initializer initializer) {
        this.initializer = initializer;
    }

    public static TypeOfClass create(Initializer initializer) {
        return new TypeOfClassManager(initializer);
    }

    @Override
    public Class getInterceptorClass(Method method) {

        List<Class> listInterceptorClass =  Arrays.asList(method.getAnnotations()).stream()
                .map(annotation -> annotation.annotationType())
                .filter(aClass -> initializer.getInterceptorClasses().get(aClass) != null)
                .map(aClass -> initializer.getInterceptorClasses().get(aClass)).collect(Collectors.toList());

        if (listInterceptorClass != null && listInterceptorClass.size() > 0) {
            return listInterceptorClass.get(0);
        }

        return null;

    }

    @Override
    public Class<?> getQualifierClass(Field field) {

        final List<Class<?>> listConcreteClassInterface = initializer.getConcreteInterfaceClasses().get(field.getType());

        if (listConcreteClassInterface != null && listConcreteClassInterface.size() > 1) {
            final String qualifier  = field.getAnnotation(Inject.class).value();
            final List<Class<?>> listClazzTemp = listConcreteClassInterface.stream().filter(aClass -> ((Component) aClass.getAnnotation(Component.class)).value().equals(qualifier))
                    .collect(Collectors.toList());
            return listClazzTemp.get(0);
        }

        return initializer.getConcreteInterfaceClasses().get(field.getType()).get(0);

    }

    @Override
    public Class<?> getClassOfField(Field field) {

        if (field.getType().isInterface()) {
            return getQualifierClass(field);
        }

        return field.getType();

    }

}
