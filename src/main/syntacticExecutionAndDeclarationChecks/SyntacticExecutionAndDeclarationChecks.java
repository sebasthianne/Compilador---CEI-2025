package main.syntacticExecutionAndDeclarationChecks;

import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.syntacticAnalyzer.SyntacticAnalyzer;
import compiler.syntacticAnalyzer.syntacticExceptions.SyntacticException;

import java.io.IOException;

import static main.errorHandlers.ErrorHandlers.handleLexicalException;
import static main.errorHandlers.ErrorHandlers.handleSyntacticException;

public class SyntacticExecutionAndDeclarationChecks {

    public static void executeSyntacticAnalysis(SyntacticAnalyzer sLex) throws IOException {
        boolean errorOccurred = false;
        try {
            sLex.performAnalysis();
        } catch (LexicalException e) {
            errorOccurred = true;
            handleLexicalException(e);
        } catch (SyntacticException e) {
            errorOccurred = true;
            handleSyntacticException(e);
        } catch (SemanticException e) {
            errorOccurred =true;
            //TODO: handle exceptionAndReformatForSemantic
        }
        //TODO: Consolidation
        if (!errorOccurred) {
            System.out.println();
            System.out.println("[SinErrores]");
        }
    }

}
