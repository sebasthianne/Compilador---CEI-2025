package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class IntUnaryNotIntOperatorException extends SemanticException {
    private final Token type;
    public IntUnaryNotIntOperatorException(Token operator1, Token type) {
        super(operator1);
        this.type = type;
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" se recibe una expresión unaria con el operador "+getErrorToken().lexeme()+" pero con una expresión de tipo "+type.lexeme()+" que no es entero";
    }
}
