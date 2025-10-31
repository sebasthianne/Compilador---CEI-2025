package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ConstructorNotFoundException extends SemanticException {
    public ConstructorNotFoundException(Token className) {
        super(className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se llama al constructor "+getErrorToken().lexeme()+" pero ese constructor no se pudo resolver";
    }
}
