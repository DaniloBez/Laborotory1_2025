package Entity.Person;

public class TeacherEntity extends PersonEntity {
    /**
     * Конструює новий екземпляр TeacherEntity із заданими параметрами.
     *
     * @param name       ім'я людини
     * @param surname    прізвище людини
     * @param middleName по батькові людини
     */
    public TeacherEntity(String name, String surname, String middleName) {
        super(name, surname, middleName);
    }
}
