package lexicalAnalyzer.lexicalExceptions;

public class EndOfFileInCharacterException extends LexicalException {
    public EndOfFileInCharacterException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }

    @Override
    public String getErrorDetail() {
        return "En la linea "+getErrorData().lineNumber()+" columna "+getErrorData().columnNumber()+" el archivo finalizó en medio de la declaración de un literal caracter.";
    }
}
