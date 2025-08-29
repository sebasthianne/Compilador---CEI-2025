package compiler.lexicalAnalyzer.lexicalExceptions;

public class UnclosedStringException extends LexicalException {
    public UnclosedStringException(String lexeme, int lineNumber, int columnNumber, String currentLine) {
        super(lexeme, lineNumber, columnNumber, currentLine);
    }

    @Override
    public String getErrorDetail() {
        return "El String: " + getErrorData().lexeme() + " en la línea " + getErrorData().lineNumber() + " no es cerrado correctamente antes de recibir un salto de línea o fin de archivo en la columna " + getErrorData().columnNumber() + ".";
    }
}
