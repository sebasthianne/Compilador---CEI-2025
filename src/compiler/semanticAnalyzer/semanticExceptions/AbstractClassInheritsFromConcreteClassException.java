package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class AbstractClassInheritsFromConcreteClassException extends InClassException {


    public AbstractClassInheritsFromConcreteClassException(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+this.getErrorToken().lineNumber()+" en la clase abstracta "+getClassName().lexeme()+" se extiende a la clase "+getErrorToken().lexeme()+" cuando esa clase es concreta.";
    }
}
