package br.com.mactechnology.macdonation.common;

import java.util.InputMismatchException;

public class MacTechnologyUtils {

    public static boolean isCpf(String cpf) {
        cpf = removeMaskCpf(cpf);

        if (sameCharacters(cpf)) {
            return false;
        }

        char dig10, dig11;
        int sm, r, num, peso;

        try {
            sm = 0;
            peso = 10;
            for (int i = 0; i < 9; i++) {
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig10 = '0';
            }
            else {
                dig10 = (char)(r + 48);
            }

            sm = 0;
            peso = 11;

            for (int i = 0; i < 10; i++) {
                num = (int)(cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            } else {
                dig11 = (char)(r + 48);
            }

            if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10))) {
                return true;
            } else {
                return false;
            }
        } catch (InputMismatchException erro) {
            return(false);
        }
    }

    public static String removeMaskCpf(String cpf) {
        return cpf.replace(".", "").replace("-", "");
    }

    public static boolean sameCharacters(String text) {
        boolean equals = true;
        char previousChar = text.charAt(0);

        for (int i = 1; i < text.length() - 1; i++) {
            char currentChar = text.charAt(i);

            if (previousChar != currentChar) {
                equals = false;
                break;
            }

            previousChar = text.charAt(i);
            currentChar = text.charAt(i + 1);
        }

        return equals;
    }
}