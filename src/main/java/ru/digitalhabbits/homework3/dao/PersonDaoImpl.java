package ru.digitalhabbits.homework3.dao;

import org.springframework.stereotype.Repository;
import ru.digitalhabbits.homework3.domain.Person;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class PersonDaoImpl
        implements PersonDao {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Person create(Person entity) {
        entityManager.persist(entity);

        return entity;
    }

    @Override
    public Person findById(@Nonnull Integer id) {
        return entityManager.find(Person.class, id);
    }

    @Override
    public List<Person> findAll() {
        CriteriaQuery<Person> criteria = entityManager.getCriteriaBuilder().createQuery(Person.class);
        criteria.select(criteria.from(Person.class));

        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public Person update(Person entity) {
        Person person = entityManager.find(Person.class, entity.getId());
        if (person != null) {
            entityManager.persist(entity);
            return entity;
        }
        return null;
    }

    @Override
    public Person delete(Integer integer) {
        Person person = entityManager.find(Person.class, integer);
        if (person != null) {
            entityManager.remove(person);
            return person;
        }

        return null;
    }
}
