package com.anec;

public class BinaryTranslate {

    public static String textToBinary(String originalText) {
        StringBuilder binaryText = new StringBuilder();

        for (int i = 0; i < originalText.length(); i++) {
            char currentChar = originalText.charAt(i);

            String binary = decimalToBinary(currentChar);
            binaryText.append(binary).append(" ");
        }

        return binaryText.toString();
    }

    public static String binaryToText(String binaryText) {
        String[] binaryNumbers = binaryText.split(" ");
        StringBuilder text = new StringBuilder();

        for (String currentBinary : binaryNumbers) {
            int decimal = binaryToDecimal(currentBinary);
            char letter = (char) decimal;
            text.append(letter);
        }
        return text.toString();
    }

    private static int binaryToDecimal(String binary) {
        int decimal = 0;
        int position = 0;

        for (int i = binary.length() - 1; i >= 0; i--) {
            short digit = 1;

            if (binary.charAt(i) == '0') {
                digit = 0;
            }

            double multiplier = Math.pow(2, position);
            decimal += digit * multiplier;
            position++;
        }
        return decimal;
    }

    private static String decimalToBinary(int decimal) {
        if (decimal <= 0) {
            return "0";
        }

        StringBuilder binary = new StringBuilder();
        while (decimal > 0) {
            short remainder = (short) (decimal % 2);
            decimal = decimal / 2;
            binary.insert(0, String.valueOf(remainder));
        }
        return binary.toString();
    }
}
