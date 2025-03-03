import Entity.DepartmentEntity;
import Repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepartmentRepositoryTest {
    private DepartmentRepository repo;

    @BeforeEach
    void setUp() {
        repo = new DepartmentRepository();
    }

    @Test
    void testCreateDepartment() {
        DepartmentEntity department = new DepartmentEntity("Di");
        repo.createDepartment(department);

        assertEquals(1, repo.getDepartments().length);
        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @Test
    void testUpdateDepartment() {
        DepartmentEntity department = new DepartmentEntity("Di");
        repo.createDepartment(department);

        department.setPersonIds(new String[]{"1", "2", "3"});
        repo.updateDepartment(department.getId(), department);

        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @Test
    void testDeleteDepartment() {
        DepartmentEntity department = new DepartmentEntity("Di");
        repo.createDepartment(department);
        assertEquals(1, repo.getDepartments().length);

        repo.deleteDepartment(department.getId());
        assertEquals(0, repo.getDepartments().length);
    }

    @Test
    void testGetDepartments() {
        DepartmentEntity department = new DepartmentEntity("Di");
        DepartmentEntity department2 = new DepartmentEntity("Di2");
        repo.createDepartment(department);
        repo.createDepartment(department2);

        DepartmentEntity[] faculties = {department, department2};
        assertArrayEquals(faculties, repo.getDepartments());
    }

    @Test
    void testGetDepartmentById() {
        DepartmentEntity department = new DepartmentEntity("Di");
        repo.createDepartment(department);

        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @Test
    void testAddPersonToDepartment() {
        DepartmentEntity department = new DepartmentEntity("Di");
        repo.createDepartment(department);
        repo.addPersonToDepartment(department.getId(), "1");

        department.setPersonIds(new String[]{"1"});
        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @Test
    void testRemovePersonFromDepartment() {
        DepartmentEntity department = new DepartmentEntity("Di");
        repo.createDepartment(department);
        repo.addPersonToDepartment(department.getId(), "1");

        repo.removePersonFromDepartment(department.getId(), "1");
        assertEquals(department, repo.getDepartment(department.getId()));
    }
}
