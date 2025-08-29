package lexicalAnalyzer.lexicalExceptions;

public abstract class LexicalException extends Exception {
    private final ErrorData errorData;
    public LexicalException(String lexeme, int lineNumber, int columnNumber, String currentLine) {
        errorData= new ErrorData(lexeme, lineNumber, columnNumber, currentLine);
    }

    public LexicalException(ErrorData data){
        errorData=data;
    }

    public ErrorData getErrorData() {
        return errorData;
    }

    public abstract String getErrorDetail();
}
