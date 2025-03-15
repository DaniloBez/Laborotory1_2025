/*Написати програму, що буде формувати список студентів та викладачів університету НаУКМА.

Відповідно мають бути реалізовані такі можливості роботи, як:

Створити/видалити/редагувати факультет.
Створити/видалити/редагувати кафедру факультета.
Додати/видалити/редагувати студента/викладача до кафедри.
Знайти студента/викладача за ПІБ, курсом або групою.
Вивести всіх студентів впорядкованих за курсами.
Вивести всіх студентів/викладачів факультета впорядкованих за алфавітом.
Вивести всіх студентів кафедри впорядкованих за курсами.
Вивести всіх студентів/викладачів кафедри впорядкованих за алфавітом.
Вивести всіх студентів кафедри вказаного курсу.
Вивести всіх студентів кафедри вказаного курсу впорядкованих за алфавітом.


Вимоги:

Повинні бути реалізовані усілякі можливі варіанти захисту від невірного введення даних, або заборонених дій.
При написанні програми необхідно обовязково використовувати об'єкти і обмін даними між ними.
Продумати ієрархію класів.
Вся інформація вводиться з клавіатури.
Для роботи користувача повинно пропонуватися меню з набором можливих дій.
Робота може виконуватися в групі, максимум з 2 студентів.
До роботи має бути доданий звіт про виконання лабораторної роботи з описом усіх написаних класів, а також реалізованих можливостей.
При груповій роботі до звіта має бути доданий чіткий розподіл функцій та повноваженнь, що були реалізовані кожним учасником групи окремо.*/

import Entity.Person.StudentEntity;
import Entity.Person.TeacherEntity;
import Service.*;
import Repository.*;
import Entity.*;
import Utils.DataInput;

import static java.lang.System.out;

public class Main {
    private static Service service;
    /**
     * Відповідає за взаємодію з користувачем через меню та виклик відповідних методів обробки.
     */
    public static void main(String[] args) {
        init();
        out.println("Вас вітає програма для роботи з університетом!");

        do {
            out.println("""
                    Виберіть номер взаємодії:
                    1) Створити/видалити/редагувати факультет
                    2) Створити/видалити/редагувати кафедру факультета.
                    3) Додати/видалити/редагувати студента/викладача до кафедри.
                    4) Знайти студента/викладача за ПІБ. Або студента за курсом або групою.
                    5) Вивести всіх студентів впорядкованих за курсами.
                    6) Вивести всіх студентів/викладачів факультета впорядкованих за алфавітом.
                    7) Вивести всіх студентів кафедри впорядкованих за курсами.
                    8) Вивести всіх студентів/викладачів кафедри впорядкованих за алфавітом.
                    9) Вивести всіх студентів кафедри вказаного курсу.
                    10)Вивести всіх студентів кафедри вказаного курсу впорядкованих за алфавітом.
                    """);

            switch (DataInput.getInt(1, 10)){
                case 1 -> facultyCRUD();
                case 2 -> departmentCRUD();
                case 3 -> {
                    out.println("""
                            Виберіть дію:
                            1) Додати/видалити/редагувати студента до кафедри.
                            2) Додати/видалити/редагувати викладача до кафедри
                            """);
                    if (DataInput.getInt(1, 2) == 1)
                        studentCRUD();
                    else
                        teacherCRUD();
                }
                case 10 -> OutputSortStudentsByFullNameForCourseInDepartment();
            }
        } while (DataInput.getBoolean());
    }
    private static void OutputSortStudentsByFullNameForCourseInDepartment() {
        out.print("Введіть кафедру: ");
        String departmentName = DataInput.getString();
        out.print("Введіть курс для студентів: ");
        int course = DataInput.getInt(1, 6);
        printArray(service.sortStudentsByFullNameForCourseInDepartment(service.findDepartmentByName(departmentName).getId(), course));
    }
    /**
     * Обробляє CRUD-операції для факультету.
     */
    private static void facultyCRUD() {
        out.println("""
                Виберіть дію:
                1) Створити факультет
                2) Оновити факультет
                3) Видалити факультет
                """);
        switch (DataInput.getInt(1, 3)) {
            case 1 -> createFaculty();
            case 2 -> updateFaculty();
            case 3 -> deleteFaculty();
        }
    }

