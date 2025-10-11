package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ConcreteMethodWithNoBodyException extends InClassException {


    public ConcreteMethodWithNoBodyException(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+this.getErrorToken().lineNumber()+" en la clase "+getClassName().lexeme()+" se declara el método concreto "+getErrorToken().lexeme()+" pero no tiene cuerpo cuando los métodos concretos tienen que tener cuerpo.";
    }
}
