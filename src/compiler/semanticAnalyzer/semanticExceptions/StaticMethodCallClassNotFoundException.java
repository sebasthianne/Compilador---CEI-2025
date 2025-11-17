package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class StaticMethodCallClassNotFoundException extends SemanticException {
    public StaticMethodCallClassNotFoundException(Token calledMethodClassName) {
        super(calledMethodClassName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea " + getErrorToken().lineNumber() + " se llamó a un método estático de la clase " + getErrorToken().lexeme() + ", que no existe";
    }
}
