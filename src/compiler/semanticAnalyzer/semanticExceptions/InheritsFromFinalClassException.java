package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class InheritsFromFinalClassException extends InClassException {


    public InheritsFromFinalClassException(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+this.getErrorToken().lineNumber()+" en la clase "+getClassName().lexeme()+" se extiende a la clase "+getErrorToken().lexeme()+" cuando esa clase es final.";
    }
}
