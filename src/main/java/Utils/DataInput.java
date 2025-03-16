package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Утилітний клас для введення даних з консолі.
 * Забезпечує безпечне зчитування різних типів даних (long, int, double, char, boolean, string).
 */
public final class DataInput {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Зчитує ціле число типу long з консолі.
     * @return введене значення типу long
     */
    public static long getLong() {
        while (true) {
            try {
                return Long.parseLong(getString());
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть коректне число типу long.");
            }
        }
    }

    /**
     * Зчитує ціле число типу long у заданому діапазоні.
     * @param min мінімальне допустиме значення
     * @param max максимальне допустиме значення
     * @return введене значення типу long в межах min і max
     */
    public static long getLong(long min, long max) {
        while (true) {
            long value = getLong();
            if (value >= min && value <= max) {
                return value;
            } else {
                System.out.println("Помилка: введіть число від " + min + " до " + max);
            }
        }
    }

    /**
     * Зчитує ціле число типу int з консолі.
     * @return введене значення типу int
     */
    public static int getInt() {
        while (true) {
            try {
                return Integer.parseInt(getString());
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть коректне число типу int.");
            }
        }
    }

    /**
     * Зчитує ціле число типу int у заданому діапазоні.
     * @param min мінімальне допустиме значення
     * @param max максимальне допустиме значення
     * @return введене значення типу int в межах min і max
     */
    public static int getInt(int min, int max) {
        while (true) {
            int value = getInt();
            if (value >= min && value <= max) {
                return value;
            } else {
                System.out.println("Помилка: введіть число від " + min + " до " + max);
            }
        }
    }

    /**
     * Зчитує число типу double з консолі.
     * @return введене значення типу double
     */
    public static double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(getString());
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть коректне число типу double.");
            }
        }
    }

    /**
     * Зчитує число типу double у заданому діапазоні.
     * @param min мінімальне допустиме значення
     * @param max максимальне допустиме значення
     * @return введене значення типу double в межах min і max
     */
    public static double getDouble(double min, double max) {
        while (true) {
            double value = getDouble();
            if (value >= min && value <= max) {
                return value;
            } else {
                System.out.println("Помилка: введіть число від " + min + " до " + max);
            }
        }
    }

    /**
     * Зчитує один символ з консолі.
     * @return перший символ введеного рядка
     */
    public static char getChar() {
        while (true) {
            String s = getString();
            if (!s.isEmpty()) {
                return s.charAt(0);
            }
            System.out.println("Помилка: введіть хоча б один символ.");
        }
    }

    /**
     * Зчитує рядок тексту з консолі.
     * @return введений рядок
     */
    public static String getString() {
        while (true) {
            try {
                return reader.readLine().trim();
            } catch (IOException e) {
                System.out.println("Помилка введення. Спробуйте ще раз.");
            }
        }
    }

    /**
     * Зчитує логічне значення з консолі.
     * Підтримуються такі варіанти введення: true/t/1/так/y/yes та false/f/0/ні/n/no.
     * @return true або false залежно від введеного значення
     */
    public static boolean getBoolean() {
        while (true) {
            String input = getString().toLowerCase();
            if (input.equals("true") || input.equals("t") || input.equals("1") || input.equals("так") || input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("false") || input.equals("f") || input.equals("0") || input.equals("ні") || input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Помилка: введіть 'true' або 'false' (так/ні, t/f, y/n, 1/0).");
            }
        }
    }
    public static String inputName() {
        while (true) {
            try {
                String input = reader.readLine().trim().toLowerCase();
                // Дозволено тільки кирилицю, апостроф, дефіс, нижнє підкреслення та пробіли між словами
                if (input.matches("[а-яіїє'_\\-]+(\\s[а-яіїє'_\\-]+)*")) {
                    // Робимо першу букву великою
                    return Character.toUpperCase(input.charAt(0)) + input.substring(1);
                } else {
                    System.out.println("Це поле має містити тільки кириличні літери, апостроф, дефіс, нижнє підкреслення та пробіли! Спробуйте ще раз.");
                }
            } catch (IOException e) {
                System.out.println("Сталася помилка при введенні.");
            }
        }
    }
}
