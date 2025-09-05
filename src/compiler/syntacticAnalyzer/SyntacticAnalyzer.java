package compiler.syntacticAnalyzer;

import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import compiler.syntacticAnalyzer.syntacticExceptions.SyntacticException;

import java.io.IOException;

public interface SyntacticAnalyzer {
    void performAnalysis() throws SyntacticException, LexicalException, IOException;
}
