package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class AbstractMethodInConcreteClassException extends InClassException {


    public AbstractMethodInConcreteClassException(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+this.getErrorToken().lineNumber()+" en la clase concreta "+getClassName().lexeme()+" se declara el método abstracto "+getErrorToken().lexeme()+" cuando las clases concretas no pueden tener métodos abstractos.";
    }
}
