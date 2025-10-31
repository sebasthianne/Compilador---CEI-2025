package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class DeclaredVariableIsNullTypeException extends SemanticException {
    public DeclaredVariableIsNullTypeException(Token assignmentToken) {
        super(assignmentToken);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lexeme()+" se recibe null luego de "+getErrorToken().lexeme()+" en el contexto de la declaración de una variable, pero no se puede declarar una variable como tipo null";
    }
}
