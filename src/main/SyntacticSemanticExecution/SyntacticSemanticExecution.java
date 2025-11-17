package main.SyntacticSemanticExecution;

import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.syntacticAnalyzer.SyntacticAnalyzer;
import compiler.syntacticAnalyzer.syntacticExceptions.SyntacticException;
import injector.Injector;

import java.io.IOException;

import static main.errorHandlers.ErrorHandlers.*;

public class SyntacticSemanticExecution {

    public static void executeSyntacticSemanticAnalysis(SyntacticAnalyzer sLex) throws IOException {
        Injector.getInjector().flushSymbolTable();
        boolean errorOccurred = false;
        try {
            SymbolTable symbolTable= Injector.getInjector().getSymbolTable();
            sLex.performAnalysis();
            symbolTable.checkSymbolTable();
            symbolTable.consolidate();
            symbolTable.statementChecks();
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
