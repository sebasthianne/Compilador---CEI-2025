package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ThisOnStaticMethodException extends InCallableException {
    public ThisOnStaticMethodException(Token thisToken,Token className,Token methodOrConstructorName) {
        super(thisToken,className,methodOrConstructorName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la clase "+getClassName().lexeme()+" en la línea "+getErrorToken().lineNumber()+" en el método estático "+getMethodOrConstructorName().lexeme()+" se hace una referencia a "+getErrorToken().lexeme();
    }
}
