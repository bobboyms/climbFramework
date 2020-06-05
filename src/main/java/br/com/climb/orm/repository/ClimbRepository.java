package br.com.climb.orm.repository;

import br.com.climb.core.interfaces.ResultIterator;
import br.com.climb.orm.annotation.ImplementDaoMethod;

/**
 * @author Thiago Luiz Rodrigues
 * @param <T>
 */
public abstract class ClimbRepository<T> implements Repository<T> {

    @Override
    @ImplementDaoMethod
    public void save(T object) {}

    @Override
    @ImplementDaoMethod
    public void update(T object) {}

    @Override
    @ImplementDaoMethod
    public void delete(T object) {}

    @Override
    @ImplementDaoMethod
    public ResultIterator find() {
        return null;
    }

    @Override
    @ImplementDaoMethod
    public T findOne(Long id) {
        return null;
    }
}
