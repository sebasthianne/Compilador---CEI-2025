package compiler.lexicalAnalyzer.lexicalExceptions;

public class NewLineInCharacterException extends LexicalException {
    public NewLineInCharacterException(String lexeme, int lineNumber, int columnNumber, String currentLine) {
        super(lexeme, lineNumber, columnNumber, currentLine);
    }

    @Override
    public String getErrorDetail() {
        return "En la línea " + getErrorData().lineNumber() + " columna " + getErrorData().columnNumber() + " se recibió un salto de línea adentro de un literal de caracter.";
    }
}
