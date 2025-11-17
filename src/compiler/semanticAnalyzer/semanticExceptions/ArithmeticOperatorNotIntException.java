package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ArithmeticOperatorNotIntException extends SemanticException {
    private final Token type1;
    private final Token type2;

    public ArithmeticOperatorNotIntException(Token operator, Token type1, Token type2) {
        super(operator);
        this.type1 = type1;
        this.type2 = type2;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se utiliza el operador "+getErrorToken().lexeme()+" con dos expresiones de los tipos "+type1+" y "+type2+" cuando al menos uno de esos tipos no es entero.";
    }
}
