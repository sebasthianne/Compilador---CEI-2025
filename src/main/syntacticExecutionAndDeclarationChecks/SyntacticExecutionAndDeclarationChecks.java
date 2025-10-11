package main.syntacticExecutionAndDeclarationChecks;

import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.syntacticAnalyzer.SyntacticAnalyzer;
import compiler.syntacticAnalyzer.syntacticExceptions.SyntacticException;
import injector.Injector;

import java.io.IOException;

import static main.errorHandlers.ErrorHandlers.*;

public class SyntacticExecutionAndDeclarationChecks {

    public static void executeSyntacticAnalysis(SyntacticAnalyzer sLex) throws IOException {
        boolean errorOccurred = false;
        try {
            SymbolTable symbolTable= Injector.getInjector().getSymbolTable();
            sLex.performAnalysis();
            symbolTable.checkSymbolTable();
            symbolTable.consolidate();
        } catch (LexicalException e) {
            errorOccurred = true;
            handleLexicalException(e);
        } catch (SyntacticException e) {
            errorOccurred = true;
            handleSyntacticException(e);
        } catch (SemanticException e) {
            errorOccurred =true;
            handleSemanticException(e);
        }
        if (!errorOccurred) {
            System.out.println();
            System.out.println("[SinErrores]");
        }
    }

}
