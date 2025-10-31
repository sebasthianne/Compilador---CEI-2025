package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class IntComparisonOperatorNotIntException extends SemanticException {
    private final Token type1;
    private final Token type2;
    public IntComparisonOperatorNotIntException(Token operator, Token type1, Token type2) {
        super(operator);
        this.type1 = type1;
        this.type2 = type2;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" en el contexto de una expresión binaria con el operador de comparación "+getErrorToken().lexeme()+" se reciben expresiones de los tipos "+type1.lexeme()+" y "+type2.lexeme()+" cuando alguno de ellos no es entero";
    }
}
