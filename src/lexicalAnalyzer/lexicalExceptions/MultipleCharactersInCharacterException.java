package lexicalAnalyzer.lexicalExceptions;

public class MultipleCharactersInCharacterException extends LexicalException {
    public MultipleCharactersInCharacterException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }

    @Override
    public String getErrorDetail() {
        return "Durante la creación del literal caracter "+ getErrorData().lexeme() + " en la linea "+getErrorData().lineNumber()+" columna "+getErrorData().columnNumber()+" se recibió un segundo caracter antes de cerrar el literal.";
    }
}
