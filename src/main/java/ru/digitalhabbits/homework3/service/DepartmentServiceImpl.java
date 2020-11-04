package ru.digitalhabbits.homework3.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digitalhabbits.homework3.dao.DepartmentDao;
import ru.digitalhabbits.homework3.dao.PersonDao;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.exception.ConflictException;
import ru.digitalhabbits.homework3.model.*;

import javax.annotation.Nonnull;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl
        implements DepartmentService {
    private final DepartmentDao departmentDao;
    private final PersonDao personDao;

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public List<DepartmentShortResponse> findAllDepartments() {
        // NotImplemented: получение краткой информации о всех департаментах
        return departmentDao.findAll()
                .stream()
                .map(this::buildDepartmentShortResponse)
                .collect(Collectors.toList());
    }

    @Nonnull
    @Override
    @Transactional(readOnly = true)
    public DepartmentResponse getDepartment(@Nonnull Integer id) {
//        NotImplemented: получение подробной информации о департаменте и краткой информации о людях в нем.
//        Если не найдено, отдавать 404:NotFound
        Department department = departmentDao.findById(id);

        if (department != null) {
            List<PersonInfo> personInfoList = department.getPersons()
                    .stream()
                    .map(PersonServiceImpl::buildPersonInfo)
                    .collect(Collectors.toList());

            return new DepartmentResponse()
                    .setId(department.getId())
                    .setName(department.getName())
                    .setClosed(department.isClosed())
                    .setPersons(personInfoList);
        }

        throw new EntityNotFoundException("Resource not found in the system");
    }

    @Nonnull
    @Override
    @Transactional
    public Integer createDepartment(@Nonnull DepartmentRequest request) {
        // NotImplemented: создание нового департамента
        Department department = new Department()
                .setName(request.getName());
        return departmentDao.create(department).getId();
    }

    @Nonnull
    @Override
    @Transactional
    public DepartmentResponse updateDepartment(@Nonnull Integer id, @Nonnull DepartmentRequest request) {
        // NotImplemented: обновление данных о департаменте. Если не найдено, отдавать 404:NotFound
        Department department = departmentDao.findById(id);
        if (department != null) {
            department.setName(request.getName());
            Department updatedDepartment = departmentDao.update(department);
            List<PersonInfo> personInfoList = updatedDepartment.getPersons()
                    .stream()
                    .map(PersonServiceImpl::buildPersonInfo)
                    .collect(Collectors.toList());

            return new DepartmentResponse()
                    .setId(updatedDepartment.getId())
                    .setName(updatedDepartment.getName())
                    .setClosed(updatedDepartment.isClosed())
                    .setPersons(personInfoList);
        }
        throw new EntityNotFoundException("Resource not found in the system");
    }

    @Override
    @Transactional
    public void deleteDepartment(@Nonnull Integer id) {
        // NotImplemented: удаление всех людей из департамента и удаление самого департамента.
        //  Если не найдено, то ничего не делать
        Department department = departmentDao.findById(id);

        if (department != null) {
            for (Person person : department.getPersons()) {
                person.setDepartment(null);
            }
            departmentDao.update(department);
            departmentDao.delete(department.getId());
        }
    }

    @Override
    @Transactional
    public void addPersonToDepartment(@Nonnull Integer departmentId, @Nonnull Integer personId) {
        // NotImplemented: добавление нового человека в департамент.
        //  Если не найден человек или департамент, отдавать 404:NotFound.
        //  Если департамент закрыт, то отдавать 409:Conflict
        Department department = departmentDao.findById(departmentId);
        Person person = personDao.findById(personId);

        if (department == null || person == null) {
            throw new EntityNotFoundException("Resource not found in the system");
        } else if (department.isClosed()) {
            throw new ConflictException("Conflict occurred in the system");
        }

        department.addPerson(person);
        departmentDao.update(department);
    }

    @Override
    @Transactional
    public void removePersonFromDepartment(@Nonnull Integer departmentId, @Nonnull Integer personId) {
        // NotImplemented: удаление человека из департамента.
        //  Если департамент не найден, отдавать 404:NotFound, если не найден человек в департаменте, то ничего не делать
        Department department = departmentDao.findById(departmentId);
        Person person = personDao.findById(personId);

        if (department == null) {
            throw new EntityNotFoundException("Resource not found in the system");
        } else if (person != null) {
            department.removePerson(person);
            departmentDao.update(department);
            personDao.update(person);
        }
    }

    @Override
    @Transactional
    public void closeDepartment(@Nonnull Integer id) {
        // NotImplemented: удаление всех людей из департамента и установка отметки на департаменте,
        //  что он закрыт для добавления новых людей. Если не найдено, отдавать 404:NotFound
        Department department = departmentDao.findById(id);

        if (department == null) {
            throw new EntityNotFoundException("Resource not found in the system");
        }

        department.getPersons()
                .forEach(person -> {
                    person.setDepartment(null);
                    personDao.update(person);
                });

        department.setClosed(true);
        departmentDao.update(department);
    }

    private DepartmentShortResponse buildDepartmentShortResponse(Department department) {
        return new DepartmentShortResponse()
                .setId(department.getId())
                .setName(department.getName());
    }

    public static DepartmentInfo buildDepartmentInfo(Department department) {
        if (department != null) {
            return new DepartmentInfo()
                    .setId(department.getId())
                    .setName(department.getName());
        }
        return null;
    }
}
