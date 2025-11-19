package compiler;

import compiler.domain.Token;

public class GenerationUtils {

    public static String getBinaryOperation(Token operator) {
        return switch (operator.name()) {
            case "or" -> "OR";
            case "and" -> "AND";
            case "igual" -> "EQ";
            case "desigual" -> "NE";
            case "menor" -> "LT";
            case "mayor" -> "GT";
            case "menorOIgual" -> "LE";
            case "mayorOIgual" -> "GE";
            case "suma" -> "ADD";
            case "resta" -> "SUB";
            case "por" -> "MUL";
            case "dividido" -> "DIV";
            case "modulo" -> "MOD";
            default -> "";
        };
    }

    public static String getUnaryOperation(Token operator) {
        return switch (operator.name()) {
            case "incremento" -> "ADD";
            case "resta" -> "NEG";
            case "decremento" -> "SUB";
            case "not" -> "NOT";
            default -> "";
        };
    }
}
