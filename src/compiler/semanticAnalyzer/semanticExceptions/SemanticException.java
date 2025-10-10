package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public abstract class SemanticException extends Exception {
    final Token errorToken;
    public SemanticException(Token token) {
        errorToken=token;
    }

    public Token getErrorToken() {
        return errorToken;
    }

    public String getErrorMessage() {
        return "Error Sintáctico en línea "+errorToken.lineNumber()+" con token: "+errorToken.name()+" y lexema: "+errorToken.lexeme()+".\nError Detallado: "+getDetailedErrorMessage();
    }

    public abstract String getDetailedErrorMessage();
}
