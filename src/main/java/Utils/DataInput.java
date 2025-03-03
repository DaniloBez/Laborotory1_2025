package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class DataInput {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static long getLong() {
        while (true) {
            try {
                return Long.parseLong(getString());
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть коректне число типу long.");
            }
        }
    }

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

    public static int getInt() {
        while (true) {
            try {
                return Integer.parseInt(getString());
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть коректне число типу int.");
            }
        }
    }

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

    public static double getDouble() {
        while (true) {
            try {
                return Double.parseDouble(getString());
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть коректне число типу double.");
            }
        }
    }

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

    public static char getChar() {
        while (true) {
            String s = getString();
            if (!s.isEmpty()) {
                return s.charAt(0);
            }
            System.out.println("Помилка: введіть хоча б один символ.");
        }
    }

    public static String getString() {
        while (true) {
            try {
                return reader.readLine().trim();
            } catch (IOException e) {
                System.out.println("Помилка введення. Спробуйте ще раз.");
            }
        }
    }

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
}
