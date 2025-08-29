package compiler.lexicalAnalyzer;

import compiler.domain.Token;
import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;

import java.io.IOException;

public interface LexicalAnalyzer {
    Token getNextToken() throws LexicalException, IOException;
}
