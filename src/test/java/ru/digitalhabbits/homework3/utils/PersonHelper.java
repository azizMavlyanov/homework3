package ru.digitalhabbits.homework3.utils;

import ru.digitalhabbits.homework3.domain.Person;
import ru.digitalhabbits.homework3.model.PersonInfo;
import ru.digitalhabbits.homework3.model.PersonRequest;
import ru.digitalhabbits.homework3.model.PersonResponse;

import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class PersonHelper {
    public static PersonRequest buildPersonRequest() {
        return new PersonRequest()
                .setAge(nextInt(10, 50))
                .setFirstName(randomAlphabetic(8))
                .setMiddleName(randomAlphabetic(8))
                .setLastName(randomAlphabetic(8));
    }

    public static PersonResponse buildPersonResponse(Person person) {
        PersonResponse personResponse = new PersonResponse()
                .setId(person.getId())
                .setAge(person.getAge())
                .setFullName(buildFullName(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getMiddleName()));
        if (person.getDepartment() != null) {
            personResponse.setDepartment(DepartmentHelper.buildDepartmentInfo(person.getDepartment()));
        }
        return personResponse;
    }

    public static Person buildPerson() {
        return new Person()
                .setId(nextInt())
                .setFirstName(randomAlphabetic(7))
                .setMiddleName(randomAlphabetic(7))
                .setLastName(randomAlphabetic(7))
                .setAge(nextInt(10, 50));
    }

    public static String buildFullName(String firstName, String lastName, String middleName) {
        return format("%s %s %s", firstName, lastName, middleName);
    }

    public static PersonInfo buildPersonInfo(Person person) {
        return new PersonInfo()
                .setId(person.getId())
                .setFullName(buildFullName(person.getFirstName(), person.getLastName(), person.getMiddleName()));
    }
}