    /**
     * Створює новий факультет на основі введених користувачем даних.
     */
    private static void createFaculty() {
        out.println("Введіть назву факультету: ");
        service.createFaculty(new FacultyEntity(DataInput.inputName()));
    }

    /**
     * Оновлює інформацію про факультет.
     */
    private static void updateFaculty() {
        out.println("Виберіть факультет у якого хочете оновити інформацію: ");
        out.println(printArray(service.getFaculties()));
        String facultyID = service.findFacultyByName(DataInput.inputName()).getId();

        out.println("Введіть нову інформацію (назву): ");
        service.updateFaculty(facultyID, new FacultyEntity(DataInput.inputName()));
    }

    /**
     * Видаляє факультет, вибраний користувачем.
     */
    private static void deleteFaculty() {
        out.println("Виберіть факультет який хочете видалити: ");
        out.println(printArray(service.getFaculties()));
        service.deleteFaculty(service.findFacultyByName(DataInput.inputName()).getId());
    }

    /**
     * Обробляє CRUD-операції для кафедри.
     */
    private static void departmentCRUD() {
        out.println("""
                Виберіть дію:
                1) Створити кафедру
                2) Оновити кафедру
                3) Видалити кафедру
                """);
        switch (DataInput.getInt(1, 3)) {
            case 1 -> createDepartment();
            case 2 -> updateDepartment();
            case 3 -> deleteDepartment();
        }
    }

    /**
     * Створює нову кафедру на обраному факультеті.
     */
    private static void createDepartment() {
        out.println("Виберіть факультет у якого хочете створити кафедру: ");
        out.println(printArray(service.getFaculties()));
        String facultyID = service.findFacultyByName(DataInput.inputName()).getId();

        out.println("Введіть назву кафедри: ");
        service.createDepartment(new DepartmentEntity(DataInput.inputName()), facultyID);
    }

    /**
     * Оновлює інформацію про кафедру.
     */
    private static void updateDepartment() {
        out.println("Виберіть кафедру у якій хочете оновити інформацію: ");
        out.println(printArray(service.getDepartments()));
        String departmentID = service.findDepartmentByName(DataInput.inputName()).getId();

        out.println("Введіть нову інформацію (назву): ");
        service.updateDepartment(departmentID, new DepartmentEntity(DataInput.inputName()));
    }

    /**
     * Видаляє кафедру, вибрану користувачем.
     */
    private static void deleteDepartment() {
        out.println("Виберіть кафедру яку хочете видалити: ");
        out.println(printArray(service.getDepartments()));
        service.deleteDepartment(service.findDepartmentByName(DataInput.inputName()).getId());
    }

    /**
     * Обробляє CRUD-операції для студента.
     */
    private static void studentCRUD(){
        out.println("""
                Виберіть дію:
                1) Створити студента
                2) Оновити студента
                3) Видалити студента
                """);
        switch (DataInput.getInt(1, 3)){
            case 1:
                createStudent();
                break;
            case 2:
                updateStudent();
                break;
            case 3:
                deleteStudent();
                break;
        }
    }

    /**
     * Створює нового студента на кафедрі
     */
    private static void createStudent(){
        out.println("Виберіть кафедру у якого хочете створити студента: ");
        out.println(printArray(service.getDepartments()));
        String departmentID = service.findDepartmentByName(DataInput.inputName()).getId();

        out.println("Заповніть дані");
        service.createStudent(getStudentEntityFromConsole(), departmentID);
    }

    /**
     * Оновлює інформацію про студента
     */
    private static void updateStudent(){
        out.println("Виберіть студента у якого хочете оновити інформацію: ");
        out.println(printArray(service.getStudents()));

        TeacherEntity studentFullName = getTeacherEntityFromConsole();

        String studentID = service.findStudentByFullName(studentFullName.getName(), studentFullName.getSurname(), studentFullName.getMiddleName()).getId();

        out.println("Введіть нову інформацію: ");
        service.updateStudent(studentID, getStudentEntityFromConsole());
    }

    /**
     * Видаляє студента, вибраного користувачем.
     */
    private static void deleteStudent(){
        out.println("Виберіть студента якого хочете видалити: ");
        out.println(printArray(service.getStudents()));

        TeacherEntity studentFullName = getTeacherEntityFromConsole();

        service.deleteStudent(service.findStudentByFullName(studentFullName.getName(),
                studentFullName.getSurname(), studentFullName.getMiddleName()).getId());
    }

