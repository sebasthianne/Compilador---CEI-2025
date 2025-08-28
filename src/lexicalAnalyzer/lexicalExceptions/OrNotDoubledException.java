package lexicalAnalyzer.lexicalExceptions;

public class OrNotDoubledException extends LexicalException {
    public OrNotDoubledException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }

    @Override
    public String getErrorDetail() {
        return "El símbolo | por si solo no es un operador válido, tiene que estar seguido por otro |. En la línea "+getErrorData().lineNumber()+" columna "+getErrorData().columnNumber()+" se recibió un caracter diferente.";
    }
}
