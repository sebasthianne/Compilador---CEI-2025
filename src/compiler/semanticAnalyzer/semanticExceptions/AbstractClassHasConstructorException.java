package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class AbstractClassHasConstructorException extends InClassException {


    public AbstractClassHasConstructorException(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+this.getErrorToken().lineNumber()+" en la clase abstracta "+getClassName().lexeme()+" se declara un constructor cuando las clases abstractas no pueden tener constructores.";
    }
}
