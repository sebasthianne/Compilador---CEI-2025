package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ParameterNameMismatchInMethodRedefinition extends InCallableException {
    public ParameterNameMismatchInMethodRedefinition(Token token, Token className, Token methodOrConstructorName) {
        super(token, className, methodOrConstructorName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" en la clase "+getClassName().lexeme()+" durante la redefinición del método "+getMethodOrConstructorName().lexeme()+" se utiliza el nombre de parámetro "+getErrorToken().lexeme()+" que no concuerda con el nombre del parámetro en esa posición en el método que está siendo redefinido.";
    }
}
