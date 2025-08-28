package lexicalAnalyzer.lexicalExceptions;

public abstract class LexicalException extends Exception {
    private final ErrorData errorData;
    public LexicalException(String lexeme, int lineNumber, int columnNumber) {
        errorData= new ErrorData(lexeme, lineNumber, columnNumber);
    }

    public ErrorData getErrorData() {
        return errorData;
    }

    public abstract String getErrorDetail();
}
