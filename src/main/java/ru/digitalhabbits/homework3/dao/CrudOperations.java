package ru.digitalhabbits.homework3.dao;

import org.springframework.data.repository.NoRepositoryBean;

import javax.annotation.Nonnull;
import java.util.List;

@NoRepositoryBean
public interface CrudOperations<T, ID> {
    T create(T entity);

    T findById(@Nonnull ID id);

    List<T> findAll();

    T update(T entity);

    T delete(ID id);
}
