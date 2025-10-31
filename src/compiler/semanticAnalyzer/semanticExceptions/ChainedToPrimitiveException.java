package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ChainedToPrimitiveException extends SemanticException {
    private final Token type;

    public ChainedToPrimitiveException(Token pointToken1, Token type) {
        super(pointToken1);
        this.type = type;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se recibe "+getErrorToken().lexeme()+" luego de una expresión de tipo "+type.lexeme()+" que no puede ser encadenado";
    }
}
