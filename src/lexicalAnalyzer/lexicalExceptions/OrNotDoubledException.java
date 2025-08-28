package lexicalAnalyzer.lexicalExceptions;

public class OrNotDoubledException extends LexicalException {
    public OrNotDoubledException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }
}
