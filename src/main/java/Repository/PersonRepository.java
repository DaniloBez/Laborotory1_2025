package Repository;

import Entity.PersonEntity;

import java.util.Arrays;

import static Utils.Constants.INDEX_NOT_FOUND;
import static java.lang.System.out;

/**
 * Репозиторій для управління сутностями PersonEntity.
 */
public class PersonRepository {
    /**
     * Масив осіб, що зберігаються в репозиторії.
     */
    private PersonEntity[] persons;

    /**
     * Створює порожній репозиторій осіб.
     */
    public PersonRepository() {
        persons = new PersonEntity[0];
    }

    /**
     * Додає нову особу в репозиторій.
     *
     * @param person особа, яку потрібно додати
     */
    public void createPerson(PersonEntity person) {
        persons = Arrays.copyOf(persons, persons.length + 1);
        persons[persons.length - 1] = person;
    }

    /**
     * Оновлює дані про особу за її ID.
     *
     * @param id     ідентифікатор особи
     * @param person нові дані для оновлення
     */
    public void updatePerson(String id, PersonEntity person) {
        PersonEntity person1 = getPerson(id);

        if (person1 != null)
            person1.update(person);
        else
            out.println("Особу не знайдено!");
    }

    /**
     * Видаляє особу з репозиторію за її ID.
     *
     * @param id ідентифікатор особи
     */
    public void deletePerson(String id) {
        int index = findIndex(id);

        if (index != INDEX_NOT_FOUND) {
            PersonEntity[] newPersons = new PersonEntity[persons.length - 1];

            int j = 0;
            for (int i = 0; i < persons.length; i++) {
                if (i != index) {
                    newPersons[j++] = persons[i];
                }
            }

            persons = newPersons;
        }
        else
            out.print("Особу не знайдено!");
    }

    /**
     * Повертає особу за її ID.
     *
     * @param id ідентифікатор особи
     * @return особа або null, якщо не знайдено
     */
    public PersonEntity getPerson(String id) {
        for (PersonEntity person1 : persons) {
            if (person1.getId().equals(id)) {
                return person1;
            }
        }
        return null;
    }

    /**
     * Повертає всі особи, що зберігаються в репозиторії.
     *
     * @return масив осіб
     */
    public PersonEntity[] getPersons() {
        return persons;
    }

    /**
     * Знаходить індекс особи за її ID.
     *
     * @param id ідентифікатор особи
     * @return індекс у масиві або INDEX_NOT_FOUND, якщо не знайдено
     */
    private int findIndex(String id) {
        for (int i = 0; i < persons.length; i++) {
            if (persons[i].getId().equals(id)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }
}
