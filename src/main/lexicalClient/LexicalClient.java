package main.lexicalClient;

import compiler.domain.Token;
import compiler.lexicalAnalyzer.LexicalAnalyzer;
import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import input.sourcemanager.SourceManager;
import main.errorHandlers.ErrorHandlers;

import java.io.IOException;

public class LexicalClient {
    LexicalAnalyzer lexicalAnalyzer;

    public LexicalClient() {
        lexicalAnalyzer = null;
    }

    public void executeLexicalAnalysis(LexicalAnalyzer analyzer) throws IOException {
        lexicalAnalyzer = analyzer;
        Token currentToken = new Token("", "", 0);
        boolean errorOcurred = false;

        while (!currentToken.lexeme().equals("" + SourceManager.END_OF_FILE)) {
            try {
                currentToken = lexicalAnalyzer.getNextToken();
                System.out.println(currentToken);
            } catch (LexicalException e) {
                errorOcurred = true;
                ErrorHandlers.handleLexicalException(e);
            }
        }
        if (!errorOcurred) {
            System.out.println();
            System.out.println("[SinErrores]");
        }
    }

}
