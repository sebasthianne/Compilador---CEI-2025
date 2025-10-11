package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class RedefinedAttributeException extends InClassException {
    public RedefinedAttributeException(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea " + getErrorToken().lineNumber() + " en la clase " + getClassName() + " se intenta declarar el atributo " + getErrorToken().lexeme() + " cuando ese atributo ya fue declarado en una clase ancestro.";
    }
}
