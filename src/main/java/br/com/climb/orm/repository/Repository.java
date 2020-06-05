package br.com.climb.orm.repository;

import br.com.climb.core.interfaces.ResultIterator;

public interface Repository<T> {

    void save(T object);

    void update(T object);

    void delete(T object);

    T findOne(Long id);

    ResultIterator find();

}
