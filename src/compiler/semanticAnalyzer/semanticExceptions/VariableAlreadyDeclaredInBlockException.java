package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class VariableAlreadyDeclaredInBlockException extends SemanticException {
    public VariableAlreadyDeclaredInBlockException(Token declaredVariableName) {
        super(declaredVariableName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se intenta declara una variable con nombre "+getErrorToken().lexeme()+" cuando esa variable ya se declaró en el bloque actual o en un bloque que lo contiene";
    }
}
