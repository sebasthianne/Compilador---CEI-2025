package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class RedefiningInstanceMethodWithStaticMethodException extends InClassException {
    public RedefiningInstanceMethodWithStaticMethodException(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" en la clase "+getClassName().lexeme()+" se redefine el método de instancia "+getErrorToken().lexeme()+" con un método estático.";
    }
}
