package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class DeclaredVariableSharesNameWithParameterException extends InCallableException {
    public DeclaredVariableSharesNameWithParameterException(Token declaredVariableName,Token className, Token methodOrConstructorName) {
        super(declaredVariableName,className,methodOrConstructorName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la clase "+getClassName().lexeme()+" en el método o constructor "+getMethodOrConstructorName().lexeme()+" en la línea "+getErrorToken().lineNumber()+" se intenta declarar una variable local con nombre "+getErrorToken().lexeme()+" cuando ese nombre ya fue utilizado por un parámetro";
    }
}
