package main.lexicalClient;

import compiler.domain.Token;
import compiler.lexicalAnalyzer.LexicalAnalyzer;
import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import input.sourcemanager.SourceManager;

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
                currentToken = analyzer.getNextToken();
                System.out.println(currentToken);
            } catch (LexicalException e) {
                errorOcurred = true;
                handleLexicalException(e);
            }
        }
        if (!errorOcurred) {
            System.out.println();
            System.out.println("[SinErrores]");
        }
    }

    public void handleLexicalException(LexicalException exception) {
        System.out.println(exception.getErrorData());
        System.out.println("Error Detallado: " + exception.getErrorDetail());
        System.out.println("Detalle: " + exception.getErrorData().currentLine());
        System.out.println(" ".repeat(exception.getErrorData().columnNumber() + 8) + "^");
        System.out.println();
        System.out.println("[Error:" + exception.getErrorData().lexeme() + "|" + exception.getErrorData().lineNumber() + "]");
    }

}
