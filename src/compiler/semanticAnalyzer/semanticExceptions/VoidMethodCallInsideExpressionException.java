package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class VoidMethodCallInsideExpressionException extends SemanticException {
    public VoidMethodCallInsideExpressionException(Token calledMethodName) {
        super(calledMethodName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea " + getErrorToken().lineNumber() + " se llamó al método void " + getErrorToken().lexeme() + " en medio de una expresión";
    }
}
