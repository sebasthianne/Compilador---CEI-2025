package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ReusedClassNameException extends SemanticException {
    public ReusedClassNameException(Token token) {
        super(token);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+this.getErrorToken().lineNumber()+" se recibió el identificador de clase "+this.getErrorToken().lexeme()+" en contexto de una declaración de clase, cuándo ya hay una clase con ese nombre declarada.";
    }
}
