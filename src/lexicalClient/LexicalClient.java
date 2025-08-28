package lexicalClient;

import domain.Token;
import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.lexicalExceptions.LexicalException;
import sourcemanager.SourceManager;

import java.io.IOException;

public class LexicalClient {
    LexicalAnalyzer lexicalAnalyzer;

    public LexicalClient(){
        lexicalAnalyzer= null;
    }

    public void executeLexicalAnalysis(LexicalAnalyzer analyzer) throws IOException {
        lexicalAnalyzer= analyzer;
        Token currentToken = new Token("","",0);
        try {
            while (!currentToken.lexeme().equals("" + SourceManager.END_OF_FILE)) {
                currentToken = analyzer.getNextToken();
                System.out.println(currentToken);
            }
            System.out.println();
            System.out.println("[SinErrores]");
        } catch (LexicalException e) {
            handleLexicalException(e);
        }
    }

    public void handleLexicalException(LexicalException exception){
        System.out.println(exception.getErrorData());
        System.out.println("Detalle: "+ exception.getErrorDetail());
        System.out.println();
        System.out.println("[Error:"+exception.getErrorData().lexeme()+"|"+exception.getErrorData().lineNumber()+"]");
    }

    //TODO: Implement client for the lexical analyzer
}
