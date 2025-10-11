package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class MissingReferenceTypeClassException extends InClassException {
    public MissingReferenceTypeClassException(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea " + getErrorToken().lineNumber() + " se declara un atributo, tipo de retorno o tipo de parámetro del tipo referencia " + getErrorToken().lexeme() + " en la clase " + getClassName().lexeme() + " cuando ese tipo corresponde a una clase que no está declarada.";
    }
}
