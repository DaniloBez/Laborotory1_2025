import Entity.Person.StudentEntity;
import Entity.Person.TeacherEntity;

import Repository.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO *непрацюючі* тести
public class StudentRepositoryTest {
    private StudentRepository repo;

    @BeforeEach
    void setUp() {
        repo = new StudentRepository();
    }

    static Stream<PersonEntity> personProvider(){
        return Stream.of(
                new TeacherEntity("teacherName", "teacherSurname", "teacherMiddleName"),
                new StudentEntity("studentName", "studentSurname", "StudentMiddleName", 1, 12)
        );
    }

    @ParameterizedTest
    @MethodSource("personProvider")
    void testCreatePerson(PersonEntity person) {
        repo.createPerson(person);
        assertEquals(1, repo.getStudents().length);
        assertEquals(person, repo.getPerson(person.getId()));
    }

    @ParameterizedTest
    @MethodSource("personProvider")
    void testUpdatePerson(PersonEntity person) {
        repo.createPerson(person);

        person.setName("newName");

        repo.updatePerson(person.getId(), person);

        assertEquals(person, repo.getPerson(person.getId()));
    }

    @ParameterizedTest
    @MethodSource("personProvider")
    void testDeletePerson(PersonEntity person) {
        repo.createPerson(person);
        assertEquals(1, repo.getStudents().length);

        repo.deletePerson(person.getId());
        assertEquals(0, repo.getStudents().length);
    }

    @ParameterizedTest
    @MethodSource("personProvider")
    void testGetPersons(PersonEntity person) {
        StudentEntity student = new StudentEntity("studentName", "studentSurname", "StudentMiddleName", 1, 12);

        repo.createPerson(person);
        repo.createPerson(student);

        PersonEntity[] persons = {person, student};

        assertArrayEquals(persons, repo.getStudents());
    }

    @ParameterizedTest
    @MethodSource("personProvider")
    void testGetPersonById(PersonEntity person) {
        repo.createPerson(person);

        assertEquals(person, repo.getPerson(person.getId()));
    }
}
