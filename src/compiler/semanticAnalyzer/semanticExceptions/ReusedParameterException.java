package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ReusedParameterException extends InCallableException {
    public ReusedParameterException(Token token, Token className, Token methodOrConstructorName) {
        super(token, className, methodOrConstructorName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" en la clase "+getClassName().lexeme()+" durante la declaración del método o constructor "+getMethodOrConstructorName().lexeme()+" se utiliza el nombre de parámetro "+getErrorToken().lexeme()+" cuando ese nombre ya se había utilizado para un parámetro anterior";
    }
}
