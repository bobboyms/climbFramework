package br.com.climb.cdi.disposes;

import br.com.climb.cdi.model.Capsule;

public interface Disposes {
    void disposeObjects();
    void addDisposeList(Capsule capsule, Object resultInvoke);
    boolean isDisposes(Class clazz);
}
