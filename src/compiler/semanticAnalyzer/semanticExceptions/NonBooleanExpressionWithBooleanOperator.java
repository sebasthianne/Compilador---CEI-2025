package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class NonBooleanExpressionWithBooleanOperator extends SemanticException {
    public NonBooleanExpressionWithBooleanOperator(Token operator) {
        super(operator);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" aparece una expresión con el operador "+getErrorToken().lexeme()+" con al menos un operando no booleano";
    }
}
