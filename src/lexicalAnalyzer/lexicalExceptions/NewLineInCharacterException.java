package lexicalAnalyzer.lexicalExceptions;

public class NewLineInCharacterException extends LexicalException {
    public NewLineInCharacterException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }
}
