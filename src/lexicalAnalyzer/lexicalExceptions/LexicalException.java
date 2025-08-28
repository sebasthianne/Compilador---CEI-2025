package lexicalAnalyzer.lexicalExceptions;

public class LexicalException extends RuntimeException {
    private final ErrorData errorData;
    public LexicalException(String lexeme, int lineNumber, int columnNumber) {
        errorData= new ErrorData(lexeme, lineNumber, columnNumber);
    }

    public ErrorData getErrorData() {
        return errorData;
    }

    //TODO: Make Subclasses for more specific types
}
