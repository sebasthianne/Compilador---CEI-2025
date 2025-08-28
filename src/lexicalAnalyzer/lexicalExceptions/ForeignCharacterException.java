package lexicalAnalyzer.lexicalExceptions;

public class ForeignCharacterException extends LexicalException {
    public ForeignCharacterException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }
}
