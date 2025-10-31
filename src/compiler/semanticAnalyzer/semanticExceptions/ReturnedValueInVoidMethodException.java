package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Type;

public class ReturnedValueInVoidMethodException extends SemanticException {
    public ReturnedValueInVoidMethodException(Type returnType) {
        super(returnType.getTypeName());
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
