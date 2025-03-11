import Entity.DepartmentEntity;
import Entity.FacultyEntity;
import Entity.Person.StudentEntity;
import Entity.Person.TeacherEntity;

import Repository.DepartmentRepository;
import Repository.FacultyRepository;
import Repository.PersonRepository;

import Service.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO *непрацюючі* тести
public class ServiceTest {
    private Service service;

    private PersonRepository personRepo;
    private DepartmentRepository deptRepo;
    private FacultyRepository facultyRepo;

    @BeforeEach
    public void setUp() {
        personRepo = new PersonRepository();
        deptRepo = new DepartmentRepository();
        facultyRepo = new FacultyRepository();

        service = new Service(personRepo, deptRepo, facultyRepo);
    }

    //region Faculty
    @Test
    void testCreateFaculty(){
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        assertEquals(1, facultyRepo.getFaculties().length);
        assertEquals(faculty, facultyRepo.getFaculty(faculty.getId()));
    }

    @Test
    void testUpdateFaculty(){
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        faculty.setDepartmentIds(new String[]{"1", "2", "3"});
        service.updateFaculty(faculty.getId(), faculty);

        assertEquals(faculty, facultyRepo.getFaculty(faculty.getId()));
    }

    @Test
    void testDeleteFaculty() {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);
        assertEquals(1, facultyRepo.getFaculties().length);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());
        assertEquals(1, deptRepo.getDepartments().length);

        PersonEntity person = new TeacherEntity("name", "surname", "middleName");
        service.createPerson(person, department.getId());
        assertEquals(1, personRepo.getPersons().length);

        service.deleteFaculty(faculty.getId());
        assertEquals(0, facultyRepo.getFaculties().length);
        assertEquals(0, deptRepo.getDepartments().length);
        assertEquals(0, personRepo.getPersons().length);
    }

    @Test
    void testGetFaculties() {
        FacultyEntity faculty = new FacultyEntity("Fi");
        FacultyEntity faculty2 = new FacultyEntity("Fi2");
        service.createFaculty(faculty);
        service.createFaculty(faculty2);

        FacultyEntity[] faculties = {faculty, faculty2};
        assertArrayEquals(faculties, facultyRepo.getFaculties());
    }

    @Test
    void testGetFacultyById() {
        FacultyEntity faculty = new FacultyEntity("Fi");
        service.createFaculty(faculty);

        assertEquals(faculty, facultyRepo.getFaculty(faculty.getId()));
    }
    //endregion

    //region Department
    @Test
    void testCreateDepartment() {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Di");
        service.createDepartment(department, faculty.getId());

        assertEquals(1, deptRepo.getDepartments().length);
        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }

    @Test
    void testUpdateDepartment() {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Di");
        service.createDepartment(department, faculty.getId());

        department.setPersonIds(new String[]{"1", "2", "3"});
        service.updateDepartment(department.getId(), department);

        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }

    @Test
    void testDeleteDepartment() {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);
        assertEquals(1, facultyRepo.getFaculties().length);

        DepartmentEntity department = new DepartmentEntity("Di");
        service.createDepartment(department, faculty.getId());
        assertEquals(1, deptRepo.getDepartments().length);

        PersonEntity person = new TeacherEntity("name", "surname", "middleName");
        service.createPerson(person, department.getId());
        assertEquals(1, personRepo.getPersons().length);

        service.deleteDepartment(department.getId());
        assertEquals(0, deptRepo.getDepartments().length);
        assertEquals(0, personRepo.getPersons().length);

        faculty.setDepartmentIds(new String[0]);
        assertEquals(faculty, facultyRepo.getFaculty(faculty.getId()));
    }

    @Test
    void testGetDepartments() {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        FacultyEntity faculty2 = new FacultyEntity("Faculty2");
        service.createFaculty(faculty2);

        DepartmentEntity department = new DepartmentEntity("Di");
        DepartmentEntity department2 = new DepartmentEntity("Di2");
        service.createDepartment(department, faculty.getId());
        service.createDepartment(department2, faculty2.getId());

        DepartmentEntity[] faculties = {department, department2};
        assertArrayEquals(faculties, deptRepo.getDepartments());
    }

    @Test
    void testGetDepartmentById() {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Di");
        service.createDepartment(department, faculty.getId());

        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }
    //endregion

    static Stream<PersonEntity> personProvider(){
        return Stream.of(
                new TeacherEntity("teacherName", "teacherSurname", "teacherMiddleName"),
                new StudentEntity("studentName", "studentSurname", "StudentMiddleName", 1, 12)
        );
    }

    @ParameterizedTest
    @MethodSource("personProvider")
    void testCreatePerson(PersonEntity person) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createPerson(person, department.getId());
        assertEquals(1, personRepo.getPersons().length);
        assertEquals(person, personRepo.getPerson(person.getId()));
    }

    @ParameterizedTest
    @MethodSource("personProvider")
    void testUpdatePerson(PersonEntity person) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createPerson(person, department.getId());

        person.setName("newName");

        service.updatePerson(person.getId(), person);

        assertEquals(person, personRepo.getPerson(person.getId()));
    }

    @ParameterizedTest
    @MethodSource("personProvider")
    void testDeletePerson(PersonEntity person) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);
        assertEquals(1, facultyRepo.getFaculties().length);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());
        assertEquals(1, deptRepo.getDepartments().length);

        service.createPerson(person, department.getId());
        assertEquals(1, personRepo.getPersons().length);

        service.deletePerson(person.getId());
        assertEquals(0, personRepo.getPersons().length);

        department.setPersonIds(new String[0]);
        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("personProvider")
    void testGetPersons(PersonEntity person) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        DepartmentEntity department2 = new DepartmentEntity("Department2");
        service.createDepartment(department2, faculty.getId());

        StudentEntity student = new StudentEntity("studentName", "studentSurname", "StudentMiddleName", 1, 12);

        service.createPerson(person, department.getId());
        service.createPerson(student, department2.getId());

        PersonEntity[] persons = {person, student};

        assertArrayEquals(persons, personRepo.getPersons());
    }

    @ParameterizedTest
    @MethodSource("personProvider")
    void testGetPersonById(PersonEntity person) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createPerson(person, department.getId());

        assertEquals(person, personRepo.getPerson(person.getId()));
    }
}