    /**
     * Обробляє CRUD-операції для вчителя.
     */
    private static void teacherCRUD(){
        out.println("""
                Виберіть дію:
                1) Створити вчителя
                2) Оновити вчителя
                3) Видалити вчителя
                """);
        switch (DataInput.getInt(1, 3)){
            case 1 -> createTeacher();
            case 2 -> updateTeacher();
            case 3 -> deleteTeacher();
        }
    }

    /**
     * Створює нового вчителя на кафедрі
     */
    private static void createTeacher(){
        out.println("Виберіть кафедру у якого хочете створити вчителя: ");
        out.println(printArray(service.getDepartments()));
        String departmentID = service.findDepartmentByName(DataInput.inputName()).getId();

        out.println("Заповніть дані");
        service.createTeacher(getTeacherEntityFromConsole(), departmentID);
    }

    /**
     * Оновлює інформацію про вчителя
     */
    private static void updateTeacher(){
        out.println("Виберіть вчителя у якого хочете оновити інформацію: ");
        out.println(printArray(service.getTeachers()));

        TeacherEntity teacherFullName = getTeacherEntityFromConsole();

        String teacherID = service.findTeacherByFullName(teacherFullName.getName(), teacherFullName.getSurname(), teacherFullName.getMiddleName()).getId();

        out.println("Введіть нову інформацію: ");
        service.updateTeacher(teacherID, getTeacherEntityFromConsole());
    }

    /**
     * Видаляє вчителя, вибраного користувачем.
     */
    private static void deleteTeacher(){
        out.println("Виберіть вчителя якого хочете видалити: ");
        out.println(printArray(service.getTeachers()));

        TeacherEntity teacherFullName = getTeacherEntityFromConsole();

        service.deleteTeacher(service.findStudentByFullName(teacherFullName.getName(),
                teacherFullName.getSurname(), teacherFullName.getMiddleName()).getId());
    }

    /**
     *  Повертає студента, створеного користувачем.
     */
    private static StudentEntity getStudentEntityFromConsole(){
        out.println("Введіть ім'я: ");
        String name = DataInput.inputName();

        out.println("Введіть прізвище: ");
        String surname = DataInput.inputName();

        out.println("Введіть по батькові: ");
        String middleName = DataInput.inputName();

        out.println("Введіть курс: ");
        int course = DataInput.getInt(1, 6);

        out.println("Введіть групу: ");
        int group = DataInput.getInt(1, 15);

        return new StudentEntity(name, surname, middleName, course, group);
    }

    /**
     *  Повертає вчителя, створеного користувачем.
     */
    private static TeacherEntity getTeacherEntityFromConsole(){
        out.println("Введіть ім'я: ");
        String name = DataInput.inputName();

        out.println("Введіть прізвище: ");
        String surname = DataInput.inputName();

        out.println("Введіть по батькові: ");
        String middleName = DataInput.inputName();

        return new TeacherEntity(name, surname, middleName);
    }

    /**
     * Ініціалізує сервіс
     */
    private static void init(){
        StudentRepository studentRepo = new StudentRepository();
        TeacherRepository teacherRepo = new TeacherRepository();
        DepartmentRepository departmentRepo = new DepartmentRepository();
        FacultyRepository facultyRepo = new FacultyRepository();

        service = new Service(studentRepo, teacherRepo, departmentRepo, facultyRepo);

        fillDB();
    }

