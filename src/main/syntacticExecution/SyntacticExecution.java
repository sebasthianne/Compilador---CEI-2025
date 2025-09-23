package main.syntacticExecution;

import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import compiler.syntacticAnalyzer.SyntacticAnalyzer;
import compiler.syntacticAnalyzer.syntacticExceptions.SyntacticException;

import java.io.IOException;

import static main.errorHandlers.ErrorHandlers.handleLexicalException;
import static main.errorHandlers.ErrorHandlers.handleSyntacticException;

public class SyntacticExecution {

    public static void executeSyntacticAnalysis(SyntacticAnalyzer sLex) throws IOException {
        boolean errorOcurred = false;
            try {
                sLex.performAnalysis();
            } catch (LexicalException e) {
                errorOcurred = true;
                handleLexicalException(e);
            } catch (SyntacticException e){
                errorOcurred = true;
                handleSyntacticException(e);
            }
        if (!errorOcurred) {
            System.out.println();
            System.out.println("[SinErrores]");
        }
    }

}
