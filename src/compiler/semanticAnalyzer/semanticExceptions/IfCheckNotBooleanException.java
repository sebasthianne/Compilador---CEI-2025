package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;
import compiler.domain.Type;

public class IfCheckNotBooleanException extends SemanticException {
    private final Token checkTypeName;

    public IfCheckNotBooleanException(Token whileToken, Type ifCheckExpressionReturnType) {
        super(whileToken);
        checkTypeName = ifCheckExpressionReturnType.getTypeName();
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se declara una sentencia if, pero en la expresión de su chequeo es del tipo "+checkTypeName.lexeme()+" en vez de boolean";
    }
}
