package lexicalAnalyzer.lexicalExceptions;

public class NumberTooLongException extends LexicalException {
    public NumberTooLongException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }
}
