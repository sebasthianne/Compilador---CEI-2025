package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class AttributeCouldNotBeResolvedException extends SemanticException {
    public AttributeCouldNotBeResolvedException(Token name) {
        super(name);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se intenta acceder al atributo o variable "+getErrorToken().lexeme()+" pero esa variable no se pudo resolver";
    }
}
