package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ReusedMethodNameInClassException extends SemanticException {
    private final Token className;

    public ReusedMethodNameInClassException(Token token, Token className) {
        super(token);
        this.className=className;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se recibió el id de método "+getErrorToken().lexeme()+" en la declaración de un método, cuando ese método ya existe en la clase "+className.lexeme()+".";
    }
}
