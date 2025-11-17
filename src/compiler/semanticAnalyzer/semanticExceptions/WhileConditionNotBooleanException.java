package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;
import compiler.domain.Type;

public class WhileConditionNotBooleanException extends SemanticException {
    Token conditionTypeName;

    public WhileConditionNotBooleanException(Token whileToken, Type ifCheckExpressionReturnType) {
        super(whileToken);
        conditionTypeName = ifCheckExpressionReturnType.getTypeName();
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se declara una expresión while, pero su condición es del tipo "+conditionTypeName.lexeme()+" en vez de boolean";
    }
}
