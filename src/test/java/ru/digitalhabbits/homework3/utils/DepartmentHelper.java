package ru.digitalhabbits.homework3.utils;

import ru.digitalhabbits.homework3.domain.Department;
import ru.digitalhabbits.homework3.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class DepartmentHelper {
    public static DepartmentInfo buildDepartmentInfo(Department department) {
        return new DepartmentInfo()
                .setId(department.getId())
                .setName(department.getName());
    }

    public static Department buildDepartment() {
        return new Department()
                .setId(nextInt())
                .setName(randomAlphabetic(7))
                .setPersons(new ArrayList<>())
                .setClosed(false);
    }

    public static DepartmentRequest buildDepartmentRequest() {
        return new DepartmentRequest()
                .setName(randomAlphabetic(7));
    }

    public static DepartmentResponse buildDepartmentResponse(Department department) {
        List<PersonInfo> personInfoList = department.getPersons()
                .stream()
                .map(PersonHelper::buildPersonInfo)
                .collect(Collectors.toList());

        return new DepartmentResponse()
                .setId(department.getId())
                .setName(department.getName())
                .setClosed(department.isClosed())
                .setPersons(personInfoList);
    }

    public static DepartmentShortResponse buildDepartmentShortResponse(Department department) {
        return new DepartmentShortResponse()
                .setId(department.getId())
                .setName(department.getName());
    }
}
