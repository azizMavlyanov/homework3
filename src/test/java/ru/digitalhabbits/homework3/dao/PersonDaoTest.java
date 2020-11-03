package ru.digitalhabbits.homework3.dao;

import org.junit.jupiter.api.BeforeEach;
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
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.utils.PersonHelper;

import java.io.InputStream;
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
class PersonDaoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonDao personDao;

    @Test
    void findById() {
        // TODO: NotImplemented
        Person builtPerson = entityManager.merge(PersonHelper.buildPerson());
        Person person = personDao.findById(builtPerson.getId());

        assertEquals(builtPerson.getId(), person.getId());
        assertEquals(builtPerson.getFirstName(), person.getFirstName());
        assertEquals(builtPerson.getLastName(), person.getLastName());
        assertEquals(builtPerson.getMiddleName(), person.getMiddleName());
        assertEquals(builtPerson.getAge(), person.getAge());
        assertEquals(builtPerson.getDepartment(), person.getDepartment());
    }

    @Test
    void findAll() {
        // TODO: NotImplemented
        final int count = 2;
        final List<Person> builtPersons = IntStream.range(0, count)
                .mapToObj(i -> entityManager.merge(PersonHelper.buildPerson()))
                .collect(Collectors.toList());

        List<Person> persons = personDao.findAll();
        assertEquals(builtPersons.size(), persons.size());
    }

    @Test
    void update() {
        // TODO: NotImplemented
        Person builtUpdatedPerson = PersonHelper.buildPerson();
        Person createdPerson = entityManager.merge(PersonHelper.buildPerson());
        createdPerson.setFirstName(builtUpdatedPerson.getFirstName());
        createdPerson.setLastName(builtUpdatedPerson.getLastName());
        createdPerson.setMiddleName(builtUpdatedPerson.getMiddleName());
        createdPerson.setAge(builtUpdatedPerson.getAge());
        createdPerson.setDepartment(builtUpdatedPerson.getDepartment());

        Person updatedPerson = personDao.update(createdPerson);

        assertEquals(createdPerson.getFirstName(), updatedPerson.getFirstName());
        assertEquals(createdPerson.getLastName(), updatedPerson.getLastName());
        assertEquals(createdPerson.getMiddleName(), updatedPerson.getMiddleName());
        assertEquals(createdPerson.getAge(), updatedPerson.getAge());
        assertEquals(createdPerson.getDepartment(), updatedPerson.getDepartment());
    }

    @Test
    void delete() {
        // TODO: NotImplemented
        Person builtPerson = PersonHelper.buildPerson();
        builtPerson.setId(1);
        Person createdPerson = entityManager.merge(builtPerson);
        Person deletedPerson = personDao.delete(createdPerson.getId());
        Person response = entityManager.find(Person.class, createdPerson.getId());

        assertNull(response);
        assertEquals(builtPerson.getId(), deletedPerson.getId());
        assertEquals(builtPerson.getFirstName(), deletedPerson.getFirstName());
        assertEquals(builtPerson.getLastName(), deletedPerson.getLastName());
        assertEquals(builtPerson.getMiddleName(), deletedPerson.getMiddleName());
        assertEquals(builtPerson.getAge(), deletedPerson.getAge());
        assertEquals(builtPerson.getDepartment(), deletedPerson.getDepartment());
    }
}