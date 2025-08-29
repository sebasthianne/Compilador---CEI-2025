package lexicalAnalyzer.lexicalExceptions;

public class ForeignCharacterException extends LexicalException {
    public ForeignCharacterException(String lexeme, int lineNumber, int columnNumber, String currentLine) {
        super(lexeme, lineNumber, columnNumber, currentLine);
    }

    @Override
    public String getErrorDetail() {
        return "El caracter "+getErrorData().lexeme()+" "+"en la linea "+ getErrorData().lineNumber()+" columna "+getErrorData().columnNumber()+" no inicia un token válido del lenguaje.";
    }
}
