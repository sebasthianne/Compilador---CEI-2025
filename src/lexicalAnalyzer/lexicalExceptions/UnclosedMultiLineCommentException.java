package lexicalAnalyzer.lexicalExceptions;

public class UnclosedMultiLineCommentException extends LexicalException {
    public UnclosedMultiLineCommentException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }
}
