package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ConstructorNameClassMismatchException extends SemanticException {
    public ConstructorNameClassMismatchException(Token errorToken) {
        super(errorToken);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea " + getErrorToken().lineNumber() + " se recibió el id de clase " + getErrorToken().lexeme() + " como nombre de un constructor cuando no concuerda con el nombre de la clase.";
    }
}
