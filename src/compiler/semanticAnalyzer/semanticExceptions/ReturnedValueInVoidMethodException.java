package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;
import compiler.domain.Type;

public class ReturnedValueInVoidMethodException extends InCallableException {
    public ReturnedValueInVoidMethodException(Type returnType, Token className, Token methodOrConstructorName) {
        super(returnType.getTypeName(),className,methodOrConstructorName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" en la clase "+getClassName().lexeme()+" en el método void "+getMethodOrConstructorName().lexeme()+" se devuelve una expresión de tipo "+getErrorToken().lexeme();
    }
}
