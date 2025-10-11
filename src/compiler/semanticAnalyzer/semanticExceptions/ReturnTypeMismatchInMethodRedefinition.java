package compiler.semanticAnalyzer.semanticExceptions;

import compiler.domain.Token;

public class ReturnTypeMismatchInMethodRedefinition extends InClassException {
    public ReturnTypeMismatchInMethodRedefinition(Token token, Token className) {
        super(token, className);
    }

    @Override
    public String getDetailedErrorMessage() {
        return "En la línea "+getErrorToken().lineNumber()+" en la clase "+getClassName().lexeme()+" durante la redefinición del método "+getErrorToken().lexeme()+" el tipo de retorno no concuerda con el tipo del retorno del método que está siendo redefinido.";
    }
}
