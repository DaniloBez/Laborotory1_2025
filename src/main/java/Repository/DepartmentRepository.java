package Repository;

import Entity.DepartmentEntity;

import java.util.Arrays;

import static Utils.Constants.ID_NOT_FOUND;
import static Utils.Constants.INDEX_NOT_FOUND;
import static java.lang.System.out;


/**
 * Репозиторій для управління сутностями кафедр.
 */
public class DepartmentRepository {
    /**
     * Масив кафедр, що зберігаються у репозиторії.
     */
    private DepartmentEntity[] departments;

    /**
     * Створює порожній репозиторій кафедр.
     */
    public DepartmentRepository() {
        departments = new DepartmentEntity[0];
    }

    //region CRUD
    /**
     * Додає нову кафедру до репозиторію.
     *
     * @param department кафедра, який потрібно додати
     */
    public void createDepartment(DepartmentEntity department) {
        departments = Arrays.copyOf(departments, departments.length + 1);
        departments[departments.length - 1] = department;
    }

    /**
     * Оновлює дані про кафедру за її ID.
     *
     * @param id      ідентифікатор кафедри
     * @param newDepartmentData нові дані для оновлення
     */
    public void updateDepartment(String id, DepartmentEntity newDepartmentData) {
        DepartmentEntity department = getDepartment(id);

        if (department != null)
            department.update(newDepartmentData);
        else
            out.println("Кафедру не знайдено!");
    }

    /**
     * Видаляє кафедру із репозиторію за її ID.
     *
     * @param id ідентифікатор кафедри
     */
    public void deleteDepartment(String id) {
        int index = findIndex(id);
        if (index != INDEX_NOT_FOUND) {
            DepartmentEntity[] newDepartments = new DepartmentEntity[departments.length - 1];

            int j = 0;
            for (int i = 0; i < departments.length; i++) {
                if (i != index) {
                    newDepartments[j++] = departments[i];
                }
            }

            departments = newDepartments;
        }
        else
            out.println("Кафедру не знайдено!");
    }

    /**
     * Повертає кафедру за його ID.
     *
     * @param id ідентифікатор кафедри
     * @return кафедра або null, якщо не знайдено
     */
    public DepartmentEntity getDepartment(String id) {
        for (DepartmentEntity department : departments) {
            if (department.getId().equals(id)) {
                return department;
            }
        }
        return null;
    }

    /**
     * Повертає всі кафедри, що зберігаються в репозиторії.
     *
     * @return масив кафедр
     */
    public DepartmentEntity[] getDepartments() {
        return departments;
    }
    //endregion

    //region Person
    /**
     * Додає кафедру до факультету за її ID.
     *
     * @param idDepartment ідентифікатор факультету
     * @param idPerson  ідентифікатор кафедри
     */
    public void addPersonToDepartment(String idDepartment, String idPerson) {
        DepartmentEntity department = getDepartment(idDepartment);

        if (department != null) {
            String[] ids = department.getPersonIds();
            ids = Arrays.copyOf(ids, ids.length + 1);
            ids[ids.length - 1] = idPerson;
            department.setPersonIds(ids);
        }
        else
            out.println("Факультет не знайдено!");
    }

    /**
     * Видаляє особу з кафедри за її ID.
     *
     * @param idDepartment ідентифікатор кафедри
     * @param idPerson  ідентифікатор особи
     */
    public void removePersonFromDepartment(String idDepartment, String idPerson) {
        DepartmentEntity department = getDepartment(idDepartment);
        if (department != null) {
            String[] ids = department.getPersonIds();

            String id = ID_NOT_FOUND;
            for (String string : ids) {
                if (string.equals(idPerson)) {
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

                department.setPersonIds(newIds);
            }
            else
                out.println("Особу не знайдено!");
        }
        else
            out.println("Кафедру не знайдено!");
    }
    //endregion

    /**
     * Знаходить індекс кафедри за її ID.
     *
     * @param id ідентифікатор кафедри
     * @return індекс у масиві або INDEX_NOT_FOUND, якщо не знайдено
     */
    private int findIndex(String id) {
        for (int i = 0; i < departments.length; i++) {
            if (departments[i].getId().equals(id)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }
}
