package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class CircularInheritanceException extends SemanticException {
    public CircularInheritanceException(Token token) {
        super(token);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "Herencia Circular: la clase " + getErrorToken().lexeme() + " declarada en la línea " + getErrorToken().lineNumber() + " es su propio ancestro.";
    }
}
