package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class RedefiningFinalMethodException extends InClassException {
    public RedefiningFinalMethodException(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" en la clase "+getClassName().lexeme()+" se redefine el método "+getErrorToken().lexeme()+" que es final.";
    }
}
