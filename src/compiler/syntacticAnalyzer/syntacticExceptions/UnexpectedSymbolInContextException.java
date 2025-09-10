package compiler.syntacticAnalyzer.syntacticExceptions;

import compiler.domain.Token;

public class UnexpectedSymbolInContextException extends SyntacticException {
    private String exceptionContext;
    public UnexpectedSymbolInContextException(String expectedName, Token received,String context)
    {
        super(expectedName, received);
        exceptionContext=context;
    }

}
