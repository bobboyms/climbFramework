package br.com.climb.cdi.disposes;

import br.com.climb.cdi.Initializer;
import br.com.climb.cdi.model.Capsule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DisposesManager implements Disposes {

    private Initializer initializer;

    private Set<Object> disposesObjects = new HashSet<>();

    protected DisposesManager(Initializer initializer) {
        this.initializer = initializer;
    }

    public static Disposes create(Initializer initializer) {
        return new DisposesManager(initializer);
    }

    /**
     * Verifica se é um tipo que vai ser dispensado quando chamar o method
     * close
     *
     * TODO: refatorar o methodo isDisposes e Dispose para poder pegar corretamente
     * o tipo generico da classe ou interface
     *
     * @param clazz
     * @return
     */
    public boolean isDisposes(Class clazz) {

        final Object result = initializer.getDisposesMethods().get(clazz);

        if (result != null) {
            return true;
        }

        final List<Class> classes = Arrays.asList(clazz.getInterfaces()).stream()
                .filter(aClass -> initializer.getDisposesMethods().get(aClass) != null).collect(Collectors.toList());

        if (classes.size() > 0) {
            return true;
        }

        final List<Type> ifaces = Arrays.asList(clazz.getSuperclass().getGenericInterfaces()).stream()
                .filter(aClass -> initializer.getDisposesMethods().get(aClass) != null).collect(Collectors.toList());

        if (ifaces.size() > 0) {
            return true;
        }

        return false;
    }

    @Override
    public void disposeObjects() {

        disposesObjects.stream().forEach(object -> {

            Capsule capsule = initializer.getDisposesMethods().get(object.getClass());

            if (capsule == null) {

                final List<Type> ifaces = Arrays.asList(object.getClass().getSuperclass().getGenericInterfaces()).stream()
                        .filter(aClass -> initializer.getDisposesMethods().get(aClass) != null).collect(Collectors.toList());

                if (ifaces.size() > 0) {
                    capsule = initializer.getDisposesMethods().get(ifaces.get(0));
                }
            }

            try {

                final Object instance = capsule.getClassFactory().getDeclaredConstructor().newInstance();
                capsule.getMethod().invoke(instance,object);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        });
    }

    public void addDisposeList(Capsule capsule, Object resultInvoke) {

        if (isDisposes(resultInvoke.getClass())) {
            disposesObjects.add(resultInvoke);
        }
    }

}
