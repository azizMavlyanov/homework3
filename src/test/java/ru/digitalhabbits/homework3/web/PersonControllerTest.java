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
import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;
import ru.digitalhabbits.homework3.service.PersonService;
import ru.digitalhabbits.homework3.utils.PersonHelper;

import java.util.List;

import static java.lang.String.format;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

    @MockBean
    private PersonService personService;

    @Autowired
    private MockMvc mockMvc;

    private final Gson gson = new GsonBuilder().create();

    @Test
    void persons() throws Exception {
        // TODO: NotImplemented
        final Person person = PersonHelper.buildPerson();
        final PersonResponse response = PersonHelper.buildPersonResponse(person);
        when(personService.findAllPersons()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(response.getId()))
                .andExpect(jsonPath("$[0].fullName").value(response.getFullName()))
                .andExpect(jsonPath("$[0].age").value(response.getAge()))
                .andExpect(jsonPath("$[0].department").value(response.getDepartment()));


    }

    @Test
    void person() throws Exception {
        // TODO: NotImplemented
        final Person person = PersonHelper.buildPerson();
        final PersonResponse response = PersonHelper.buildPersonResponse(person);

        when(personService.getPerson(any(Integer.class))).thenReturn(response);

        mockMvc.perform(get(format("/api/v1/persons/%s", response.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.fullName").value(response.getFullName()))
                .andExpect(jsonPath("$.age").value(response.getAge()))
                .andExpect(jsonPath("$.department").value(response.getDepartment()));
    }

    @Test
    void createPerson() throws Exception {
        // TODO: NotImplemented
        final Person person = PersonHelper.buildPerson();
        final PersonRequest request = PersonHelper.buildPersonRequest();
        request.setFirstName(person.getFirstName())
                .setLastName(person.getLastName())
                .setMiddleName(person.getMiddleName())
                .setAge(person.getAge());
        final PersonResponse response = PersonHelper.buildPersonResponse(person);
        when(personService.createPerson(request)).thenReturn(response.getId());

        mockMvc.perform(post("/api/v1/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void updatePerson() throws Exception {
        // TODO: NotImplemented
        final Person person = PersonHelper.buildPerson();
        final PersonRequest request = PersonHelper.buildPersonRequest();
        request.setFirstName(person.getFirstName())
                .setLastName(person.getLastName())
                .setMiddleName(person.getMiddleName())
                .setAge(person.getAge());
        final PersonResponse response = PersonHelper.buildPersonResponse(person);
        when(personService.updatePerson(person.getId(), request)).thenReturn(response);

        mockMvc.perform(patch(format("/api/v1/persons/%s", response.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId()))
                .andExpect(jsonPath("$.fullName").value(response.getFullName()))
                .andExpect(jsonPath("$.age").value(response.getAge()))
                .andExpect(jsonPath("$.department").value(response.getDepartment()));
    }

    @Test
    void deletePerson() throws Exception {
        // TODO: NotImplemented
        final Person person = PersonHelper.buildPerson();
        final PersonResponse response = PersonHelper.buildPersonResponse(person);
        doNothing().when(personService).deletePerson(person.getId());

        mockMvc.perform(delete(format("/api/v1/persons/%s", response.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}