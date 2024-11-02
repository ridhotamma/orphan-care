package org.orphancare.dashboard.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RandomGeneratorUtil {
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    private static final Random RANDOM = new Random();

    public static String generatePassword(int length) {
        // Ensure minimum length of 8
        length = Math.max(length, 8);

        // Initialize StringBuilder with a guaranteed character from each required type
        StringBuilder password = new StringBuilder();
        password.append(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length()))); // Add lowercase
        password.append(UPPERCASE.charAt(RANDOM.nextInt(UPPERCASE.length()))); // Add uppercase
        password.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length()))); // Add number
        password.append(SYMBOLS.charAt(RANDOM.nextInt(SYMBOLS.length()))); // Add symbol

        // Create a string of all possible characters
        String allChars = LOWERCASE + UPPERCASE + NUMBERS + SYMBOLS;

        // Fill the rest of the password length with random characters
        for (int i = 4; i < length; i++) {
            password.append(allChars.charAt(RANDOM.nextInt(allChars.length())));
        }

        // Shuffle the password to avoid predictable pattern
        List<Character> passwordChars = new ArrayList<>();
        for (char c : password.toString().toCharArray()) {
            passwordChars.add(c);
        }
        java.util.Collections.shuffle(passwordChars);

        // Convert back to string
        StringBuilder shuffledPassword = new StringBuilder();
        for (char c : passwordChars) {
            shuffledPassword.append(c);
        }

        return shuffledPassword.toString();
    }

    public static String generateUsername(int length) {
        StringBuilder sb = new StringBuilder("user_");
        for (int i = 0; i < length; i++) {
            sb.append(LOWERCASE.charAt(RANDOM.nextInt(LOWERCASE.length())));
        }
        return sb.toString();
    }

    public static String generateEmail() {
        return UUID.randomUUID().toString().substring(0, 8) + "@annajah.my.id";
    }

    // Utility method to verify password meets requirements (useful for testing)
    public static boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.matches(".*[a-z].*") &&  // Has lowercase
                password.matches(".*[A-Z].*") &&  // Has uppercase
                password.matches(".*\\d.*") &&    // Has number
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?].*"); // Has symbol
    }
}
