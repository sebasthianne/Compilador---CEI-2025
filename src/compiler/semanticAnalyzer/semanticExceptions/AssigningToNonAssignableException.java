package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class AssigningToNonAssignableException extends SemanticException {

    public AssigningToNonAssignableException(Token a) {
        super(a);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se intenta asignar un valor a una expresión que no es asignable";
    }
}
