package ru.digitalhabbits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabbits.homework3.dao.PersonDao;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.PersonInfo;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl
        implements PersonService {
    private final PersonDao personDao;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<PersonResponse> findAllPersons() {
        // NotImplemented: получение информации о всех людях во всех отделах
        try {
            return personDao.findAll()
                    .stream()
                    .map(PersonServiceImpl::buildPersonResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public PersonResponse getPerson(@Nonnull Integer id) {
        // NotImplemented: получение информации о человеке. Если не найдено, отдавать 404:NotFound
        try {
            Person person = personDao.findById(id);

            if (person != null) {
                return PersonServiceImpl.buildPersonResponse(person);
            }

            throw new EntityNotFoundException("Resource not found in the system");
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Nonnull
    @Override
    @Transactional
    public Integer createPerson(@Nonnull PersonRequest request) {
        // NotImplemented: создание новой записи о человеке
        Person person = new Person()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setMiddleName(request.getMiddleName())
                .setAge(request.getAge());
        try {
            return personDao.create(person).getId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Nonnull
    @Override
    @Transactional
    public PersonResponse updatePerson(@Nonnull Integer id, @Nonnull PersonRequest request) {
        // NotImplemented: обновление информации о человеке. Если не найдено, отдавать 404:NotFound
        try {
            Person person = personDao.findById(id);

            if (person == null) {
                throw new EntityNotFoundException("Resource not found in the system");
            }

            person
                    .setFirstName(request.getFirstName())
                    .setLastName(request.getLastName())
                    .setMiddleName(request.getMiddleName())
                    .setAge(request.getAge());

            Person updatedPerson = personDao.update(person);

            return PersonServiceImpl.buildPersonResponse(updatedPerson);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    @Override
    @Transactional
    public void deletePerson(@Nonnull Integer id) {
        // NotImplemented: удаление информации о человеке и удаление его из отдела. Если не найдено, ничего не делать
        try {
            Person person = personDao.findById(id);

            if (person != null) {
                personDao.delete(person.getId());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public static PersonInfo buildPersonInfo(Person person) {
        return new PersonInfo()
                .setId(person.getId())
                .setFullName(format("%s %s %s", person.getFirstName(), person.getLastName(), person.getMiddleName()));
    }

    public static PersonResponse buildPersonResponse(Person person) {
        return new PersonResponse()
                .setId(person.getId())
                .setFullName(format("%s %s %s", person.getFirstName(), person.getLastName(), person.getMiddleName()))
                .setAge(person.getAge())
                .setDepartment(DepartmentServiceImpl.buildDepartmentInfo(person.getDepartment()));
    }
}
