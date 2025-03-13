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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * Перетворює масив об'єктів у масив {@code TeacherEntity[]}.
     *
     * @param objects масив об'єктів {@code Object[]}
     * @return масив {@code TeacherEntity[]}
     */
    private TeacherEntity[] castObjectArrayToTeacherEntityArray(Object[] objects) {
        TeacherEntity[] teachers = new TeacherEntity[objects.length];
        for (int i = 0; i < objects.length; i++) {
            teachers[i] = (TeacherEntity) objects[i]; // Нисхідне перетворення
        }
        return teachers;
    }

    /**
     * Повертає масив викладачів факультету, відсортований за повним ім'ям (прізвище, ім'я, по батькові).
     *
     * @param idFaculty ідентифікатор факультету
     * @return відсортований масив {@code TeacherEntity[]} викладачів факультету
     */
    public TeacherEntity[] sortTeachersByFullNameInFaculty(String idFaculty) {
        String[] departments = facultyRepository.getFaculty(idFaculty).getDepartmentIds();
        Object[] filteredTeachers = new Object[0];

        for (String idDepartment : departments)
            for(String idTeacher :  departmentRepository.getDepartment(idDepartment).getTeacherIds())
                filteredTeachers = addObjectToArray(filteredTeachers,teacherRepository.getTeacher(idTeacher));

        TeacherEntity[] teachers = castObjectArrayToTeacherEntityArray(filteredTeachers);

        SortUtils.quickSort(teachers, 0, teachers.length - 1, SortUtils.SortType.BY_FULL_NAME, true);

        return teachers;
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
    public String findDepartmentLinkedToStudent(String isStudent){
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
    public StudentEntity[] getStudents(){
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
     * Повертає масив усіх студентів, відсортований за курсом у порядку зростання.
     *
     * @return відсортований масив {@code StudentEntity[]} студентів
     */
    public StudentEntity[] sortStudentsByCourse() {
        StudentEntity[] students = getStudents();
        SortUtils.quickSort(students, 0, students.length - 1, SortUtils.SortType.BY_COURSE, true);
        return students;
    }

    /**
     * Перетворює масив Object[] у масив StudentEntity[] шляхом нисхідного перетворення.
     *
     * @param objects масив об'єктів
     * @return масив студентів
     */
    private StudentEntity[] castObjectArrayToStudentEntityArray(Object[] objects) {
        StudentEntity[] students = new StudentEntity[objects.length];
        for (int i = 0; i < objects.length; i++) {
            students[i] = (StudentEntity) objects[i]; // Нисхідне перетворення
        }
        return students;
    }

    /**
     * Повертає масив студентів, які навчаються на вказаній кафедрі,
     * відсортований за курсом.
     *
     * @param idDepartment ідентифікатор кафедри
     * @return відсортований масив {@code StudentEntity[]} студентів кафедри
     */
    public StudentEntity[] sortStudentsByCourseInDepartment(String idDepartment) {
        Object[] filteredStudents = new Object[0]; // Початковий порожній масив

        for (StudentEntity student : getStudents()) {
            String departmentId = findDepartmentLinkedToStudent(student.getId());
            if (departmentId != null && departmentId.equals(idDepartment)) {
                // Додаємо студента в масив Object
                filteredStudents = addObjectToArray(filteredStudents, student);
            }
        }

        StudentEntity[] students = castObjectArrayToStudentEntityArray(filteredStudents);

        // Сортуємо масив за курсом
        SortUtils.quickSort(filteredStudents, 0, students.length - 1, SortUtils.SortType.BY_COURSE, true);

        return students;
    }

    /**
     * Повертає масив студентів, які навчаються на факультеті з вказаним ідентифікатором,
     * відсортований за повним ім'ям (прізвище, ім'я, по батькові).
     *
     * @param idFaculty ідентифікатор факультету
     * @return відсортований масив {@code StudentEntity[]} студентів факультету
     */
    public StudentEntity[] sortStudentsByFullNameInFaculty(String idFaculty) {
        String[] departments = facultyRepository.getFaculty(idFaculty).getDepartmentIds();
        Object[] filteredStudents = new Object[0];

        for (String idDepartment : departments)
            for(String idStudent :  departmentRepository.getDepartment(idDepartment).getStudentIds())
                filteredStudents = addObjectToArray(filteredStudents,studentRepository.getStudent(idStudent));

        StudentEntity[] students = castObjectArrayToStudentEntityArray(filteredStudents);

        SortUtils.quickSort(students, 0, students.length - 1, SortUtils.SortType.BY_FULL_NAME, true);

        return students;
    }
    //endregion

    /**
     * Додає новий об'єкт до масиву об'єктів, створюючи новий масив розміром на 1 більший.
     *
     * @param original вихідний масив об'єктів
     * @param newElement об'єкт, який потрібно додати
     * @return новий масив об'єктів з доданим елементом
     */
    private Object[] addObjectToArray(Object[] original, Object newElement) {
        Object[] newArray = new Object[original.length + 1];
        System.arraycopy(original, 0, newArray, 0, original.length);
        newArray[newArray.length - 1] = newElement;
        return newArray;
    }
}
