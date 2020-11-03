package ru.digitalhabbits.homework3.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.digitalhabbits.homework3.dao.DepartmentDao;
import ru.digitalhabbits.homework3.dao.PersonDao;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.DepartmentRequest;
import ru.digitalhabbits.homework3.model.DepartmentResponse;
import ru.digitalhabbits.homework3.model.DepartmentShortResponse;
import ru.digitalhabbits.homework3.utils.DepartmentHelper;
import ru.digitalhabbits.homework3.utils.PersonHelper;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DepartmentServiceImpl.class)
class DepartmentServiceTest {
    private static final int COUNT = 2;
    @MockBean
    private DepartmentDao departmentDao;

    @MockBean
    private PersonDao personDao;

    @Autowired
    private DepartmentService departmentService;

    @Test
    void findAllDepartments() {
        // TODO: NotImplemented
        when(departmentDao.findAll())
                .thenReturn(range(0, COUNT).mapToObj(i -> DepartmentHelper.buildDepartment())
                        .collect(Collectors.toList()));
        final List<DepartmentShortResponse> departments = departmentService.findAllDepartments();

        assertEquals(COUNT, departments.size());
    }

    @Test
    void getDepartment() {
        // TODO: NotImplemented
        final Department department = DepartmentHelper.buildDepartment();
        final DepartmentResponse departmentResponse = DepartmentHelper.buildDepartmentResponse(department);
        when(departmentDao.findById(any(Integer.class))).thenReturn(department);

        final DepartmentResponse response = departmentService.getDepartment(department.getId());
        assertEquals(departmentResponse.getId(), response.getId());
        assertEquals(departmentResponse.getName(), response.getName());
        assertEquals(departmentResponse.isClosed(), response.isClosed());
        assertEquals(departmentResponse.getPersons(), response.getPersons());
    }

    @Test
    void createDepartment() {
        // TODO: NotImplemented
        final DepartmentRequest request = DepartmentHelper.buildDepartmentRequest();
        final int departmentId = nextInt();
        final Department department = new Department()
                .setId(departmentId)
                .setName(request.getName())
                .setClosed(false);

        when(departmentDao.create(any(Department.class))).thenReturn(department);

        final int response = departmentService.createDepartment(request);
        assertEquals(departmentId, response);
    }

    @Test
    void updateDepartment() {
        // TODO: NotImplemented
        final DepartmentRequest request = DepartmentHelper.buildDepartmentRequest();
        final int departmentId = nextInt();
        final Department department = new Department()
                .setId(departmentId)
                .setName(request.getName())
                .setClosed(false);

        when(departmentDao.findById(any(Integer.class))).thenReturn(department);
        when(departmentDao.update(any(Department.class))).thenReturn(department);

        final DepartmentResponse response = departmentService.updateDepartment(departmentId, request);

        assertEquals(departmentId, response.getId());
        assertEquals(request.getName(), response.getName());
    }

    @Test
    void deleteDepartment() {
        // TODO: NotImplemented
        final DepartmentService mockedDepartmentService = mock(DepartmentService.class);
        final Department department = DepartmentHelper.buildDepartment();

        when(departmentDao.findById(any(Integer.class))).thenReturn(department);
        when(departmentDao.update(any(Department.class))).thenReturn(department);
        when(departmentDao.delete(any(Integer.class))).thenReturn(department);

        departmentService.deleteDepartment(department.getId());

        mockedDepartmentService.deleteDepartment(department.getId());

        verify(departmentDao, times(1)).delete(department.getId());
        verify(mockedDepartmentService, times(1)).deleteDepartment(department.getId());
    }

    @Test
    void addPersonToDepartment() {
        // TODO: NotImplemented
        final DepartmentService mockedDepartmentService = mock(DepartmentService.class);
        final Department department = DepartmentHelper.buildDepartment();
        final Person person = PersonHelper.buildPerson();

        when(departmentDao.findById(any(Integer.class))).thenReturn(department);
        when(personDao.findById(any(Integer.class))).thenReturn(person);
        when(departmentDao.update(any(Department.class))).thenReturn(department);

        departmentService.addPersonToDepartment(department.getId(), person.getId());

        mockedDepartmentService.addPersonToDepartment(department.getId(), person.getId());

        verify(departmentDao, times(1)).findById(department.getId());
        verify(personDao, times(1)).findById(person.getId());
        verify(departmentDao, times(1)).update(department);
        verify(mockedDepartmentService, times(1))
                .addPersonToDepartment(department.getId(), person.getId());
    }

    @Test
    void removePersonToDepartment() {
        // TODO: NotImplemented
        final DepartmentService mockedDepartmentService = mock(DepartmentService.class);
        final Department department = DepartmentHelper.buildDepartment();
        final Person person = PersonHelper.buildPerson();

        when(departmentDao.findById(any(Integer.class))).thenReturn(department);
        when(personDao.findById(any(Integer.class))).thenReturn(person);
        when(departmentDao.update(any(Department.class))).thenReturn(department);
        when(personDao.update(any(Person.class))).thenReturn(person);

        departmentService.removePersonFromDepartment(department.getId(), person.getId());

        mockedDepartmentService.removePersonFromDepartment(department.getId(), person.getId());

        verify(departmentDao, times(1)).findById(department.getId());
        verify(personDao, times(1)).findById(person.getId());
        verify(departmentDao, times(1)).update(department);
        verify(personDao, times(1)).update(person);
        verify(mockedDepartmentService, times(1))
                .removePersonFromDepartment(department.getId(), person.getId());
    }

    @Test
    void closeDepartment() {
        // TODO: NotImplemented
        final Department department = DepartmentHelper.buildDepartment();
        final Person person = PersonHelper.buildPerson();
        department.addPerson(person);

        when(departmentDao.findById(any(Integer.class))).thenReturn(department);
        when(departmentDao.update(any(Department.class))).thenReturn(department);
        when(personDao.update(any(Person.class))).thenReturn(person);

        departmentService.closeDepartment(department.getId());

        assertNull(person.getDepartment());
        assertTrue(department.isClosed());
    }
}