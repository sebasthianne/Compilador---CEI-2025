package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Type;

public class EmptyReturnInNonVoidMethodException extends SemanticException {
    public EmptyReturnInNonVoidMethodException(Type methodReturnType) {
        super(methodReturnType.getTypeName());
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
