package Repository;

import Entity.Person.StudentEntity;

import java.util.Arrays;

import static Utils.Constants.INDEX_NOT_FOUND;
import static java.lang.System.out;

/**
 * Репозиторій для управління сутностями StudentRepository.
 */
public class StudentRepository {
    /**
     * Масив студентів, що зберігаються в репозиторії.
     */
    private StudentEntity[] students;

    /**
     * Створює порожній репозиторій студентів.
     */
    public StudentRepository() {
        students = new StudentEntity[0];
    }

    //region CRUD
    /**
     * Додає нового студента в репозиторій.
     *
     * @param student студент, якого потрібно додати
     */
    public void createStudent(StudentEntity student) {
        students = Arrays.copyOf(students, students.length + 1);
        students[students.length - 1] = student;
    }

    /**
     * Оновлює дані про студента за її ID.
     *
     * @param id ідентифікатор студента
     * @param newStudentData нові дані для оновлення
     */
    public void updateStudent(String id, StudentEntity newStudentData) {
        StudentEntity person = getStudent(id);

        if (person != null)
            person.update(newStudentData);
        else
            out.println("Студента не знайдено!");
    }

    /**
     * Видаляє студента з репозиторію за її ID.
     *
     * @param id ідентифікатор студента
     */
    public void deleteStudent(String id) {
        int index = findIndex(id);

        if (index != INDEX_NOT_FOUND) {
            StudentEntity[] newStudents = new StudentEntity[students.length - 1];

            int j = 0;
            for (int i = 0; i < students.length; i++) {
                if (i != index) {
                    newStudents[j++] = students[i];
                }
            }

            students = newStudents;
        }
        else
            out.print("Студента не знайдено!");
    }

    /**
     * Повертає студента за його ID.
     *
     * @param id ідентифікатор студента
     * @return студент або null, якщо не знайдено
     */
    public StudentEntity getStudent(String id) {
        for (StudentEntity person1 : students) {
            if (person1.getId().equals(id)) {
                return person1;
            }
        }
        return null;
    }

    /**
     * Повертає всіх студентів, що зберігаються в репозиторії.
     *
     * @return масив студентів
     */
    public StudentEntity[] getStudents() {
        return students;
    }
    //endregion

    /**
     * Знаходить індекс студента за його ID.
     *
     * @param id ідентифікатор студента
     * @return індекс у масиві або INDEX_NOT_FOUND, якщо не знайдено
     */
    private int findIndex(String id) {
        for (int i = 0; i < students.length; i++) {
            if (students[i].getId().equals(id)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }
}
