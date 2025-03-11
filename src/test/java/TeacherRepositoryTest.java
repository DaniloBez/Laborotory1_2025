import Entity.Person.TeacherEntity;
import Repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherRepositoryTest {
    private TeacherRepository repo;

    @BeforeEach
    void setUp() {
        repo = new TeacherRepository();
    }

    static Stream<TeacherEntity> teacherProvider(){
        return Stream.of(
                new TeacherEntity("teacherName", "teacherSurname", "teacherMiddleName"),
                new TeacherEntity("teacher!!_!Name", "teacher!!_!Surname", "teacher!!_!MiddleName")
        );
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testCreateTeacher(TeacherEntity teacher) {
        repo.createTeacher(teacher);
        assertEquals(1, repo.getTeachers().length);
        assertEquals(teacher, repo.getTeacher(teacher.getId()));
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testUpdateTeacher(TeacherEntity teacher) {
        repo.createTeacher(teacher);

        teacher.setName("newName");

        repo.updateTeacher(teacher.getId(), teacher);

        assertEquals(teacher, repo.getTeacher(teacher.getId()));
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testCantUpdateTeacher(TeacherEntity teacher) {
        repo.createTeacher(teacher);

        String name = teacher.getName();

        teacher.setName("newName");
        repo.updateTeacher("teacher.getId()", teacher);

        teacher.setName(name);

        assertEquals(teacher, repo.getTeacher(teacher.getId()));
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testDeletePerson(TeacherEntity teacher) {
        repo.createTeacher(teacher);
        assertEquals(1, repo.getTeachers().length);

        repo.deleteTeacher(teacher.getId());
        assertEquals(0, repo.getTeachers().length);
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testCantDeletePerson(TeacherEntity teacher) {
        repo.createTeacher(teacher);
        assertEquals(1, repo.getTeachers().length);

        repo.deleteTeacher("teacher.getId()");
        assertEquals(1, repo.getTeachers().length);
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testGetPersons(TeacherEntity teacher) {
        TeacherEntity fakeTeacher = new TeacherEntity("studentName", "studentSurname", "StudentMiddleName");

        repo.createTeacher(teacher);
        repo.createTeacher(fakeTeacher);

        TeacherEntity[] teacherEntities = {teacher, fakeTeacher};

        assertArrayEquals(teacherEntities, repo.getTeachers());
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testGetPersonById(TeacherEntity teacher) {
        repo.createTeacher(teacher);

        assertEquals(teacher, repo.getTeacher(teacher.getId()));
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testCantGetPersonById(TeacherEntity teacher) {
        repo.createTeacher(teacher);

        assertNull(repo.getTeacher("teacher.getId()"));
    }
}
