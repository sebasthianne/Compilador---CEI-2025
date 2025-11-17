package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;
import compiler.domain.Type;

public class ReturnedValueInVoidMethodException extends InCallableException {
    private final Token returnType;
    public ReturnedValueInVoidMethodException(Token returnToken, Type returnType, Token className, Token methodOrConstructorName) {
        super(returnToken,className,methodOrConstructorName);
        this.returnType = returnType.getTypeName();
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" en la clase "+getClassName().lexeme()+" en el método void "+getMethodOrConstructorName().lexeme()+" se devuelve una expresión de tipo "+returnType.lexeme();
    }
}
