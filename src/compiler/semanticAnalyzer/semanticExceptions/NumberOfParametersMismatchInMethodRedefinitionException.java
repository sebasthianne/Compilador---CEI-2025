package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class NumberOfParametersMismatchInMethodRedefinitionException extends InClassException {
    public NumberOfParametersMismatchInMethodRedefinitionException(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" en la clase "+getClassName().lexeme()+" se redefine el método "+getErrorToken().lexeme()+" que no tiene la misma cantidad de parámetros que el método que redefine.";
    }
}
