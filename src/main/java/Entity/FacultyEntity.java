package Entity;

import Utils.IDGenerator;
import lombok.*;

import java.util.UUID;

/**
 * Представляє факультет із назвою та списком ідентифікаторів осіб, які до нього належать.
 */
@Getter
@Setter
@EqualsAndHashCode
public class FacultyEntity {
    /**
     * Унікальний ідентифікатор факультету.
     */
    @Setter(AccessLevel.NONE)
    private final String id = IDGenerator.generateID() + hashCode();

    /**
     * Назва факультету.
     */
    private String name;

    /**
     * Масив ідентифікаторів кафедр, що належать до факультету.
     */
    private String[] departmentIds;

    /**
     * Конструктор, що створює новий факультет із заданою назвою.
     *
     * @param name назва факультету
     */
    public FacultyEntity(String name) {
        this.name = name;
        this.departmentIds = new String[0];
    }

    /**
     * Оновлює дані факультету на основі переданої сутності.
     *
     * @param facultyEntity сутність факультету з оновленими даними
     */
    public void update(FacultyEntity facultyEntity) {
        this.name = facultyEntity.getName();
        this.departmentIds = facultyEntity.getDepartmentIds();
    }
}
