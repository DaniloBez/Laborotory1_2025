package Service;

import Entity.FacultyEntity;
import Entity.DepartmentEntity;
import Entity.Person.StudentEntity;
import Entity.Person.TeacherEntity;
import Repository.DepartmentRepository;
import Repository.FacultyRepository;
import Repository.StudentRepository;
import Repository.TeacherRepository;
import Utils.SortUtils;

import static java.lang.System.out;

/**
 * Сервісний клас, який забезпечує управління факультетами, кафедрами та особами.
 */
public class Service {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;

    /**
     * Конструктор сервісу.
     *
     * @param studentRepository репозиторій студентів
     * @param teacherRepository репозиторій вчителів
     * @param departmentRepository репозиторій кафедр
     * @param facultyRepository репозиторій факультетів
     */
    public Service(StudentRepository studentRepository, TeacherRepository teacherRepository, DepartmentRepository departmentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
        this.facultyRepository = facultyRepository;
    }

    //region Faculty
    /**
     * Створює новий факультет.
     *
     * @param faculty факультет для створення
     */
    public void createFaculty(FacultyEntity faculty){
        facultyRepository.createFaculty(faculty);
    }

    /**
     * Оновлює дані факультету.
     *
     * @param idFaculty ідентифікатор факультету
     * @param newFacultyData нові дані факультету
     */
    public void updateFaculty(String idFaculty, FacultyEntity newFacultyData){
        facultyRepository.updateFaculty(idFaculty, newFacultyData);
    }

    /**
     * Видаляє факультет та всі пов’язані кафедри та осіб.
     *
     * @param idFaculty ідентифікатор факультету
     */
    public void deleteFaculty(String idFaculty){
        FacultyEntity faculty = facultyRepository.getFaculty(idFaculty);

        if(faculty != null){
            for(String departmentId : faculty.getDepartmentIds()){
                DepartmentEntity department = departmentRepository.getDepartment(departmentId);

                if(department != null){
                    for (String personId : department.getStudentIds())
                        studentRepository.deleteStudent(personId);

                    for (String teacherId : department.getTeacherIds())
                        teacherRepository.deleteTeacher(teacherId);

                    departmentRepository.deleteDepartment(departmentId);
                }
                else
                    out.println("Кафедру не знайдено!");
            }

            facultyRepository.deleteFaculty(idFaculty);
        }
        else
            out.println("Факультет не знайдено!");
    }

    /**
     * Отримує факультет за ідентифікатором.
     *
     * @param idFaculty ідентифікатор факультету
     * @return об'єкт факультету
     */
    public FacultyEntity getFaculty(String idFaculty){
        return facultyRepository.getFaculty(idFaculty);
    }

    /**
     * Отримує список усіх факультетів.
     *
     * @return масив факультетів
     */
    public FacultyEntity[] getFaculties(){
        return facultyRepository.getFaculties();
    }
    //endregion

    //region Department
    /**
     * Створює нову кафедру та додає її до факультету.
     *
     * @param department нова кафедра
     * @param idFaculty ідентифікатор факультету
     */
    public void createDepartment(DepartmentEntity department, String idFaculty){
        if(facultyRepository.getFaculty(idFaculty) != null){
            departmentRepository.createDepartment(department);
            facultyRepository.addDepartmentToFaculty(idFaculty, department.getId());
        }
        else
            out.println("Факультет не знайдено!");
    }

    /**
     * Оновлює дані кафедри.
     *
     * @param idDepartment ідентифікатор кафедри
     * @param newDepartmentData нові дані кафедри
     */
    public void updateDepartment(String idDepartment, DepartmentEntity newDepartmentData){
        departmentRepository.updateDepartment(idDepartment, newDepartmentData);
    }

    /**
     * Видаляє кафедру та всіх осіб, що до неї належать.
     *
     * @param idDepartment ідентифікатор кафедри
     */
    public void deleteDepartment(String idDepartment){
        DepartmentEntity department = departmentRepository.getDepartment(idDepartment);

        if(department != null){
            for(String studentId : department.getStudentIds()){
                studentRepository.deleteStudent(studentId);
            }

            for(String teacherId : department.getTeacherIds()){
                teacherRepository.deleteTeacher(teacherId);
            }

            departmentRepository.deleteDepartment(idDepartment);
            facultyRepository.removeDepartmentFromFaculty(findFacultyLinkedToDepartment(idDepartment), idDepartment);
        }
        else
            out.println("Кафедру не знайдено!");
    }

