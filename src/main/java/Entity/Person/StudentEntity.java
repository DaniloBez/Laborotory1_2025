package Entity.Person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentEntity extends PersonEntity{

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
     * @param name       ім'я людини
     * @param surname    прізвище людини
     * @param middleName по батькові людини
     * @param course     курс навчання
     * @param group      група, до якої належить людина
     */
    public StudentEntity(String name, String surname, String middleName, int course, int group) {
        super(name, surname, middleName);
        this.course = course;
        this.group = group;
    }

    /**
     * Повертає рядкове представлення сутності людини.
     *
     * @return рядок, що містить деталі про студента
     */
    @Override
    public String toString() {
        return super.toString() + ", Course: " + course + ", Group: " + group + ".";
    }
}
