import Entity.*;
import Entity.Person.*;
import Repository.*;
import Service.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {
    private Service service;

    private StudentRepository studentRepo;
    private TeacherRepository teacherRepo;
    private DepartmentRepository deptRepo;
    private FacultyRepository facultyRepo;

    @BeforeEach
    public void setUp() {
        studentRepo = new StudentRepository();
        teacherRepo = new TeacherRepository();
        deptRepo = new DepartmentRepository();
        facultyRepo = new FacultyRepository();

        service = new Service(studentRepo, teacherRepo, deptRepo, facultyRepo);
    }

    //region Faculty
    static Stream<FacultyEntity> facultyProvider(){
        return Stream.of(
                new FacultyEntity("FI"),
                new FacultyEntity("Kma-Pro")
        );
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testCreateFaculty(FacultyEntity faculty) {
        service.createFaculty(faculty);

        assertEquals(1, facultyRepo.getFaculties().length);
        assertEquals(faculty, facultyRepo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testCantCreateFacultyWithNotUniqueName(FacultyEntity faculty) {
        service.createFaculty(faculty);
        service.createFaculty(faculty);

        assertEquals(1, facultyRepo.getFaculties().length);
        assertEquals(faculty, facultyRepo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testUpdateFaculty(FacultyEntity faculty){
        service.createFaculty(faculty);

        faculty.setDepartmentIds(new String[]{"1", "2", "3"});
        service.updateFaculty(faculty.getId(), faculty);

        assertEquals(faculty, facultyRepo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testCantUpdateFaculty(FacultyEntity faculty){
        service.createFaculty(faculty);

        faculty.setDepartmentIds(new String[]{"1", "2", "3"});
        service.updateFaculty("faculty.getId()", faculty);

        faculty.setDepartmentIds(new String[0]);
        assertEquals(faculty, facultyRepo.getFaculty(faculty.getId()));
    }


    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testDeleteFaculty(FacultyEntity faculty) {
        service.createFaculty(faculty);
        assertEquals(1, facultyRepo.getFaculties().length);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());
        assertEquals(1, deptRepo.getDepartments().length);

        TeacherEntity teacher = new TeacherEntity("name", "surname", "middleName");
        service.createTeacher(teacher, department.getId());
        assertEquals(1, teacherRepo.getTeachers().length);

        StudentEntity student = new StudentEntity("name", "surname", "middleName", 1, 1);
        service.createStudent(student, department.getId());
        assertEquals(1, studentRepo.getStudents().length);

        service.deleteFaculty(faculty.getId());
        assertEquals(0, facultyRepo.getFaculties().length);
        assertEquals(0, deptRepo.getDepartments().length);
        assertEquals(0, studentRepo.getStudents().length);
        assertEquals(0, teacherRepo.getTeachers().length);
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testCantDeleteFaculty(FacultyEntity faculty) {
        service.createFaculty(faculty);
        assertEquals(1, facultyRepo.getFaculties().length);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());
        assertEquals(1, deptRepo.getDepartments().length);

        TeacherEntity teacher = new TeacherEntity("name", "surname", "middleName");
        service.createTeacher(teacher, department.getId());
        assertEquals(1, teacherRepo.getTeachers().length);

        StudentEntity student = new StudentEntity("name", "surname", "middleName", 1, 1);
        service.createStudent(student, department.getId());
        assertEquals(1, studentRepo.getStudents().length);

        service.deleteFaculty("faculty.getId()");
        assertEquals(1, facultyRepo.getFaculties().length);
        assertEquals(1, deptRepo.getDepartments().length);
        assertEquals(1, studentRepo.getStudents().length);
        assertEquals(1, teacherRepo.getTeachers().length);
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testGetFaculties(FacultyEntity faculty) {
        FacultyEntity faculty2 = new FacultyEntity("Fi2");
        service.createFaculty(faculty);
        service.createFaculty(faculty2);

        FacultyEntity[] faculties = {faculty, faculty2};
        assertArrayEquals(faculties, facultyRepo.getFaculties());
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testGetFacultyById(FacultyEntity faculty) {
        service.createFaculty(faculty);

        assertEquals(faculty, facultyRepo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("facultyProvider")
    void testCantGetFacultyById(FacultyEntity faculty) {
        service.createFaculty(faculty);

        assertNull(facultyRepo.getFaculty("faculty.getId()"));
    }
    //endregion

    //region Department
    static Stream<DepartmentEntity> departmentProvider(){
        return Stream.of(
                new DepartmentEntity("FI"),
                new DepartmentEntity("Kma-Pro")
        );
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCreateDepartment(DepartmentEntity department) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        service.createDepartment(department, faculty.getId());

        assertEquals(1, deptRepo.getDepartments().length);
        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantCreateDepartmentWithNotUniqueName(DepartmentEntity department) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        service.createDepartment(department, faculty.getId());
        service.createDepartment(department, faculty.getId());

        assertEquals(1, deptRepo.getDepartments().length);
        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantCreateDepartment(DepartmentEntity department) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        service.createDepartment(department, "faculty.getId()");

        assertEquals(0, deptRepo.getDepartments().length);
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testUpdateDepartment(DepartmentEntity department) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        service.createDepartment(department, faculty.getId());

        department.setStudentIds(new String[]{"1", "2", "3"});
        service.updateDepartment(department.getId(), department);

        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantUpdateDepartment(DepartmentEntity department) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        service.createDepartment(department, faculty.getId());

        department.setStudentIds(new String[]{"1", "2", "3"});
        service.updateDepartment("department.getId()", department);

        department.setStudentIds(new String[0]);
        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testDeleteDepartment(DepartmentEntity department) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);
        assertEquals(1, facultyRepo.getFaculties().length);

        service.createDepartment(department, faculty.getId());
        assertEquals(1, deptRepo.getDepartments().length);

        TeacherEntity teacher = new TeacherEntity("name", "surname", "middleName");
        service.createTeacher(teacher, department.getId());
        assertEquals(1, teacherRepo.getTeachers().length);

        StudentEntity student = new StudentEntity("name", "surname", "middleName", 1, 1);
        service.createStudent(student, department.getId());
        assertEquals(1, studentRepo.getStudents().length);

        service.deleteDepartment(department.getId());
        assertEquals(0, deptRepo.getDepartments().length);
        assertEquals(0, studentRepo.getStudents().length);
        assertEquals(0, teacherRepo.getTeachers().length);

        faculty.setDepartmentIds(new String[0]);
        assertEquals(faculty, facultyRepo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantDeleteDepartment(DepartmentEntity department) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);
        assertEquals(1, facultyRepo.getFaculties().length);

        service.createDepartment(department, faculty.getId());
        assertEquals(1, deptRepo.getDepartments().length);

        TeacherEntity teacher = new TeacherEntity("name", "surname", "middleName");
        service.createTeacher(teacher, department.getId());
        assertEquals(1, teacherRepo.getTeachers().length);

        StudentEntity student = new StudentEntity("name", "surname", "middleName", 1, 1);
        service.createStudent(student, department.getId());
        assertEquals(1, studentRepo.getStudents().length);

        service.deleteDepartment("department.getId()");
        assertEquals(1, deptRepo.getDepartments().length);
        assertEquals(1, studentRepo.getStudents().length);
        assertEquals(1, teacherRepo.getTeachers().length);
        assertEquals(faculty, facultyRepo.getFaculty(faculty.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testGetDepartments(DepartmentEntity department) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        FacultyEntity faculty2 = new FacultyEntity("Faculty2");
        service.createFaculty(faculty2);

        DepartmentEntity department2 = new DepartmentEntity("Di2");
        service.createDepartment(department, faculty.getId());
        service.createDepartment(department2, faculty2.getId());

        DepartmentEntity[] faculties = {department, department2};
        assertArrayEquals(faculties, deptRepo.getDepartments());
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testGetDepartmentById(DepartmentEntity department) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        service.createDepartment(department, faculty.getId());

        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("departmentProvider")
    void testCantGetDepartmentById(DepartmentEntity department) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        service.createDepartment(department, "faculty.getId()");

        assertNull(deptRepo.getDepartment(department.getId()));
    }
    //endregion

    //region Student
    static Stream<StudentEntity> studentProvider(){
        return Stream.of(
                new StudentEntity("student___$%1_Name", "teacher__!@234_Surname", "teacher__!Middle_++Name", 5, 6),
                new StudentEntity("studentName", "studentSurname", "StudentMiddleName", 1, 12)
        );
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testCreateStudent(StudentEntity student) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createStudent(student, department.getId());

        assertEquals(1, studentRepo.getStudents().length);
        assertEquals(student, studentRepo.getStudent(student.getId()));
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testCantCreateNotUniqueStudent(StudentEntity student) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createStudent(student, department.getId());
        service.createStudent(student, department.getId());

        assertEquals(1, studentRepo.getStudents().length);
        assertEquals(student, studentRepo.getStudent(student.getId()));
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testCantCreateStudent(StudentEntity student) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createStudent(student, "department.getId()");

        assertEquals(0, studentRepo.getStudents().length);
        assertNull(studentRepo.getStudent(student.getId()));
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testUpdateStudent(StudentEntity student) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createStudent(student, department.getId());

        student.setName("newName");

        service.updateStudent(student.getId(), student);

        assertEquals(student, studentRepo.getStudent(student.getId()));
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testCantUpdateStudent(StudentEntity student) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createStudent(student, department.getId());

        String name = student.getName();

        student.setName("newName");
        service.updateStudent("student.getId()", student);

        student.setName(name);
        assertEquals(student, studentRepo.getStudent(student.getId()));
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testDeleteStudent(StudentEntity student) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);
        assertEquals(1, facultyRepo.getFaculties().length);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());
        assertEquals(1, deptRepo.getDepartments().length);

        service.createStudent(student, department.getId());
        assertEquals(1, studentRepo.getStudents().length);

        service.deleteStudent(student.getId());
        assertEquals(0, studentRepo.getStudents().length);

        department.setStudentIds(new String[0]);
        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testCantDeleteStudent(StudentEntity student) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);
        assertEquals(1, facultyRepo.getFaculties().length);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());
        assertEquals(1, deptRepo.getDepartments().length);

        service.createStudent(student, department.getId());
        assertEquals(1, studentRepo.getStudents().length);

        service.deleteStudent("student.getId()");
        assertEquals(1, studentRepo.getStudents().length);

        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testGetStudents(StudentEntity student) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        DepartmentEntity department2 = new DepartmentEntity("Department2");
        service.createDepartment(department2, faculty.getId());

        StudentEntity student1 = new StudentEntity("studentName", "studentSurname", "StudentMiddleName", 1, 12);

        service.createStudent(student, department.getId());
        service.createStudent(student1, department2.getId());

        StudentEntity[] students = {student, student1};

        assertArrayEquals(students, studentRepo.getStudents());
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testGetStudentById(StudentEntity student) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createStudent(student, department.getId());

        assertEquals(student, studentRepo.getStudent(student.getId()));
    }

    @ParameterizedTest
    @MethodSource("studentProvider")
    void testCantGetStudentById(StudentEntity student) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createStudent(student, "department.getId()");

        assertNull(studentRepo.getStudent(student.getId()));
    }
    //endregion

    //region Teacher
    static Stream<TeacherEntity> teacherProvider(){
        return Stream.of(
                new TeacherEntity("teacherName", "teacherSurname", "teacherMiddleName"),
                new TeacherEntity("teacher!!_!Name", "teacher!!_!Surname", "teacher!!_!MiddleName")
        );
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testCreateTeacher(TeacherEntity teacher) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createTeacher(teacher, department.getId());

        assertEquals(1, teacherRepo.getTeachers().length);
        assertEquals(teacher, teacherRepo.getTeacher(teacher.getId()));
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testCantCreateTeacher(TeacherEntity teacher) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createTeacher(teacher, "department.getId()");

        assertEquals(0, teacherRepo.getTeachers().length);
        assertNull(teacherRepo.getTeacher(teacher.getId()));
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testUpdateTeacher (TeacherEntity teacher) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createTeacher(teacher, department.getId());

        teacher.setName("newName");

        service.updateTeacher(teacher.getId(), teacher);

        assertEquals(teacher, teacherRepo.getTeacher(teacher.getId()));
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testCantUpdateTeacher(TeacherEntity teacher) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createTeacher(teacher, department.getId());

        String name = teacher.getName();

        teacher.setName("newName");
        service.updateTeacher("student.getId()", teacher);

        teacher.setName(name);
        assertEquals(teacher, teacherRepo.getTeacher(teacher.getId()));
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testDeleteTeacher(TeacherEntity teacher) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);
        assertEquals(1, facultyRepo.getFaculties().length);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());
        assertEquals(1, deptRepo.getDepartments().length);

        service.createTeacher(teacher, department.getId());
        assertEquals(1, teacherRepo.getTeachers().length);

        service.deleteTeacher(teacher.getId());
        assertEquals(0, teacherRepo.getTeachers().length);

        department.setTeacherIds(new String[0]);
        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testCantDeleteTeacher(TeacherEntity teacher) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);
        assertEquals(1, facultyRepo.getFaculties().length);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());
        assertEquals(1, deptRepo.getDepartments().length);

        service.createTeacher(teacher, department.getId());
        assertEquals(1, teacherRepo.getTeachers().length);

        service.deleteTeacher("student.getId()");
        assertEquals(1, teacherRepo.getTeachers().length);

        assertEquals(department, deptRepo.getDepartment(department.getId()));
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testGetTeachers(TeacherEntity teacher) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        DepartmentEntity department2 = new DepartmentEntity("Department2");
        service.createDepartment(department2, faculty.getId());

        TeacherEntity teacher1 = new TeacherEntity("studentName", "studentSurname", "StudentMiddleName");

        service.createTeacher(teacher, department.getId());
        service.createTeacher(teacher1, department2.getId());

        TeacherEntity[] teachers = {teacher, teacher1};

        assertArrayEquals(teachers, teacherRepo.getTeachers());
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testGetTeacherById(TeacherEntity teacher) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createTeacher(teacher, department.getId());

        assertEquals(teacher, teacherRepo.getTeacher(teacher.getId()));
    }

    @ParameterizedTest
    @MethodSource("teacherProvider")
    void testCantGetTeacherById(TeacherEntity teacher) {
        FacultyEntity faculty = new FacultyEntity("Faculty");
        service.createFaculty(faculty);

        DepartmentEntity department = new DepartmentEntity("Department");
        service.createDepartment(department, faculty.getId());

        service.createTeacher(teacher, "department.getId()");

        assertNull(teacherRepo.getTeacher(teacher.getId()));
    }
    //endregion
}
