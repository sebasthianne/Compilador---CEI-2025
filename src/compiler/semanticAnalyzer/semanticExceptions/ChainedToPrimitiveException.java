package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ChainedToPrimitiveException extends SemanticException {
    private final Token type;

    public ChainedToPrimitiveException(Token chainedName, Token type) {
        super(chainedName);
        this.type = type;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se encadena el método o atributo "+getErrorToken().lexeme()+" a una expresión de tipo "+type.lexeme()+" que no puede ser encadenado";
    }
}
