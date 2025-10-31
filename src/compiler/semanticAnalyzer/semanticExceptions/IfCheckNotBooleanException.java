package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Type;

public class IfCheckNotBooleanException extends SemanticException {
    public IfCheckNotBooleanException(Type ifCheckExpressionReturnType) {
        super(ifCheckExpressionReturnType.getTypeName());
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" en el contexto de la expresión del chequeo de una sentencia if se recibe algo del tipo "+getErrorToken().lexeme()+" en vez de un booleano";
    }
}
