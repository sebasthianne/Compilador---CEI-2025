package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class InheritsFromMissingClass extends InClassException {

    public InheritsFromMissingClass(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" en la clase "+getClassName().lexeme()+" se intenta heredar de la clase "+getErrorToken().lexeme()+" cuando esa clase no fue declarada.";
    }
}
