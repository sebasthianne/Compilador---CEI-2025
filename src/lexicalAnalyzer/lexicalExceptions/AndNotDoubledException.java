package lexicalAnalyzer.lexicalExceptions;

public class AndNotDoubledException extends LexicalException {
    public AndNotDoubledException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }

    public String getErrorDetail() {
        return "El símbolo & por si solo no es un operador válido, tiene que estar seguido por otro &. En la línea "+getErrorData().lineNumber()+" columna "+getErrorData().columnNumber()+" se recibió un caracter diferente.";
    }
}
