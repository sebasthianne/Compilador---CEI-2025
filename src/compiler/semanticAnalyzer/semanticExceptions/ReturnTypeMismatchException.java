package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Type;

public class ReturnTypeMismatchException extends SemanticException {
    public ReturnTypeMismatchException(Type returnType) {
        super(returnType.getTypeName());
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
