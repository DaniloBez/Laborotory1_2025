package Service;

import Entity.FacultyEntity;
import Entity.DepartmentEntity;
import Repository.DepartmentRepository;
import Repository.FacultyRepository;
import Repository.StudentRepository;

/**
 * Сервісний клас, який забезпечує управління факультетами, кафедрами та особами.
 */
public class Service {
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final FacultyRepository facultyRepository;

    /**
     * Конструктор сервісу.
     *
     * @param studentRepository репозиторій осіб
     * @param departmentRepository репозиторій кафедр
     * @param facultyRepository репозиторій факультетів
     */
    public Service(StudentRepository studentRepository, DepartmentRepository departmentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
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
                    for (String personId : department.getPersonIds())
                        studentRepository.deletePerson(personId);

                    departmentRepository.deleteDepartment(departmentId);
                }
            }

            facultyRepository.deleteFaculty(idFaculty);
        }
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
        departmentRepository.createDepartment(department);
        facultyRepository.addDepartmentToFaculty(idFaculty, department.getId());
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
            for(String personId : department.getPersonIds()){
                studentRepository.deletePerson(personId);
            }
        }

        departmentRepository.deleteDepartment(idDepartment);
        facultyRepository.removeDepartmentFromFaculty(findFacultyLinkedToDepartment(idDepartment), idDepartment);
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
                    return departmentId;
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

    //region Person

    /**
     * Створює нову особу та додає її до кафедри.
     *
     * @param person нова особа
     * @param idDepartment ідентифікатор кафедри
     */
    public void createPerson(PersonEntity person, String idDepartment){
        studentRepository.createPerson(person);
        departmentRepository.addStudentToDepartment(idDepartment, person.getId());
    }

    /**
     * Оновлює дані особи.
     *
     * @param idPerson ідентифікатор особи
     * @param newPersonData нові дані
     */
    public void updatePerson(String idPerson, PersonEntity newPersonData){
        studentRepository.updatePerson(idPerson, newPersonData);
    }

    /**
     * Видаляє особу.
     *
     * @param idPerson ідентифікатор особи
     */
    public void deletePerson(String idPerson){
        studentRepository.deletePerson(idPerson);
        departmentRepository.removePersonFromDepartment(idPerson, findDepartmentLinkedToPerson(idPerson));
    }

    /**
     * Знаходить ідентифікатор кафедри, до якої належить особа.
     *
     * @param idPerson ідентифікатор особи
     * @return ідентифікатор кафедри, або null, якщо кафедру не знайдено
     */
    private String findDepartmentLinkedToPerson(String idPerson){
        for (DepartmentEntity department : departmentRepository.getDepartments()) {
            for (String personId : department.getPersonIds()) {
                if (personId.equals(idPerson)) {
                    return personId;
                }
            }
        }
        return null;
    }

    /**
     * Повертає особу за її ідентифікатором.
     *
     * @param idPerson ідентифікатор особи
     * @return об'єкт особи
     */
    public PersonEntity getPerson(String idPerson){
        return studentRepository.getPerson(idPerson);
    }

    /**
     * Повертає усіх осіб.
     *
     * @return масив осіб
     */
    public PersonEntity[] getPersons(){
        return studentRepository.getStudents();
    }
    //endregion
}
