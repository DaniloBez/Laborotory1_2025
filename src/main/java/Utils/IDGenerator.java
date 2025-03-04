package Utils;

import java.util.Random;

public final class IDGenerator {
    private static final Random random = new Random();

    public static String generateID() {
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            id.append(generateChar());
        }

        id.append('-');
        id.append(System.nanoTime());
        id.append('-');

        for (int i = 0; i < 4; i++) {
            id.append(generateChar());
        }
        id.append('-');

        return id.toString();
    }

    private static char generateChar(){
        return (char) random.nextInt(Character.MIN_VALUE, Character.MAX_VALUE);
    }
}
