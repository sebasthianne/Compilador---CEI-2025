package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class CalledInstanceMethodInsideOfStaticMethodException extends InCallableException {
    public CalledInstanceMethodInsideOfStaticMethodException(Token calledMethodName,Token currentClass, Token currentMethod) {
        super(calledMethodName,currentClass,currentMethod);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la clase "+getClassName().lexeme()+" en el método estático "+getMethodOrConstructorName().lexeme()+" en la line "+ getErrorToken().lineNumber()+" se intenta llamar al método de instancia " + getErrorToken().lexeme();
    }
}
