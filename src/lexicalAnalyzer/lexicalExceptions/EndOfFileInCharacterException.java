package lexicalAnalyzer.lexicalExceptions;

public class EndOfFileInCharacterException extends LexicalException {
    public EndOfFileInCharacterException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }
}
