package Entity;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Представляє кафедру факультету із назвою та списком ідентифікаторів осіб, які до нього належать.
 */
@Getter
@Setter
@EqualsAndHashCode
public class DepartmentEntity {
    /**
     * Унікальний ідентифікатор кафедри.
     */
    @Setter(AccessLevel.NONE)
    private final String id = UUID.randomUUID().toString();

    /**
     * Назва кафедри.
     */
    private String name;

    /**
     * Масив ідентифікаторів осіб, що належать до факультету.
     */
    private String[] personIds;

    /**
     * Конструктор, що створює нову кафедру із заданою назвою.
     *
     * @param name назва кафедри
     */
    public DepartmentEntity(String name) {
        this.name = name;
        personIds = new String[0];
    }

    /**
     * Оновлює дані кафедри на основі переданої сутності.
     *
     * @param departmentEntity сутність кафедри з оновленими даними
     */
    public void update(DepartmentEntity departmentEntity) {
        this.name = departmentEntity.getName();
        this.personIds = departmentEntity.getPersonIds();
    }
}
