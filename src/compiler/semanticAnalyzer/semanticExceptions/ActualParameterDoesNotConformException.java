package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ActualParameterDoesNotConformException extends InCallableException {
    private final Token formalParameterType;
    private final Token formalParameterName;
    private final Token actualParameterType;

    public ActualParameterDoesNotConformException(Token callableName, Token className, Token methodOrConstructorName, Token formalParameterType, Token formalParameterName, Token actualParameterType) {
        super(callableName,className,methodOrConstructorName);
        this.formalParameterType = formalParameterType;
        this.formalParameterName = formalParameterName;
        this.actualParameterType = actualParameterType;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En el método o constructor "+getMethodOrConstructorName().lexeme()+" en la clase "+getClassName().lexeme()+" en la línea "+getErrorToken().lineNumber()+" se llama al método o constructor "+getErrorToken().lexeme()+" pero en la posición del parámetro formal "+formalParameterName.lexeme()+" del tipo "+formalParameterType.lexeme()+" se recibe una expresión con tipo "+actualParameterType.lexeme()+" que no conforma con ese tipo.";
    }
}
