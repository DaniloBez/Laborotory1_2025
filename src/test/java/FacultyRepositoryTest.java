import Entity.FacultyEntity;
import Repository.FacultyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FacultyRepositoryTest {
    private FacultyRepository repo;

    @BeforeEach
    void setUp() {
        repo = new FacultyRepository();
    }

    @Test
    void testCreateFaculty() {
        FacultyEntity faculty = new FacultyEntity("Fi");
        repo.createFaculty(faculty);

        assertEquals(1, repo.getFaculties().length);
        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }

    @Test
    void testUpdateFaculty() {
        FacultyEntity faculty = new FacultyEntity("Fi");
        repo.createFaculty(faculty);

        faculty.setDepartmentIds(new String[]{"1", "2", "3"});
        repo.updateFaculty(faculty.getId(), faculty);

        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }

    @Test
    void testDeleteFaculty() {
        FacultyEntity faculty = new FacultyEntity("Fi");
        repo.createFaculty(faculty);
        assertEquals(1, repo.getFaculties().length);

        repo.deleteFaculty(faculty.getId());
        assertEquals(0, repo.getFaculties().length);
    }

    @Test
    void testGetFaculties() {
        FacultyEntity faculty = new FacultyEntity("Fi");
        FacultyEntity faculty2 = new FacultyEntity("Fi2");
        repo.createFaculty(faculty);
        repo.createFaculty(faculty2);

        FacultyEntity[] faculties = {faculty, faculty2};
        assertArrayEquals(faculties, repo.getFaculties());
    }

    @Test
    void testGetFacultyById() {
        FacultyEntity faculty = new FacultyEntity("Fi");
        repo.createFaculty(faculty);

        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }

    @Test
    void testAddDepartmentToFaculty() {
        FacultyEntity faculty = new FacultyEntity("Fi");
        repo.createFaculty(faculty);
        repo.addDepartmentToFaculty(faculty.getId(), "1");

        faculty.setDepartmentIds(new String[]{"1"});
        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }

    @Test
    void testRemoveDepartmentFromFaculty() {
        FacultyEntity faculty = new FacultyEntity("Fi");
        repo.createFaculty(faculty);
        repo.addDepartmentToFaculty(faculty.getId(), "1");

        repo.removeDepartmentFromFaculty(faculty.getId(), "1");
        assertEquals(faculty, repo.getFaculty(faculty.getId()));
    }
}
