package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;
import compiler.domain.Type;

public class ReturnTypeMismatchException extends SemanticException {
    private final Token type;
    public ReturnTypeMismatchException(Type returnType, Token type) {
        super(returnType.getTypeName());
        this.type = type;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se retorna una expresión de tipo "+getErrorToken().lexeme()+" que no conforma con el tipo "+type.lexeme()+" que devuelve el método";
    }
}
