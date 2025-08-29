package compiler.lexicalAnalyzer.lexicalExceptions;

public class NumberTooLongException extends LexicalException {
    public NumberTooLongException(String lexeme, int lineNumber, int columnNumber, String currentLine) {
        super(lexeme, lineNumber, columnNumber, currentLine);
    }

    @Override
    public String getErrorDetail() {
        return "En la línea " + getErrorData().lineNumber() + " columna " + getErrorData().columnNumber() + " se recibió un dígito después de haber recibido otros 9, creando así un entero demasiado largo.";
    }
}
