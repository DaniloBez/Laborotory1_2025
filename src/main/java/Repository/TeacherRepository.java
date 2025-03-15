package Repository;

import Entity.Person.TeacherEntity;

import java.util.Arrays;

import static Utils.Constants.INDEX_NOT_FOUND;
import static java.lang.System.out;

/**
 * Репозиторій для управління сутностями TeacherEntity.
 */
public class TeacherRepository {
    /**
     * Масив вчителів, що зберігаються в репозиторії.
     */
    private TeacherEntity[] teachers;

    /**
     * Створює порожній репозиторій вчителів.
     */
    public TeacherRepository() {
        teachers = new TeacherEntity[0];
    }

    //region CRUD
    /**
     * Додає нового вчителя в репозиторій.
     *
     * @param teacher вчитель, якого потрібно додати
     */
    public void createTeacher(TeacherEntity teacher) {
        teachers = Arrays.copyOf(teachers, teachers.length + 1);
        teachers[teachers.length - 1] = teacher;
    }

    /**
     * Оновлює дані про вчителя за його ID.
     *
     * @param id             ідентифікатор вчителя
     * @param newTeacherData нові дані для оновлення
     */
    public void updateTeacher(String id, TeacherEntity newTeacherData) {
        TeacherEntity teacher = getTeacher(id);

        if (teacher != null) {
            teacher.update(newTeacherData);
            out.println("Вчителя успішно оновлено!");
        }
        else
            out.println("Вчителя не знайдено!");
    }

    /**
     * Видаляє вчителя з репозиторію за її ID.
     *
     * @param id ідентифікатор вчителя
     */
    public void deleteTeacher(String id) {
        int index = findIndex(id);

        if (index != INDEX_NOT_FOUND) {
            TeacherEntity[] newTeachers = new TeacherEntity[teachers.length - 1];

            int j = 0;
            for (int i = 0; i < teachers.length; i++) {
                if (i != index) {
                    newTeachers[j++] = teachers[i];
                }
            }

            teachers = newTeachers;
            out.println("Вчителя успішно видалено!");
        }
        else
            out.print("Вчителя не знайдено!");
    }

    /**
     * Повертає вчителя за її ID.
     *
     * @param id ідентифікатор вчителя
     * @return вчитель або null, якщо не знайдено
     */
    public TeacherEntity getTeacher(String id) {
        for (TeacherEntity teacher : teachers) {
            if (teacher.getId().equals(id)) {
                return teacher;
            }
        }
        return null;
    }

    /**
     * Повертає всіх вчителів, що зберігаються в репозиторії.
     *
     * @return масив вчителів
     */
    public TeacherEntity[] getTeachers() {
        return teachers;
    }
    //endregion

    /**
     * Знаходить індекс вчителя за її ID.
     *
     * @param id ідентифікатор вчителя
     * @return індекс у масиві або INDEX_NOT_FOUND, якщо не знайдено
     */
    private int findIndex(String id) {
        for (int i = 0; i < teachers.length; i++) {
            if (teachers[i].getId().equals(id)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }
}