    /**
     * Знаходить факультет, до якого належить кафедра.
     *
     * @param idDepartment ідентифікатор кафедри
     * @return ідентифікатор факультету або null, якщо факультет не знайдено
     */
    private String findFacultyLinkedToDepartment(String idDepartment){
        for (FacultyEntity faculty : facultyRepository.getFaculties()) {
            for (String departmentId : faculty.getDepartmentIds()) {
                if (departmentId.equals(idDepartment)) {
                    return faculty.getId();
                }
            }
        }
        return null;
    }

    /**
     * Повертає кафедру за ідентифікатором.
     *
     * @param idDepartment ідентифікатор кафедри
     * @return об'єкт кафедри
     */
    public DepartmentEntity getDepartment(String idDepartment){
        return departmentRepository.getDepartment(idDepartment);
    }

    /**
     * Повертає усі кафедри.
     *
     * @return масив кафедр
     */
    public DepartmentEntity[] getDepartments(){
        return departmentRepository.getDepartments();
    }
    //endregion

    //region Teacher
    /**
     * Створює нового вчителя та додає його до кафедри.
     *
     * @param teacher      новий вчитель
     * @param idDepartment ідентифікатор кафедри
     */
    public void createTeacher(TeacherEntity teacher, String idDepartment){
        if(departmentRepository.getDepartment(idDepartment) != null){
            teacherRepository.createTeacher(teacher);
            departmentRepository.addTeacherToDepartment(idDepartment, teacher.getId());
        }
        else
            out.println("Кафедру не знайдено!");
    }

    /**
     * Оновлює дані особи.
     *
     * @param idTeacher      ідентифікатор вчителя
     * @param newTeacherData нові дані
     */
    public void updateTeacher(String idTeacher, TeacherEntity newTeacherData){
        teacherRepository.updateTeacher(idTeacher, newTeacherData);
    }

    /**
     * Видаляє вчителя.
     *
     * @param idTeacher ідентифікатор вчителя
     */
    public void deleteTeacher(String idTeacher){
        teacherRepository.deleteTeacher(idTeacher);
        departmentRepository.removeTeacherFromDepartment(findDepartmentLinkedToTeacher(idTeacher), idTeacher);
    }

    /**
     * Знаходить ідентифікатор кафедри, до якої належить вчитель.
     *
     * @param idTeacher ідентифікатор вчителя
     * @return ідентифікатор кафедри, або null, якщо кафедру не знайдено
     */
    private String findDepartmentLinkedToTeacher(String idTeacher){
        for (DepartmentEntity department : departmentRepository.getDepartments()) {
            for (String teacherId : department.getTeacherIds()) {
                if (teacherId.equals(idTeacher)) {
                    return department.getId();
                }
            }
        }
        return null;
    }

    /**
     * Повертає вчителя за його ідентифікатором.
     *
     * @param idTeacher ідентифікатор вчителя
     * @return об'єкт вчителя
     */
    public TeacherEntity getTeacher(String idTeacher){
        return teacherRepository.getTeacher(idTeacher);
    }

    /**
     * Повертає усіх вчителів.
     *
     * @return масив вчителів
     */
    public TeacherEntity[] getTeachers(){
        return teacherRepository.getTeachers();
    }
    //endregion

    //region Student
    /**
     * Створює нового студента та додає його до кафедри.
     *
     * @param student      новий студент
     * @param idDepartment ідентифікатор кафедри
     */
    public void createStudent(StudentEntity student, String idDepartment){
        if(departmentRepository.getDepartment(idDepartment) != null){
            studentRepository.createStudent(student);
            departmentRepository.addStudentToDepartment(idDepartment, student.getId());
        }
        else
            out.println("Кафедру не знайдено!");
    }

