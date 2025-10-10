package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ReusedAttributeNameInClassException extends SemanticException {
    private final Token className;

    public ReusedAttributeNameInClassException(Token token, Token className) {
        super(token);
        this.className=className;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se recibió el id de atributo "+getErrorToken().lexeme()+" en la declaración de un atributo, cuando ese atributo ya existe en la clase "+className.lexeme()+".";
    }
}
