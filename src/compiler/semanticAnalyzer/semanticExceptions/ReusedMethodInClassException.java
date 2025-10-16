package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ReusedMethodInClassException extends InClassException {
    private final int arity;

    public ReusedMethodInClassException(Token token, Token className, int arity) {
        super(token,className);
        this.arity =arity;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se recibió el id de método "+getErrorToken().lexeme()+" con "+ getArity()+"parámetros en la declaración de un método, cuando ese método ya existe en la clase "+getClassName().lexeme()+".";
    }

    public int getArity() {
        return arity;
    }
}
