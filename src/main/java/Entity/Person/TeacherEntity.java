package Entity.Person;

import Utils.IDGenerator;
import lombok.*;

/**
 * Представляє сутність вчителя з основною інформацією та автоматично згенерованим ID.
 */
@Getter
@Setter
@EqualsAndHashCode
public class TeacherEntity {

    /**
     * Унікальний ідентифікатор для кожного екземпляра вчителя.
     */
    @Setter(AccessLevel.NONE)
    private final String id = IDGenerator.generateID() + hashCode();

    /**
     * Ім'я вчителя.
     */
    private String name;

    /**
     * Прізвище вчителя.
     */
    private String surname;

    /**
     * По батькові вчителя.
     */
    private String middleName;

    /**
     * Конструює новий екземпляр teacherEntity із заданими параметрами.
     *
     * @param name       ім'я вчителя
     * @param surname    прізвище вчителя
     * @param middleName по батькові вчителя
     */
    public TeacherEntity(String name, String surname, String middleName) {
        this.name = name;
        this.surname = surname;
        this.middleName = middleName;
    }

    /**
     * Оновлює дані вчителя
     *
     * @param teacherEntity сутність, що представляє нові дані вчителя
     */
    public void update(TeacherEntity teacherEntity) {
        this.name = teacherEntity.getName();
        this.surname = teacherEntity.getSurname();
        this.middleName = teacherEntity.getMiddleName();
    }

    /**
     * Повертає рядкове представлення сутності вчителя.
     *
     * @return рядок, що містить деталі про вчителя
     */
    @Override
    public String toString() {
        return "Ім'я: " + name + ", Прізвище: " + surname + ", По батькові: " + middleName;
    }
}
