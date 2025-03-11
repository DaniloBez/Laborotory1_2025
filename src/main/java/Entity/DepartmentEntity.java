package Entity;

import Utils.IDGenerator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


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
    private final String id = IDGenerator.generateID() + hashCode();

    /**
     * Назва кафедри.
     */
    private String name;

    /**
     * Масив ідентифікаторів студентів, що належать до факультету.
     */
    private String[] studentIds;

    /**
     * Масив ідентифікаторів вчителів, що належать до факультету.
     */
    private String[] teacherIds;

    /**
     * Конструктор, що створює нову кафедру із заданою назвою.
     *
     * @param name назва кафедри
     */
    public DepartmentEntity(String name) {
        this.name = name;
        studentIds = new String[0];
        teacherIds = new String[0];
    }

    /**
     * Оновлює дані кафедри на основі переданої сутності.
     *
     * @param departmentEntity сутність кафедри з оновленими даними
     */
    public void update(DepartmentEntity departmentEntity) {
        this.name = departmentEntity.getName();
        this.studentIds = departmentEntity.getStudentIds();
        this.teacherIds = departmentEntity.getTeacherIds();
    }
}