    /**
     * Заповнює базу даних даними
     */
    private static void fillDB(){
        //region Faculty
        FacultyEntity FGN = new FacultyEntity("ФГН");
        String FGNId = FGN.getId();
        service.createFaculty(FGN);

        FacultyEntity FI = new FacultyEntity("ФІ");
        String FIId = FI.getId();
        service.createFaculty(FI);

        FacultyEntity PRO = new FacultyEntity("КМА_ПРО");
        String PROId = PRO.getId();
        service.createFaculty(PRO);
        //endregion

        //region Department
        DepartmentEntity interdisciplinaryEducation = new DepartmentEntity("Кафедра міждисциплінарної освіти");
        String interdisciplinaryEducationId = interdisciplinaryEducation.getId();
        service.createDepartment(interdisciplinaryEducation, PROId);

        DepartmentEntity Mathematics = new DepartmentEntity("Кафедра математики");
        String MathematicsId = Mathematics.getId();
        service.createDepartment(Mathematics, FIId);

        DepartmentEntity Informatics = new DepartmentEntity("Кафедра інформатики");
        String InformaticsId = Informatics.getId();
        service.createDepartment(Informatics, FIId);

        DepartmentEntity MultimediaSystems = new DepartmentEntity("Кафедра мультимедійних систем");
        String MultimediaSystemsId = MultimediaSystems.getId();
        service.createDepartment(MultimediaSystems, FIId);

        DepartmentEntity networkTechnologies = new DepartmentEntity("Кафедра мережних технологій");
        String networkTechnologiesId = networkTechnologies.getId();
        service.createDepartment(networkTechnologies, FIId);

        DepartmentEntity culturalStudies = new DepartmentEntity("Кафедра культурології");
        String culturalStudiesId = culturalStudies.getId();
        service.createDepartment(culturalStudies, FGNId);

        DepartmentEntity history = new DepartmentEntity("Кафедра історії");
        String historyId = history.getId();
        service.createDepartment(history, FGNId);

        DepartmentEntity archaeology = new DepartmentEntity("Кафедра археології");
        String archaeologyId = archaeology.getId();
        service.createDepartment(archaeology, FGNId);

        DepartmentEntity philosophyAndReligiousStudies = new DepartmentEntity("Кафедра філософії та релігієзнавства");
        String philosophyAndReligiousStudiesId = philosophyAndReligiousStudies.getId();
        service.createDepartment(philosophyAndReligiousStudies, FGNId);

        DepartmentEntity GeneralAndSlavicLinguistics = new DepartmentEntity("Кафедра загального та слов’янського мовознавства");
        String GeneralAndSlavicLinguisticsId = GeneralAndSlavicLinguistics.getId();
        service.createDepartment(GeneralAndSlavicLinguistics, FGNId);
        //endregion

        //region Teacher
        service.createTeacher(new TeacherEntity("Андрій", "Петренко", "Олександрович"), MathematicsId);
        service.createTeacher(new TeacherEntity("Олена", "Сидоренко", "Іванівна"), InformaticsId);
        service.createTeacher(new TeacherEntity("Михайло", "Коваленко", "Володимирович"), MultimediaSystemsId);
        service.createTeacher(new TeacherEntity("Наталія", "Ткаченко", "Миколаївна"), networkTechnologiesId);
        service.createTeacher(new TeacherEntity("Іван", "Бондаренко", "Сергійович"), culturalStudiesId);
        service.createTeacher(new TeacherEntity("Світлана", "Гончар", "Андріївна"), historyId);
        service.createTeacher(new TeacherEntity("Юрій", "Мельник", "Петрович"), archaeologyId);
        service.createTeacher(new TeacherEntity("Тетяна", "Кравченко", "Олексіївна"), philosophyAndReligiousStudiesId);
        service.createTeacher(new TeacherEntity("Олексій", "Лисенко", "Романович"), GeneralAndSlavicLinguisticsId);
        service.createTeacher(new TeacherEntity("Марина", "Поліщук", "Євгенівна"), interdisciplinaryEducationId);
        service.createTeacher(new TeacherEntity("Дмитро", "Романенко", "Васильович"), MathematicsId);
        service.createTeacher(new TeacherEntity("Катерина", "Василенко", "Богданівна"), InformaticsId);
        service.createTeacher(new TeacherEntity("Володимир", "Шевченко", "Анатолійович"), MultimediaSystemsId);
        service.createTeacher(new TeacherEntity("Галина", "Захаренко", "Ігорівна"), networkTechnologiesId);
        service.createTeacher(new TeacherEntity("Сергій", "Омельченко", "Максимович"), culturalStudiesId);
        service.createTeacher(new TeacherEntity("Василь", "Тимченко", "Олегович"), historyId);
        service.createTeacher(new TeacherEntity("Людмила", "Руденко", "Віталіївна"), archaeologyId);
        service.createTeacher(new TeacherEntity("Артем", "Савченко", "Дмитрович"), philosophyAndReligiousStudiesId);
        service.createTeacher(new TeacherEntity("Ірина", "Федоренко", "Русланівна"), GeneralAndSlavicLinguisticsId);
        service.createTeacher(new TeacherEntity("Богдан", "Мартиненко", "Олександрович"), interdisciplinaryEducationId);
        //endregion

        //region Student
        service.createStudent(new StudentEntity("Олег", "Кириленко", "Сергійович", 1, 3), MathematicsId);
        service.createStudent(new StudentEntity("Дарина", "Козак", "Василівна", 2, 5), InformaticsId);
        service.createStudent(new StudentEntity("Максим", "Ігнатенко", "Ігорович", 3, 7), MultimediaSystemsId);
        service.createStudent(new StudentEntity("Анастасія", "Дорошенко", "Андріївна", 4, 2), networkTechnologiesId);
        service.createStudent(new StudentEntity("Євген", "Левченко", "Романович", 5, 6), culturalStudiesId);
        service.createStudent(new StudentEntity("Олександра", "Масленко", "Петрівна", 6, 8), historyId);
        service.createStudent(new StudentEntity("Петро", "Самойленко", "Богданович", 1, 9), archaeologyId);
        service.createStudent(new StudentEntity("Юлія", "Хоменко", "Максимівна", 2, 4), philosophyAndReligiousStudiesId);
        service.createStudent(new StudentEntity("Віталій", "Щербак", "Дмитрович", 3, 1), GeneralAndSlavicLinguisticsId);
        service.createStudent(new StudentEntity("Анна", "Рибалко", "Олегівна", 4, 10), interdisciplinaryEducationId);
        service.createStudent(new StudentEntity("Степан", "Зінченко", "Станіславович", 5, 3), MathematicsId);
        service.createStudent(new StudentEntity("Марія", "Палієнко", "Володимирівна", 6, 5), InformaticsId);
        service.createStudent(new StudentEntity("Роман", "Гаврилюк", "Анатолійович", 1, 7), MultimediaSystemsId);
        service.createStudent(new StudentEntity("Вероніка", "Журавель", "Миколаївна", 2, 2), networkTechnologiesId);
        service.createStudent(new StudentEntity("Ігор", "Нестеренко", "Юрійович", 3, 6), culturalStudiesId);
        service.createStudent(new StudentEntity("Лідія", "Семенюк", "Олександрівна", 4, 8), historyId);
        service.createStudent(new StudentEntity("Арсен", "Тараненко", "Романович", 5, 9), archaeologyId);
        service.createStudent(new StudentEntity("Софія", "Яценко", "Євгенівна", 6, 4), philosophyAndReligiousStudiesId);
        service.createStudent(new StudentEntity("Василь", "Ковальчук", "Борисович", 1, 1), GeneralAndSlavicLinguisticsId);
        service.createStudent(new StudentEntity("Еліна", "Михайленко", "Григоріївна", 2, 10), interdisciplinaryEducationId);
        service.createStudent(new StudentEntity("Денис", "Литвиненко", "Артемович", 3, 3), MathematicsId);
        service.createStudent(new StudentEntity("Оксана", "Савицька", "Валентинівна", 4, 5), InformaticsId);
        service.createStudent(new StudentEntity("Тимофій", "Білоус", "Миколайович", 5, 7), MultimediaSystemsId);
        service.createStudent(new StudentEntity("Алла", "Вороненко", "Іванівна", 6, 2), networkTechnologiesId);
        service.createStudent(new StudentEntity("Ростислав", "Гриценко", "Данилович", 1, 6), culturalStudiesId);
        service.createStudent(new StudentEntity("Кіра", "Дудник", "Олегівна", 2, 8), historyId);
        service.createStudent(new StudentEntity("Борис", "Москаленко", "Володимирович", 3, 9), archaeologyId);
        service.createStudent(new StudentEntity("Юліан", "Фролов", "Артемович", 4, 4), philosophyAndReligiousStudiesId);
        service.createStudent(new StudentEntity("Марта", "Підопригора", "Юріївна", 5, 1), GeneralAndSlavicLinguisticsId);
        //endregion

    }

    /**
     * Перетворює масив для зручного виводу у консоль
     */
    private static String printArray(Object[] array) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            result.append((i + 1)).append(") ").append(array[i]).append("\n");
        }

        return result.toString();
    }
}
