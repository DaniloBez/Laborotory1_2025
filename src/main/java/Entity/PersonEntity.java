package Entity;

import lombok.*;
import java.util.UUID;

/**
 * Представляє сутність людини з основною інформацією та автоматично згенерованим ID.
 */
@Getter
@Setter
@EqualsAndHashCode
public class PersonEntity {

    /**
     * Унікальний ідентифікатор для кожного екземпляра людини.
     */
    @Setter(AccessLevel.NONE)
    private final String id = UUID.randomUUID().toString();

    /**
     * Ім'я людини.
     */
    private String name;

    /**
     * Прізвище людини.
     */
    private String surname;

    /**
     * По батькові людини.
     */
    private String middleName;

    /**
     * Курс навчання людини.
     */
    private String course;

    /**
     * Група, до якої належить людина.
     */
    private String group;

    /**
     * Тип людини (наприклад, студент, викладач тощо).
     */
    private PersonType type;

    /**
     * Конструює новий екземпляр PersonEntity із заданими параметрами.
     *
     * @param name       ім'я людини
     * @param surname    прізвище людини
     * @param middleName по батькові людини
     * @param course     курс навчання
     * @param group      група, до якої належить людина
     * @param type       тип людини
     */
    public PersonEntity(String name, String surname, String middleName, String course, String group, PersonType type) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
        this.course = course;
        this.group = group;
        this.type = type;
    }

    /**
     * Оновлює інформацію про поточну людину на основі іншого екземпляра PersonEntity.
     *
     * @param personEntity сутність людини з оновленими значеннями
     */
    public void update(PersonEntity personEntity) {
        this.name = personEntity.getName();
        this.surname = personEntity.getSurname();
        this.middleName = personEntity.getMiddleName();
        this.course = personEntity.getCourse();
        this.group = personEntity.getGroup();
        this.type = personEntity.getType();
    }

    /**
     * Повертає рядкове представлення сутності людини.
     *
     * @return рядок, що містить деталі про людину
     */
    @Override
    public String toString() {
        return "Ім'я: " + name + ", прізвище: " + surname + ", по батькові: " + middleName +
                ", курс: " + course + ", група: " + group + ", тип: " + type + ".";
    }
}