    /**
     * Оновлює дані студента.
     *
     * @param idStudent       ідентифікатор студента
     * @param newStudentData нові дані
     */
    public void updateStudent(String idStudent, StudentEntity newStudentData){
        studentRepository.updateStudent(idStudent, newStudentData);
    }

    /**
     * Видаляє студента.
     *
     * @param idStudent ідентифікатор студента
     */
    public void deleteStudent(String idStudent){
        studentRepository.deleteStudent(idStudent);
        departmentRepository.removeStudentFromDepartment(findDepartmentLinkedToStudent(idStudent), idStudent);
    }

    /**
     * Знаходить ідентифікатор кафедри, до якої належить студент.
     *
     * @param isStudent ідентифікатор студента
     * @return ідентифікатор кафедри, або null, якщо кафедру не знайдено
     */
    private String findDepartmentLinkedToStudent(String isStudent){
        for (DepartmentEntity department : departmentRepository.getDepartments()) {
            for (String personId : department.getStudentIds()) {
                if (personId.equals(isStudent)) {
                    return department.getId();
                }
            }
        }
        return null;
    }

    /**
     * Повертає студента за його ідентифікатором.
     *
     * @param idStudent ідентифікатор студента
     * @return об'єкт студента
     */
    public StudentEntity getStudent(String idStudent){
        return studentRepository.getStudent(idStudent);
    }

    /**
     * Повертає усіх студентів.
     *
     * @return масив студентів
     */
    public StudentEntity[] getPersons(){
        return studentRepository.getStudents();
    }

    /**
     * Знаходить студента за повним ім'ям.
     *
     * @param name ім'я студента
     * @param surname прізвище студента
     * @param middleName по батькові студента
     * @return знайдений студент або null, якщо такого немає
     */
    public StudentEntity findStudentByFullName(String name, String surname, String middleName){
        StudentEntity[] students = studentRepository.getStudents();
        for (StudentEntity student : students) {
            if (student.getName().equals(name) && student.getSurname().equals(surname) && student.getMiddleName().equals(middleName))
                return student;
        }
        return null;
    }

    /**
     * Знаходить студента за номером групи.
     *
     * @param group номер групи
     * @return знайдений студент або null, якщо такого немає
     */
    public StudentEntity findStudentByGroup(int group){
        StudentEntity[] students = studentRepository.getStudents();
        for (StudentEntity student : students) {
            if (student.getGroup() == group)
                return student;
        }

        return null;
    }

    /**
     * Знаходить студента за номером курсу.
     *
     * @param course номер курсу
     * @return знайдений студент або null, якщо такого немає
     */
    public StudentEntity findStudentByCourse(int course){
        StudentEntity[] students = studentRepository.getStudents();
        for (StudentEntity student : students) {
            if (student.getCourse() == course)
                return student;
        }

        return null;
    }

    /**
     * Знаходить викладача за повним ім'ям.
     *
     * @param name ім'я викладача
     * @param surname прізвище викладача
     * @param middleName по батькові викладача
     * @return знайдений викладач або null, якщо такого немає
     */
    public TeacherEntity findTeacherByFullName(String name, String surname, String middleName){
        TeacherEntity[] teachers = teacherRepository.getTeachers();
        for (TeacherEntity teacher : teachers) {
            if (teacher.getName().equals(name) && teacher.getSurname().equals(surname) && teacher.getMiddleName().equals(middleName))
                return teacher;
        }

        return null;
    }

    public StudentEntity[] sortStudentsByCourse() {
        StudentEntity[] students = studentRepository.getStudents();
        SortUtils.quickSort(students, 0, students.length - 1, SortUtils.SortType.BY_COURSE, true);
        return students;
    }

    public StudentEntity[] sortStudentsByFullName() {
        StudentEntity[] students = studentRepository.getStudents();
        SortUtils.quickSort(students, 0, students.length - 1, SortUtils.SortType.BY_FULL_NAME, true);
        return students;
    }

    public TeacherEntity[] sortTeachersByFullName() {
        TeacherEntity[] teachers = teacherRepository.getTeachers();
        SortUtils.quickSort(teachers, 0, teachers.length - 1, SortUtils.SortType.BY_FULL_NAME, true);
        return teachers;
    }


    //endregion
}
