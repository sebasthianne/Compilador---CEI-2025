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
        return "En el método o constructor "+getMethodOrConstructorName().lexeme()+" en la clase "+getClassName().lexeme()+" en la línea "+getErrorToken().lineNumber()+" se recibió un "+getErrorToken().lexeme()+" en el contexto de una asignación cuando el tipo de la expresión "+assignedExpressionType.lexeme()+" aún no concuerda con el tipo de la variable o atributo a la que fue asignado, del tipo "+assignedToType.lexeme();
    }
}
