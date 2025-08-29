package compiler.lexicalAnalyzer.lexicalExceptions;

public class OrNotDoubledException extends LexicalException {
    public OrNotDoubledException(String lexeme, int lineNumber, int columnNumber, String currentLine) {
        super(lexeme, lineNumber, columnNumber, currentLine);
    }

    @Override
    public String getErrorDetail() {
        return "El símbolo | por si solo no es un operador válido, tiene que estar seguido por otro |. En la línea " + getErrorData().lineNumber() + " columna " + getErrorData().columnNumber() + " se recibió un caracter diferente.";
    }
}
