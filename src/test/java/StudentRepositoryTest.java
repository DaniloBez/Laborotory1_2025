import Entity.Person.StudentEntity;

import Repository.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StudentRepositoryTest {
    private StudentRepository repo;

    @BeforeEach
    void setUp() {
        repo = new StudentRepository();
    }

    static Stream<StudentEntity> studentProvider(){
        return Stream.of(
                new StudentEntity("student___$%1_Name", "teacher__!@234_Surname", "teacher__!Middle_++Name", 5, 6),
                new StudentEntity("studentName", "studentSurname", "StudentMiddleName", 1, 12)
        );
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testCreatePerson(StudentEntity student) {
        repo.createStudent(student);
        assertEquals(1, repo.getStudents().length);
        assertEquals(student, repo.getStudent(student.getId()));
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testUpdatePerson(StudentEntity student) {
        repo.createStudent(student);

        student.setName("newName");

        repo.updateStudent(student.getId(), student);

        assertEquals(student, repo.getStudent(student.getId()));
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testCantUpdatePerson(StudentEntity student) {
        repo.createStudent(student);

        String name = student.getName();

        student.setName("newName");
        repo.updateStudent(student.getId(), student);

        student.setName(name);
        assertEquals(student, repo.getStudent(student.getId()));
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testDeletePerson(StudentEntity student) {
        repo.createStudent(student);
        assertEquals(1, repo.getStudents().length);

        repo.deleteStudent(student.getId());
        assertEquals(0, repo.getStudents().length);
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testCantDeletePerson(StudentEntity student) {
        repo.createStudent(student);
        assertEquals(1, repo.getStudents().length);

        repo.deleteStudent("student.getId()");
        assertEquals(1, repo.getStudents().length);
    }


    @ParameterizedTest
    @MethodSource("studentProvider")
    void testGetPersons(StudentEntity student) {
        StudentEntity student2 = new StudentEntity("studentName", "studentSurname", "StudentMiddleName", 1, 12);

        repo.createStudent(student);
        repo.createStudent(student2);

        StudentEntity[] persons = {student, student2};

        assertArrayEquals(persons, repo.getStudents());
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testGetPersonById(StudentEntity student) {
        repo.createStudent(student);

        assertEquals(student, repo.getStudent(student.getId()));
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testCantGetPersonById(StudentEntity student) {
        repo.createStudent(student);

        assertNull(repo.getStudent("student.getId()"));
    }
}
