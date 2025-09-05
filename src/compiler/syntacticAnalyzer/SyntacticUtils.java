package compiler.syntacticAnalyzer;

import compiler.domain.Token;

import java.util.Arrays;

public class SyntacticUtils {
    private static final String[] primLiterals = {"charLiteral","intLiteral","palabraReservadanull","palabraReservadatrue","palabraReservadafalse"};
    private static final String[] unaryOperators = {"suma","incremento","resta","decremento","not"};
    private static final String[] referencePrimaryFirsts = {"palabraReservadathis","stringLiteral","idMetVar","palabraReservadanew","idClase","abreParéntesis"};
    private static final String[] binaryOperators = {"or","and","igual","desigual","menor","mayor","menorOIgual","mayorOIgual","suma","resta","por", "dividido" ,"modulo"};
    private static final String[] nonExpressionStatementFirsts = {"puntoYComa","palabraReservadavar","palabraReservadareturn","palabraReservadaif","palabraReservadawhile","abreLlave"};
    private static final String[] types = {"idClase","palabraReservadachar","palabraReservadaboolean","palabraReservadaint"};
    private static final String[] modifiers = {"palabraReservadastatic","palabraReservadaabstract","palabraReservadafinal"};
    private static final String[] assignmentFirsts= {"asignación","suma","resta"};

    public static boolean isPrimitiveLiteral(Token token){
        return Arrays.asList(primLiterals).contains(token.name());
    }

    public static boolean isUnaryOperator(Token token){
        return Arrays.asList(unaryOperators).contains(token.name());
    }

    public static boolean isReferencePrimaryFirst(Token token){
        return Arrays.asList(referencePrimaryFirsts).contains(token.name());
    }

    public static boolean isOperandFirst(Token token){
        return isPrimitiveLiteral(token)||isReferencePrimaryFirst(token);
    }

    public static boolean isBinaryOperator(Token token){
        return Arrays.asList(binaryOperators).contains(token.name());
    }

    public static boolean isNonExpressionStatementFirst(Token token){
        return Arrays.asList(nonExpressionStatementFirsts).contains(token.name());
    }

    public static boolean isStatementFirst(Token token){
        return isNonExpressionStatementFirst(token)||isOperandFirst(token);
    }

    public static boolean isType(Token token){
        return Arrays.asList(types).contains(token.name());
    }

    public static boolean isModifier(Token token){
        return Arrays.asList(modifiers).contains(token.name());
    }

    public static boolean isAssignmentFirst(Token token){
        return Arrays.asList(assignmentFirsts).contains(token.name());
    }

    public static boolean isMemberFirst(Token token){
        return token.name().equals("palabraReservadapublic") || isMethodOrAttributeFirst(token);
    }

    public static boolean isMethodOrAttributeFirst(Token token) {
        return token.name().equals("palabraReservadavoid") || isModifier(token) || isType(token);
    }
}
