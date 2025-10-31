package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;
import compiler.domain.Type;

public class EmptyReturnInNonVoidMethodException extends InCallableException {
    public EmptyReturnInNonVoidMethodException(Type methodReturnType, Token className, Token methodOrConstructorName) {
        super(methodReturnType.getTypeName(),className,methodOrConstructorName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la clase "+getClassName().lexeme()+" en el método "+getMethodOrConstructorName()+" con tipo de retorno "+getErrorToken().lexeme()+" en la línea "+getErrorToken().lineNumber()+" se recibe la sentencia return; cuando esta solo puede aparecer en métodos void";
    }
}
