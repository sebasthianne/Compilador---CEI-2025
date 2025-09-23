package main.errorHandlers;

import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import compiler.syntacticAnalyzer.syntacticExceptions.SyntacticException;
import input.sourcemanager.SourceManager;

public class ErrorHandlers {
    public static void handleLexicalException(LexicalException exception) {
        String lexema=exception.getErrorData().lexeme();
        System.out.println(exception.getErrorData());
        System.out.println("Error Detallado: " + exception.getErrorDetail());
        System.out.println("Detalle: " + exception.getErrorData().currentLine());
        System.out.println(" ".repeat(exception.getErrorData().columnNumber() + 8) + "^");
        System.out.println();
        if(lexema.equals(SourceManager.END_OF_FILE+"")) lexema="";
        System.out.println("[Error:" + lexema + "|" + exception.getErrorData().lineNumber() + "]");
    }

    public static void handleSyntacticException(SyntacticException exception){
        String lexema=exception.getTokenReceived().lexeme();
        System.out.println(exception.getBasicErrorMessage());
        System.out.println("Error Detallado: "+exception.getDetailedErrorMessage());
        if(lexema.equals(SourceManager.END_OF_FILE+"")) lexema="";
        System.out.println("[Error:"+lexema+"|"+exception.getTokenReceived().lineNumber()+"]");
    }
}
