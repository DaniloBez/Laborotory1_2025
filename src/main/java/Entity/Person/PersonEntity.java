package Entity.Person;

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
     * Конструює новий екземпляр PersonEntity із заданими параметрами.
     *
     * @param name       ім'я людини
     * @param surname    прізвище людини
     * @param middleName по батькові людини
     */
    public PersonEntity(String name, String surname, String middleName) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
    }

    /**
     * Повертає рядкове представлення сутності людини.
     *
     * @return рядок, що містить деталі про людину
     */
    @Override
    public String toString() {
        return "Ім'я: " + name + ", прізвище: " + surname + ", по батькові: " + middleName;
    }
}
