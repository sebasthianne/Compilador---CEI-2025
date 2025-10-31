package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class CalledInstancedMethodInsideOfStaticMethodException extends SemanticException {
    public CalledInstancedMethodInsideOfStaticMethodException(Token calledMethodName1) {
        super(calledMethodName1);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
