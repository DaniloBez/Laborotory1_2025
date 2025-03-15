package Entity.Person;

import Utils.IDGenerator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


/**
 * Представляє сутність студента з основною інформацією та автоматично згенерованим ID.
 */
@Getter
@Setter
public class StudentEntity{

    /**
     * Унікальний ідентифікатор для кожного екземпляра студента.
     */
    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    private final String id = IDGenerator.generateID() + hashCode();

    /**
     * Ім'я студента.
     */
    private String name;

    /**
     * Прізвище студента.
     */
    private String surname;

    /**
     * По батькові студента.
     */
    private String middleName;

    /**
     * Курс навчання студента.
     */
    private int course;

    /**
     * Група, до якої належить студент.
     */
    private int group;

    /**
     * Конструює новий екземпляр StudentEntity із заданими параметрами.
     *
     * @param name       ім'я студента
     * @param surname    прізвище студента
     * @param middleName по батькові студента
     * @param course     курс навчання
     * @param group      група, до якої належить людина
     */
    public StudentEntity(String name, String surname, String middleName, int course, int group) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.course = course;
        this.group = group;
    }

    /**
     * Оновлює дані студента
     *
     * @param studentEntity сутність що представляє нові дані студента
     */
    public void update(StudentEntity studentEntity) {
        this.name = studentEntity.getName();
        this.surname = studentEntity.getSurname();
        this.middleName = studentEntity.getMiddleName();
        this.course = studentEntity.getCourse();
        this.group = studentEntity.getGroup();
    }

    /**
     * Повертає рядкове представлення сутності людини.
     *
     * @return рядок, що містить деталі про студента
     */
    @Override
    public String toString() {
        return surname + " " + name + " " + middleName + ", Курс: " + course + ", Група: " + group + ".";
    }
}
