package lexicalAnalyzer.lexicalExceptions;

public class UnclosedMultiLineCommentException extends LexicalException {
    public UnclosedMultiLineCommentException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }

    @Override
    public String getErrorDetail(){
        return "El comentario abierto en la linea "+getErrorData().lineNumber()+" columna "+getErrorData().columnNumber()+" nunca se cierra.";
    }

}
