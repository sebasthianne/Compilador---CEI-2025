package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;
import compiler.domain.Type;

public class ReturnTypeMismatchException extends SemanticException {
    private final Token type;
    private final Token returnType;
    public ReturnTypeMismatchException(Token returnToken,Type returnType, Token type) {
        super(returnToken);
        this.type = type;
        this.returnType = returnType.getTypeName();
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se retorna una expresión de tipo "+returnType.lexeme()+" que no conforma con el tipo "+type.lexeme()+" que devuelve el método";
    }
}
