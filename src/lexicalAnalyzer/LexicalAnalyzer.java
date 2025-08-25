package lexicalAnalyzer;

import java.io.IOException;

public interface LexicalAnalyzer {
    Token getNextToken() throws LexicalException, IOException;
}
