package Repository;

import Entity.FacultyEntity;

import java.util.Arrays;

import static Utils.Constants.ID_NOT_FOUND;
import static Utils.Constants.INDEX_NOT_FOUND;
import static java.lang.System.out;

/**
 * Репозиторій для управління сутностями факультетів.
 */
public class FacultyRepository {
    /**
     * Масив факультетів, що зберігаються в репозиторії.
     */
    private FacultyEntity[] faculties;

    /**
     * Створює порожній репозиторій факультетів.
     */
    public FacultyRepository() {
        faculties = new FacultyEntity[0];
    }

    //region CRUD
    /**
     * Додає новий факультет до репозиторію.
     *
     * @param faculty факультет, який потрібно додати
     */
    public void createFaculty(FacultyEntity faculty) {
        faculties = Arrays.copyOf(faculties, faculties.length + 1);
        faculties[faculties.length - 1] = faculty;
    }

    /**
     * Оновлює дані про факультет за його ID.
     *
     * @param id      ідентифікатор факультету
     * @param faculty нові дані для оновлення
     */
    public void updateFaculty(String id, FacultyEntity faculty) {
        FacultyEntity faculty1 = getFaculty(id);

        if (faculty1 != null)
            faculty1.update(faculty);
        else
            out.println("Факультет не знайдено!");
    }

    /**
     * Видаляє факультет із репозиторію за його ID.
     *
     * @param id ідентифікатор факультету
     */
    public void deleteFaculty(String id) {
        int index = findIndex(id);
        if (index != INDEX_NOT_FOUND) {
            FacultyEntity[] newFaculties = new FacultyEntity[faculties.length - 1];

            int j = 0;
            for (int i = 0; i < faculties.length; i++) {
                if (i != index) {
                    newFaculties[j++] = faculties[i];
                }
            }

            faculties = newFaculties;
        }
        else
            out.println("Факультет не знайдено!");
    }

    /**
     * Повертає факультет за його ID.
     *
     * @param id ідентифікатор факультету
     * @return факультет або null, якщо не знайдено
     */
    public FacultyEntity getFaculty(String id) {
        for (FacultyEntity faculty1 : faculties) {
            if (faculty1.getId().equals(id)) {
                return faculty1;
            }
        }
        return null;
    }

    /**
     * Повертає всі факультети, що зберігаються в репозиторії.
     *
     * @return масив факультетів
     */
    public FacultyEntity[] getFaculties() {
        return faculties;
    }
    //endregion

    //region Department
    /**
     * Додає кафедру до факультету за її ID.
     *
     * @param idFaculty ідентифікатор факультету
     * @param idDepartment  ідентифікатор кафедри
     */
    public void addDepartmentToFaculty(String idFaculty, String idDepartment) {
        FacultyEntity faculty = getFaculty(idFaculty);

        if (faculty != null) {
            String[] ids = Arrays.copyOf(faculty.getDepartmentIds(), faculty.getDepartmentIds().length + 1);
            ids[ids.length - 1] = idDepartment;
            faculty.setDepartmentIds(ids);
        }
        else
            out.println("Факультет не знайдено!");
    }

    /**
     * Видаляє кафедру з факультету за її ID.
     *
     * @param idFaculty ідентифікатор факультету
     * @param idDepartment  ідентифікатор кафедри
     */
    public void removeDepartmentFromFaculty(String idFaculty, String idDepartment) {
        FacultyEntity faculty = getFaculty(idFaculty);
        if (faculty != null) {
            String[] ids = faculty.getDepartmentIds();

            String id = ID_NOT_FOUND;
            for (String string : ids) {
                if (string.equals(idDepartment)) {
                    id = string;
                    break;
                }
            }

            if (!id.equals(ID_NOT_FOUND)) {
                String[] newIds = new String[ids.length - 1];

                int j = 0;
                for (String s : ids) {
                    if (!s.equals(id)) {
                        newIds[j++] = s;
                    }
                }

                faculty.setDepartmentIds(newIds);
            }
            else
                out.println("Кафедру не знайдено!");
        }
        else
            out.println("Факультет не знайдено!");
    }
    //endregion

    /**
     * Знаходить індекс факультету за його ID.
     *
     * @param id ідентифікатор факультету
     * @return індекс у масиві або INDEX_NOT_FOUND, якщо не знайдено
     */
    private int findIndex(String id) {
        for (int i = 0; i < faculties.length; i++) {
            if (faculties[i].getId().equals(id)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }
}
