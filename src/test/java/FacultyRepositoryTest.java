import Entity.FacultyEntity;
import Repository.FacultyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class FacultyRepositoryTest {
    private FacultyRepository repo;

    @BeforeEach
    void setUp() {
        repo = new FacultyRepository();
    }

    static Stream<FacultyEntity> facultyProvider(){
        return Stream.of(
                new FacultyEntity("FI"),
                new FacultyEntity("Kma-Pro")
        );
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testCreateFaculty(FacultyEntity faculty) {
        repo.createFaculty(faculty);

        assertEquals(1, repo.getFaculties().length);
        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testUpdateFaculty(FacultyEntity faculty) {
        repo.createFaculty(faculty);

        faculty.setDepartmentIds(new String[]{"1", "2", "3"});
        repo.updateFaculty(faculty.getId(), faculty);

        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testCantUpdateFaculty(FacultyEntity faculty) {
        repo.createFaculty(faculty);

        faculty.setDepartmentIds(new String[]{"1", "2", "3"});
        repo.updateFaculty("faculty.getId()", faculty);

        faculty.setDepartmentIds(new String[0]);
        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testDeleteFaculty(FacultyEntity faculty) {
        repo.createFaculty(faculty);
        assertEquals(1, repo.getFaculties().length);

        repo.deleteFaculty(faculty.getId());
        assertEquals(0, repo.getFaculties().length);
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testCantDeleteFaculty(FacultyEntity faculty) {
        repo.createFaculty(faculty);
        assertEquals(1, repo.getFaculties().length);

        repo.deleteFaculty("faculty.getId()");
        assertEquals(1, repo.getFaculties().length);
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testGetFaculties(FacultyEntity faculty) {
        FacultyEntity faculty2 = new FacultyEntity("Fi2");
        repo.createFaculty(faculty);
        repo.createFaculty(faculty2);

        FacultyEntity[] faculties = {faculty, faculty2};
        assertArrayEquals(faculties, repo.getFaculties());
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testGetFacultyById(FacultyEntity faculty) {
        repo.createFaculty(faculty);

        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testCantGetFacultyById(FacultyEntity faculty) {
        repo.createFaculty(faculty);

        assertNull(repo.getFaculty("faculty.getId()"));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testAddDepartmentToFaculty(FacultyEntity faculty) {
        repo.createFaculty(faculty);
        repo.addDepartmentToFaculty(faculty.getId(), "1");

        faculty.setDepartmentIds(new String[]{"1"});
        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testCantAddDepartmentToFaculty(FacultyEntity faculty) {
        repo.createFaculty(faculty);
        repo.addDepartmentToFaculty("faculty.getId()", "1");

        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testRemoveDepartmentFromFaculty(FacultyEntity faculty) {
        repo.createFaculty(faculty);
        repo.addDepartmentToFaculty(faculty.getId(), "1");

        repo.removeDepartmentFromFaculty(faculty.getId(), "1");
        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testCantRemoveDepartmentFromFaculty(FacultyEntity faculty) {
        repo.createFaculty(faculty);
        repo.addDepartmentToFaculty(faculty.getId(), "1");

        faculty.setDepartmentIds(new String[]{"1"});

        repo.removeDepartmentFromFaculty(faculty.getId(), "2");
        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testCantRemoveDepartmentFromFaculty2(FacultyEntity faculty) {
        repo.createFaculty(faculty);
        repo.addDepartmentToFaculty(faculty.getId(), "1");

        faculty.setDepartmentIds(new String[]{"1"});

        repo.removeDepartmentFromFaculty("faculty.getId()", "1");
        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }
}
