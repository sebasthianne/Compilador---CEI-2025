package lexicalAnalyzer.lexicalExceptions;

public class MultipleCharactersInCharacterException extends LexicalException {
    public MultipleCharactersInCharacterException(String lexeme, int lineNumber, int columnNumber) {
        super(lexeme, lineNumber, columnNumber);
    }
}
