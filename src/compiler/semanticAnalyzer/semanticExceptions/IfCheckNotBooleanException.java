package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Type;

public class IfCheckNotBooleanException extends SemanticException {
    public IfCheckNotBooleanException(Type ifCheckExpressionReturnType) {
        super(ifCheckExpressionReturnType.getTypeName());
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
