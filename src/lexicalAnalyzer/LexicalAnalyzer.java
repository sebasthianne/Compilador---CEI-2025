package lexicalAnalyzer;

import domain.Token;
import lexicalAnalyzer.lexicalExceptions.LexicalException;

import java.io.IOException;

public interface LexicalAnalyzer {
    Token getNextToken() throws LexicalException, IOException;
}
