package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Type;

public class WhileConditionNotBooleanException extends SemanticException {
    public WhileConditionNotBooleanException(Type ifCheckExpressionReturnType) {
        super(ifCheckExpressionReturnType.getTypeName());
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
