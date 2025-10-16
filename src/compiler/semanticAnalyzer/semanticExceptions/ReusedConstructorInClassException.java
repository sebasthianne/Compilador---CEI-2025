package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ReusedConstructorInClassException extends ReusedMethodInClassException {

    public ReusedConstructorInClassException(Token token, Token className, int arity) {
        super(token,className,arity);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se recibió el id de constructor "+getErrorToken().lexeme()+" con "+ getArity()+" parámetros en la declaración de un constructor, cuando ya existe un constructor con esa aridad en la clase.";
    }
}
