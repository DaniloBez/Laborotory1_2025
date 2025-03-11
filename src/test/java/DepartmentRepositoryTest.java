import Entity.DepartmentEntity;

import Repository.DepartmentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class DepartmentRepositoryTest {
    private DepartmentRepository repo;

    @BeforeEach
    void setUp() {
        repo = new DepartmentRepository();
    }

    static Stream<DepartmentEntity> departmentProvider(){
        return Stream.of(
                new DepartmentEntity("FI"),
                new DepartmentEntity("Kma-Pro")
        );
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCreateDepartment(DepartmentEntity department) {
        repo.createDepartment(department);

        assertEquals(1, repo.getDepartments().length);
        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testUpdateDepartment(DepartmentEntity department) {
        repo.createDepartment(department);

        department.setStudentIds(new String[]{"1", "2", "3"});
        department.setStudentIds(new String[]{"3", "2", "3"});
        repo.updateDepartment(department.getId(), department);

        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantUpdateDepartment(DepartmentEntity department) {
        repo.createDepartment(department);

        department.setStudentIds(new String[]{"1", "2", "3"});
        repo.updateDepartment("department.getId()", department);

        department.setStudentIds(new String[0]);
        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testDeleteDepartment(DepartmentEntity department) {
        repo.createDepartment(department);
        assertEquals(1, repo.getDepartments().length);

        repo.deleteDepartment(department.getId());
        assertEquals(0, repo.getDepartments().length);
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantDeleteDepartment(DepartmentEntity department) {
        repo.createDepartment(department);
        assertEquals(1, repo.getDepartments().length);

        repo.deleteDepartment("department.getId()");
        assertEquals(1, repo.getDepartments().length);
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testGetDepartments(DepartmentEntity department) {
        DepartmentEntity department2 = new DepartmentEntity("Di2");
        repo.createDepartment(department);
        repo.createDepartment(department2);

        DepartmentEntity[] faculties = {department, department2};
        assertArrayEquals(faculties, repo.getDepartments());
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testGetDepartmentById(DepartmentEntity department) {
        repo.createDepartment(department);

        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantGetDepartmentById(DepartmentEntity department) {
        repo.createDepartment(department);

        assertNull(repo.getDepartment("department.getId()"));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testAddStudentToDepartment(DepartmentEntity department) {
        repo.createDepartment(department);
        repo.addStudentToDepartment(department.getId(), "1");

        department.setStudentIds(new String[]{"1"});
        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantAddStudentToDepartment(DepartmentEntity department) {
        repo.createDepartment(department);
        repo.addStudentToDepartment("department.getId()", "1");

        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testRemoveStudentFromDepartment(DepartmentEntity department) {
        repo.createDepartment(department);
        repo.addStudentToDepartment(department.getId(), "1");

        repo.removeStudentFromDepartment(department.getId(), "1");
        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantRemoveStudentFromDepartment(DepartmentEntity department) {
        repo.createDepartment(department);
        repo.addStudentToDepartment(department.getId(), "1");

        department.setStudentIds(new String[]{"1"});

        repo.removeStudentFromDepartment(department.getId(), "2");
        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantRemoveStudentFromDepartment2(DepartmentEntity department) {
        repo.createDepartment(department);
        repo.addStudentToDepartment(department.getId(), "1");

        department.setStudentIds(new String[]{"1"});

        repo.removeStudentFromDepartment("department.getId()", "2");
        assertEquals(department, repo.getDepartment(department.getId()));
    }


    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testAddTeacherToDepartment(DepartmentEntity department) {
        repo.createDepartment(department);
        repo.addTeacherToDepartment(department.getId(), "1");

        department.setTeacherIds(new String[]{"1"});
        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantAddTeacherToDepartment(DepartmentEntity department) {
        repo.createDepartment(department);
        repo.addTeacherToDepartment("department.getId()", "1");

        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testRemoveTeacherFromDepartment(DepartmentEntity department) {
        repo.createDepartment(department);
        repo.addTeacherToDepartment(department.getId(), "1");

        repo.removeTeacherFromDepartment(department.getId(), "1");
        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantTeacherFromDepartment(DepartmentEntity department) {
        repo.createDepartment(department);
        repo.addTeacherToDepartment(department.getId(), "1");

        department.setTeacherIds(new String[]{"1"});

        repo.removeTeacherFromDepartment(department.getId(), "2");
        assertEquals(department, repo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantTeacherFromDepartment2(DepartmentEntity department) {
        repo.createDepartment(department);
        repo.addTeacherToDepartment(department.getId(), "1");

        department.setTeacherIds(new String[]{"1"});

        repo.removeTeacherFromDepartment("department.getId()", "1");
        assertEquals(department, repo.getDepartment(department.getId()));
    }
}
