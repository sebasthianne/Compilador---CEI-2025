package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ChainedToVoidMethodCallException extends SemanticException {
    public ChainedToVoidMethodCallException(Token chainedName) {
        super(chainedName);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea " + getErrorToken().lineNumber() + " se encadena el método o atributo "+getErrorToken().lexeme()+" a un método void, pero un método void no puede ser encadenado porque no tiene retorno";
    }
}
