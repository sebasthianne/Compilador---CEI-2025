package compiler.lexicalAnalyzer.lexicalExceptions;

public class EmptyCharacterException extends LexicalException {
    public EmptyCharacterException(String lexeme, int lineNumber, int columnNumber, String currentLine) {
        super(lexeme, lineNumber, columnNumber, currentLine);
    }

    @Override
    public String getErrorDetail() {
        return "En la línea "+getErrorData().lineNumber()+" columna "+getErrorData().columnNumber()+" se recibió "+getErrorData().lexeme()+" inmediatamente después de otra ', pero los caracteres vacíos no son válidos.";
    }
}
