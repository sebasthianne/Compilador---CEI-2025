package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class AssignedTypeDoesNotConformException extends InCallableException {
    private final Token assignedToType;
    private final Token assignedExpressionType;

    public AssignedTypeDoesNotConformException(Token semicolonToken, Token className, Token methodOrConstructorName,Token assignedToType, Token assignedExpressionType) {
        super(semicolonToken,className,methodOrConstructorName);
        this.assignedToType = assignedToType;
        this.assignedExpressionType = assignedExpressionType;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En el método o constructor "+getMethodOrConstructorName().lexeme()+" en la clase "+getClassName().lexeme()+" en la línea "+getErrorToken().lineNumber()+" ocurre un error de tipos incompatibles en el contexto de una asignación cuando a una variable de tipo "+assignedToType.lexeme()+" se le intenta asignar una expresión de tipo "+assignedExpressionType.lexeme();
    }
}
