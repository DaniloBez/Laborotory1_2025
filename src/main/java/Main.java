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
import Utils.*;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {

    }

    private static String printArray(Object[] array) {
        String result = "";

        for (int i = 0; i < array.length; i++) {
            result += (i + 1) + ") " + array[i] + "\n";
        }

        return result;
    }
}
///**
// * Додає нового студента до кафедри за вказаним ідентифікатором кафедри.
// *
// * @param idDepartment ідентифікатор кафедри, до якої буде додано студента
// */
//public void createStudent(String idDepartment){
//    createPerson(setSudentData(idDepartment), idDepartment);
//}
///**
// * Отримує та перевіряє інформацію про особу (ім'я, прізвище або по батькові).
// *
// * @param personType тип особи (наприклад, "студента" чи "викладача")
// * @param idDepartment ідентифікатор кафедри, до якої додається особа
// * @return виправлене ім'я з великої літери, що містить лише літери
// */
//private String inputPersonInfo(String personType, String idDepartment){
//    System.out.print("Введіть ім'я "+personType+", щоб додати його до кафедри "+getDepartment(idDepartment).getName()+": ");
//    String info = DataInput.getString();
//    while(info.isEmpty() || !info.matches("[a-zA-ZА-Яа-яЁёІіЇїЄє']+")){
//        System.out.print("Ім'я студента може містити лише літери, спробуйте знову: ");
//        info = DataInput.getString();
//    }
//    return Character.toUpperCase(info.charAt(0)) + info.substring(1).toLowerCase();
//}
//private StudentEntity setSudentData(String idDepartment) {
//    String name = inputPersonInfo("студента", idDepartment);
//    String surname = inputPersonInfo("студента", idDepartment);
//    String middlename = inputPersonInfo("студента", idDepartment);
//    System.out.println("Введіть курс навчання: ");
//    int course = DataInput.getInt(1, 6);
//    System.out.println("Введіть групу студента: ");
//    int group = DataInput.getInt(0, 7);
//    return new StudentEntity(name, surname, middlename, course, group);
//}
//public void deleteStudent(String idStudent){
//    System.out.println("Ви успішно видалили студента!");
//    deletePerson(idStudent);
//}
//public void updateStudent(String idStudent, StudentEntity newStudentData){
//    personRepository.updatePerson(idStudent, newStudentData);
//}
