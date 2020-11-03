package ru.digitalhabbits.homework3.web;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.*;
import ru.digitalhabbits.homework3.service.DepartmentService;
import ru.digitalhabbits.homework3.utils.DepartmentHelper;
import ru.digitalhabbits.homework3.utils.PersonHelper;

import java.util.List;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DepartmentController.class)
class DepartmentControllerTest {

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new GsonBuilder().create();

    @Test
    void departments() throws Exception {
        // TODO: NotImplemented
        final Department department = DepartmentHelper.buildDepartment();
        final DepartmentShortResponse response = DepartmentHelper.buildDepartmentShortResponse(department);

        when(departmentService.findAllDepartments()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(response.getId()))
                .andExpect(jsonPath("$[0].name").value(response.getName()));
    }

    @Test
    void department() throws Exception {
        // TODO: NotImplemented
        final Department department = DepartmentHelper.buildDepartment();
        final DepartmentResponse response = DepartmentHelper.buildDepartmentResponse(department);

        when(departmentService.getDepartment(any(Integer.class))).thenReturn(response);

        mockMvc.perform(get(format("/api/v1/departments/%s", response.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.persons").isArray())
                .andExpect(jsonPath("$.persons").value(response.getPersons()));
    }

    @Test
    void createDepartment() throws Exception {
        // TODO: NotImplemented
        final Department department = DepartmentHelper.buildDepartment();
        final DepartmentRequest request = DepartmentHelper.buildDepartmentRequest();
        request.setName(department.getName());

        final DepartmentShortResponse response = DepartmentHelper.buildDepartmentShortResponse(department);
        when(departmentService.createDepartment(request)).thenReturn(response.getId());

        mockMvc.perform(post("/api/v1/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updateDepartment() throws Exception {
        // TODO: NotImplemented
        final Department department = DepartmentHelper.buildDepartment();
        final DepartmentRequest request = DepartmentHelper.buildDepartmentRequest();
        request.setName(department.getName());
        final DepartmentResponse response = DepartmentHelper.buildDepartmentResponse(department);
        when(departmentService.updateDepartment(department.getId(), request)).thenReturn(response);

        mockMvc.perform(patch(format("/api/v1/departments/%s", response.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.persons").isArray())
                .andExpect(jsonPath("$.persons").value(response.getPersons()));
    }

    @Test
    void deleteDepartment() throws Exception {
        // TODO: NotImplemented
        final Department department = DepartmentHelper.buildDepartment();
        final DepartmentResponse response = DepartmentHelper.buildDepartmentResponse(department);
        doNothing().when(departmentService).deleteDepartment(department.getId());

        mockMvc.perform(delete(format("/api/v1/departments/%s", response.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void addPersonToDepartment() throws Exception {
        // TODO: NotImplemented
        final Department department = DepartmentHelper.buildDepartment();
        final Person person = PersonHelper.buildPerson();
        doNothing().when(departmentService).addPersonToDepartment(department.getId(), person.getId());

        mockMvc.perform(post(format("/api/v1/departments/%s/%s", department.getId(), person.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void removePersonToDepartment() throws Exception {
        // TODO: NotImplemented
        final Department department = DepartmentHelper.buildDepartment();
        final Person person = PersonHelper.buildPerson();
        doNothing().when(departmentService).removePersonFromDepartment(department.getId(), person.getId());

        mockMvc.perform(delete(format("/api/v1/departments/%s/%s", department.getId(), person.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void closeDepartment() throws Exception {
        // TODO: NotImplemented
        final Department department = DepartmentHelper.buildDepartment();
        doNothing().when(departmentService).closeDepartment(department.getId());

        mockMvc.perform(post(format("/api/v1/departments/%s/close", department.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}