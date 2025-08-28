package lexicalAnalyzer.lexicalExceptions;

public class UnclosedStringException extends LexicalException {
    public UnclosedStringException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }
}
