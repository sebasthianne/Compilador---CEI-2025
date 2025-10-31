package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Type;

public class WhileConditionNotBooleanException extends SemanticException {
    public WhileConditionNotBooleanException(Type ifCheckExpressionReturnType) {
        super(ifCheckExpressionReturnType.getTypeName());
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" en el contexto de la expresión de la condición de while se recibe algo del tipo "+getErrorToken().lexeme()+" que no es boolean";
    }
}
