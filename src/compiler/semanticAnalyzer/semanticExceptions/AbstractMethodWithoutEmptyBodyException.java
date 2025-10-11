package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class AbstractMethodWithoutEmptyBodyException extends InClassException {


    public AbstractMethodWithoutEmptyBodyException(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+this.getErrorToken().lineNumber()+" en la clase "+getClassName().lexeme()+" se declara el método abstracto "+getErrorToken().lexeme()+" pero tiene cuerpo cuando los métodos abstractos no pueden tener cuerpo.";
    }
}
