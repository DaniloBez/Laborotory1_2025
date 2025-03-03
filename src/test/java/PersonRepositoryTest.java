import Entity.PersonEntity;
import Entity.PersonType;
import Repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonRepositoryTest {
    private PersonRepository repo;

    @BeforeEach
    void setUp() {
        repo = new PersonRepository();
    }

    @Test
    void testCreatePerson() {
        PersonEntity person = new PersonEntity("name", "surname",
                "middleName", "course", "group", PersonType.STUDENT);
        repo.createPerson(person);
        assertEquals(1, repo.getPersons().length);
        assertEquals(person, repo.getPerson(person.getId()));
    }

    @Test
    void testUpdatePerson() {
        PersonEntity person = new PersonEntity("name", "surname",
                "middleName", "course", "group", PersonType.STUDENT);
        repo.createPerson(person);

        person.setName("newName");

        repo.updatePerson(person.getId(), person);

        assertEquals(person, repo.getPerson(person.getId()));
    }

    @Test
    void testDeletePerson() {
        PersonEntity person = new PersonEntity("name", "surname",
                "middleName", "course", "group", PersonType.STUDENT);
        repo.createPerson(person);
        assertEquals(1, repo.getPersons().length);

        repo.deletePerson(person.getId());
        assertEquals(0, repo.getPersons().length);
    }

    @Test
    void testGetPersons() {
        PersonEntity student = new PersonEntity("name", "surname",
                "middleName", "course", "group", PersonType.STUDENT);
        PersonEntity teacher = new PersonEntity("name", "surname",
                "middleName", "course", "group", PersonType.TEACHER);

        repo.createPerson(student);
        repo.createPerson(teacher);

        PersonEntity[] persons = {student, teacher};

        assertArrayEquals(persons, repo.getPersons());
    }

    @Test
    void testGetPersonById() {
        PersonEntity student = new PersonEntity("name", "surname",
                "middleName", "course", "group", PersonType.STUDENT);
        repo.createPerson(student);

        assertEquals(student, repo.getPerson(student.getId()));
    }
}
