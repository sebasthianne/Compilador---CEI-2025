package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class AbstractMethodNotRedefinedException extends SemanticException {
    private final Token methodName;

    public AbstractMethodNotRedefinedException(Token token, Token methodName) {
        super(token);
        this.methodName = methodName;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la clase "+getErrorToken().lexeme()+" declarada en la línea "+getErrorToken().lineNumber()+" no se redefine en el método abstracto "+getMethodName().lexeme()+" declarado en un ancestro de la clase.";
    }

    public Token getMethodName() {
        return methodName;
    }
}
