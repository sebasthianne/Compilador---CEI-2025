package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;
import compiler.domain.Type;

public class EmptyReturnInNonVoidMethodException extends InCallableException {

    private final Token returnType;

    public EmptyReturnInNonVoidMethodException(Token returnToken, Type methodReturnType, Token className, Token methodOrConstructorName) {
        super(returnToken,className,methodOrConstructorName);
        this.returnType = methodReturnType.getTypeName();
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la clase "+getClassName().lexeme()+" en el método "+getMethodOrConstructorName()+" con tipo de retorno "+returnType.lexeme()+" en la línea "+getErrorToken().lineNumber()+" se recibe la sentencia return; cuando esta solo puede aparecer en métodos void";
    }
}
