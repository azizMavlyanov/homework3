package ru.digitalhabbits.homework3.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 80, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "BOOL NOT NULL DEFAULT FALSE")
    private boolean closed;

    @OneToMany(
            mappedBy = "department",
            cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    private List<Person> persons = new ArrayList<>();

    public void addPerson(Person person) {
        persons.add(person);
        person.setDepartment(this);
    }

    public void removePerson(Person person) {
        persons.remove(person);
        person.setDepartment(null);
    }
}