package lexicalAnalyzer.lexicalExceptions;

public class WronglyClosedMultiLineCommentException extends LexicalException {
    public WronglyClosedMultiLineCommentException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }
}
