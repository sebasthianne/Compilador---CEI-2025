package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ChainedToPrimitiveException extends SemanticException {
    public ChainedToPrimitiveException(Token pointToken1) {
        super(pointToken1);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "";
    }
}
