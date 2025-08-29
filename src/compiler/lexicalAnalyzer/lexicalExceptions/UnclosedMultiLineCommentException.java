package compiler.lexicalAnalyzer.lexicalExceptions;

public class UnclosedMultiLineCommentException extends LexicalException {
    public UnclosedMultiLineCommentException(ErrorData errorData) {
        super(errorData);
    }

    @Override
    public String getErrorDetail() {
        return "El comentario abierto en la linea " + getErrorData().lineNumber() + " columna " + getErrorData().columnNumber() + " nunca se cierra.";
    }

}
