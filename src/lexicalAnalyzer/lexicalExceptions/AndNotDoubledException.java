package lexicalAnalyzer.lexicalExceptions;

public class AndNotDoubledException extends LexicalException {
    public AndNotDoubledException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }
}
