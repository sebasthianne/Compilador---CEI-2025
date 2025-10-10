package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class MissingAttributeTypeException extends InClassException {
    public MissingAttributeTypeException(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea " + getErrorToken().lineNumber() + " se declara de tipo " + getErrorToken().lexeme() + " un atributo de la clase " + getClassName().lexeme() + " cuando ese tipo referencia corresponde a una clase que no está declarada.";
    }
}
