package main.syntacticSemanticExecutionPlusPossibleCodeGeneration;

import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.syntacticAnalyzer.SyntacticAnalyzer;
import compiler.syntacticAnalyzer.syntacticExceptions.SyntacticException;
import injector.Injector;
import inout.sourcemanager.SourceManager;

import java.io.IOException;

import static main.errorHandlers.ErrorHandlers.*;

public class SyntacticSemanticExecutionPlusPossibleCodeGeneration {

    public static void executeSyntacticSemanticAnalysisPlusPossibleCodeGeneration(SyntacticAnalyzer sLex) throws IOException {
        Injector.getInjector().flushSymbolTable();
        boolean errorOccurred = false;
        try {
            SymbolTable symbolTable= Injector.getInjector().getSymbolTable();
            sLex.performAnalysis();
            symbolTable.checkSymbolTable();
            symbolTable.consolidate();
            symbolTable.statementChecks();
            SourceManager source = Injector.getInjector().getSource();
            if(source.getOutputFilePath()!=null){
                symbolTable.generate();
                source.createOutputFile();
            }
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
