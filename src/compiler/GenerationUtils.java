package compiler;

import compiler.domain.Callable;
import compiler.domain.Class;
import compiler.domain.Constructor;
import compiler.domain.Method;
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

    public static String getMethodLabel(Method method){
        return "lblMet"+method.getName().lexeme()+"$"+method.getArity()+"@"+method.getClassDeclaredIn().getName().lexeme();
    }

    public static String getConstructorLabel(Constructor constructor){
        return "lblConstructor$"+constructor.getArity()+"@"+constructor.getName().lexeme();
    }

}
