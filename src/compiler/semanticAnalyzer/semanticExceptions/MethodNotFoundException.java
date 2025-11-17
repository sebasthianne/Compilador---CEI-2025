package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class MethodNotFoundException extends InClassException {
    public MethodNotFoundException(Token methodName,Token className) {
        super(methodName,className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se realiza una llamada al método "+getErrorToken().lexeme()+" cuando ese método no existe en la clase "+getClassName().lexeme();
    }
}
