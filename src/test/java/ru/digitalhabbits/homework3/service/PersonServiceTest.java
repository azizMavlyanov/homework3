package ru.digitalhabbits.homework3.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.digitalhabbits.homework3.dao.PersonDao;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;
import ru.digitalhabbits.homework3.utils.PersonHelper;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = PersonServiceImpl.class)
class PersonServiceTest {
    private static final int COUNT = 2;
    @MockBean
    private PersonDao personDao;

    @Autowired
    private PersonService personService;

    @Test
    void findAllPersons() {
        // TODO: NotImplemented
        when(personDao.findAll())
                .thenReturn(range(0, COUNT).mapToObj(i -> PersonHelper.buildPerson()).collect(Collectors.toList()));
        final List<PersonResponse> persons = personService.findAllPersons();

        assertEquals(COUNT, persons.size());
    }

    @Test
    void getPerson() {
        // TODO: NotImplemented
        final Person person = PersonHelper.buildPerson();
        final PersonResponse personResponse = PersonHelper.buildPersonResponse(person);
        when(personDao.findById(any(Integer.class))).thenReturn(person);
        final PersonResponse response = personService.getPerson(person.getId());

        assertEquals(personResponse.getId(), response.getId());
        assertEquals(personResponse.getAge(), response.getAge());
        assertEquals(personResponse.getFullName(), response.getFullName());
    }

    @Test
    void createPerson() {
        // TODO: NotImplemented
        final PersonRequest request = PersonHelper.buildPersonRequest();
        final int personId = nextInt();
        final Person person = new Person()
                .setId(personId)
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAge(request.getAge());

        when(personDao.create(any(Person.class))).thenReturn(person);

        final int response = personService.createPerson(request);
        assertEquals(personId, response);
    }

    @Test
    void updatePerson() {
        // TODO: NotImplemented
        final PersonRequest request = PersonHelper.buildPersonRequest();
        final int personId = nextInt();
        final Person person = new Person()
                .setId(personId)
                .setFirstName(request.getFirstName())
                .setMiddleName(request.getMiddleName())
                .setLastName(request.getLastName())
                .setAge(request.getAge());

        when(personDao.findById(any(Integer.class))).thenReturn(person);
        when(personDao.update(any(Person.class))).thenReturn(person);

        final PersonResponse response = personService.updatePerson(personId, request);
        assertEquals(personId, response.getId());
        assertEquals(request.getAge(), response.getAge());
        assertEquals(PersonHelper.buildFullName(request.getFirstName(), request.getLastName(), request.getMiddleName()),
                response.getFullName());
    }

    @Test
    void deletePerson() {
        // TODO: NotImplemented
        final PersonService mockedPersonService = mock(PersonService.class);
        final Person person = PersonHelper.buildPerson();

        when(personDao.findById(any(Integer.class))).thenReturn(person);
        when(personDao.delete(any(Integer.class))).thenReturn(person);

        personService.deletePerson(person.getId());

        mockedPersonService.deletePerson(person.getId());
        verify(personDao, times(1)).delete(person.getId());
        verify(mockedPersonService, times(1)).deletePerson(person.getId());
    }
}