package lexicalAnalyzer.lexicalExceptions;

public class NewLineInCharacterException extends LexicalException {
    public NewLineInCharacterException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }

    @Override
    public String getErrorDetail() {
        return "En la línea "+getErrorData().lineNumber()+" columna "+getErrorData().columnNumber()+" se recibió un salto de línea adentro de un literal de caracter.";
    }
}
