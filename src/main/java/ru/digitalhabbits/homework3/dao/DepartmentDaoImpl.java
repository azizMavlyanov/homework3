package ru.digitalhabbits.homework3.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DepartmentDaoImpl
        implements DepartmentDao {
    private final PersonDao personDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Department create(Department entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public Department findById(@Nonnull Integer id) {
        return entityManager.find(Department.class, id);
    }

    @Override
    public List<Department> findAll() {
        CriteriaQuery<Department> criteria = entityManager.getCriteriaBuilder().createQuery(Department.class);
        criteria.select(criteria.from(Department.class));
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public Department update(Department entity) {
        Department department = entityManager.find(Department.class, entity.getId());
        if (department != null) {
            entityManager.persist(entity);
            return entity;
        }
        return null;
    }

    @Override
    public Department delete(Integer integer) {
        Department department = entityManager.find(Department.class, integer);

        if (department != null) {
            entityManager.remove(department);
            return department;
        }

        return null;
    }
}
