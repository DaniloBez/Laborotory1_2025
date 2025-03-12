package Utils;

import Entity.Person.StudentEntity;
import Entity.Person.TeacherEntity;

public class SortUtils {

    private static final String UKRAINIAN_ALPHABET = "АаБбВвГгҐґДдЕеЄєЖжЗзИиІіЇїЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЬьЮюЯя";

    public enum SortType {
        BY_COURSE, BY_FULL_NAME
    }

    public static void quickSort(Object[] array, int low, int high, SortType sortType, boolean asc) {
        if (low < high) {
            int pi = partition(array, low, high, sortType, asc);
            quickSort(array, low, pi - 1, sortType, asc);
            quickSort(array, pi + 1, high, sortType, asc);
        }
    }

    private static int partition(Object[] array, int low, int high, SortType sortType, boolean asc) {
        Object pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            int cmp = compareObjects(array[j], pivot, sortType);
            boolean condition = asc ? cmp < 0 : cmp > 0;

            if (condition) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);
        return i + 1;
    }

    private static void swap(Object[] array, int i, int j) {
        Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // ----------- Метод порівняння об'єктів залежно від типу сортування ----------- //
    private static int compareObjects(Object o1, Object o2, SortType sortType) {
        if (o1 instanceof StudentEntity s1 && o2 instanceof StudentEntity s2) {

            if (sortType == SortType.BY_COURSE) {
                int cmp = Integer.compare(s1.getCourse(), s2.getCourse());
                if (cmp != 0) return cmp;

                // Якщо курси однакові, то сортуємо за ПІБ! Щоб результат був очікуваний
                cmp = compareNames(s1.getSurname(), s2.getSurname());
                if (cmp != 0) return cmp;
                cmp = compareNames(s1.getName(), s2.getName());
                if (cmp != 0) return cmp;
                return compareNames(s1.getMiddleName(), s2.getMiddleName());
            } else if (sortType == SortType.BY_FULL_NAME) {
                return compareStudents(s1, s2);
            }

        } else if (o1 instanceof TeacherEntity t1 && o2 instanceof TeacherEntity t2) {

            if (sortType == SortType.BY_FULL_NAME) {
                return compareTeachers(t1, t2); // Метод порівняння імен викладачів (аналогічний StudentEntity)
            }
        }

        throw new IllegalArgumentException("Unsupported object types or sort type.");
    }

    // ----------- Метод порівняння студентів (ПІБ) ----------- //
    private static int compareStudents(StudentEntity s1, StudentEntity s2) {
        int cmp = compareNames(s1.getSurname(), s2.getSurname());
        if (cmp != 0) return cmp;
        cmp = compareNames(s1.getName(), s2.getName());
        if (cmp != 0) return cmp;
        return compareNames(s1.getMiddleName(), s2.getMiddleName());
    }

    // ----------- Метод порівняння викладачів (ПІБ) ----------- //
    private static int compareTeachers(TeacherEntity t1, TeacherEntity t2) {
        int cmp = compareNames(t1.getSurname(), t2.getSurname());
        if (cmp != 0) return cmp;
        cmp = compareNames(t1.getName(), t2.getName());
        if (cmp != 0) return cmp;
        return compareNames(t1.getMiddleName(), t2.getMiddleName());
    }

    private static int compareNames(String name1, String name2) {
        int n1 = name1.length();
        int n2 = name2.length();
        int minLength = Math.min(n1, n2);

        for (int i = 0; i < minLength; i++) {
            char c1 = name1.charAt(i);
            char c2 = name2.charAt(i);

            int idx1 = UKRAINIAN_ALPHABET.indexOf(c1);
            int idx2 = UKRAINIAN_ALPHABET.indexOf(c2);

            // Якщо букви не знайдені — обробити як стандартні символи
            if (idx1 == -1 || idx2 == -1) {
                int cmp = Character.compare(c1, c2);
                if (cmp != 0) return cmp;
            } else if (idx1 != idx2) {
                return Integer.compare(idx1, idx2);
            }
        }

        return Integer.compare(n1, n2);
    }

}
