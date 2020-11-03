package ru.digitalhabbits.homework3.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.utils.DepartmentHelper;
import ru.digitalhabbits.homework3.utils.PersonHelper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class DepartmentDaoImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentDao departmentDao;

    @Test
    void findById() {
        // TODO: NotImplemented
        Department builtDepartment = entityManager.merge(DepartmentHelper.buildDepartment());
        Department department = departmentDao.findById(builtDepartment.getId());

        assertEquals(builtDepartment.getId(), department.getId());
        assertEquals(builtDepartment.getName(), department.getName());
        assertEquals(builtDepartment.getPersons(), department.getPersons());
    }

    @Test
    void findAll() {
        // TODO: NotImplemented
        final int count = 2;
        final List<Department> builtDepartments = IntStream.range(0, count)
                .mapToObj(i -> entityManager.merge(DepartmentHelper.buildDepartment()))
                .collect(Collectors.toList());

        List<Department> departments = departmentDao.findAll();
        assertEquals(builtDepartments.size(), departments.size());
    }

    @Test
    void update() {
        // TODO: NotImplemented
        Department builtUpdatedDepartment = DepartmentHelper.buildDepartment();
        Department createdDepartment = entityManager.merge(DepartmentHelper.buildDepartment());
        createdDepartment.setName(builtUpdatedDepartment.getName());
        createdDepartment.setClosed(builtUpdatedDepartment.isClosed());
        createdDepartment.setPersons(builtUpdatedDepartment.getPersons());

        Department updatedDepartment = departmentDao.update(createdDepartment);

        assertEquals(createdDepartment.getId(), updatedDepartment.getId());
        assertEquals(createdDepartment.getName(), updatedDepartment.getName());
        assertEquals(createdDepartment.isClosed(), updatedDepartment.isClosed());
        assertEquals(createdDepartment.getPersons(), updatedDepartment.getPersons());

    }

    @Test
    void delete() {
        // TODO: NotImplemented
        Department builtDepartment = DepartmentHelper.buildDepartment();
        builtDepartment.setId(1);
        Department createdDepartment = entityManager.merge(builtDepartment);
        Department deletedDepartment = departmentDao.delete(createdDepartment.getId());
        Department response = entityManager.find(Department.class, createdDepartment.getId());

        assertNull(response);
        assertEquals(builtDepartment.getId(), deletedDepartment.getId());
        assertEquals(builtDepartment.getName(), deletedDepartment.getName());
        assertEquals(builtDepartment.isClosed(), deletedDepartment.isClosed());
        assertEquals(builtDepartment.getPersons(), deletedDepartment.getPersons());

    }
}