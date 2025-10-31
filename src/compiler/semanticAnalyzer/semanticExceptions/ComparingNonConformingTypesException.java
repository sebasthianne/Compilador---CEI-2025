package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ComparingNonConformingTypesException extends InCallableException {
    private final Token type1;
    private final Token type2;

    public ComparingNonConformingTypesException(Token errorToken, Token currentClass, Token currentMethod, Token type1, Token type2) {
        super(errorToken,currentClass,currentMethod);
        this.type1 = type1;
        this.type2 = type2;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En el método o constructor "+getMethodOrConstructorName().lexeme()+" en la clase "+getClassName().lexeme()+" se intenta comparar con el operador "+getErrorToken().lexeme()+" do expresiones de los tipos "+type1.lexeme()+" y "+type2.lexeme()+" cuando esos tipos no conforman";
    }
}
