package main.ErrorHandlers;

import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import compiler.syntacticAnalyzer.syntacticExceptions.SyntacticException;

public class ErrorHandlers {
    public static void handleLexicalException(LexicalException exception) {
        System.out.println(exception.getErrorData());
        System.out.println("Error Detallado: " + exception.getErrorDetail());
        System.out.println("Detalle: " + exception.getErrorData().currentLine());
        System.out.println(" ".repeat(exception.getErrorData().columnNumber() + 8) + "^");
        System.out.println();
        System.out.println("[Error:" + exception.getErrorData().lexeme() + "|" + exception.getErrorData().lineNumber() + "]");
    }

    public static void handleSyntacticException(SyntacticException exception){
        System.out.println(exception.getBasicErrorMessage());
        System.out.println("Error Detallado: "+exception.getDetailedErrorMessage());
        System.out.println("[Error:"+exception.getTokenReceived().lexeme()+"|"+exception.getTokenReceived().lineNumber()+"]");
    }
}
