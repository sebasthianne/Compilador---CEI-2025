package compiler.domain.abstractSyntaxTree;

import compiler.domain.*;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;


public class ReturnStatementNode extends StatementNode {
    private final ExpressionNode expressionToReturn;
    private final Token returnToken;

    public ReturnStatementNode(ExpressionNode expressionToReturn, Token returnToken) {
        this.expressionToReturn = expressionToReturn;
        this.returnToken = returnToken;
    }

    @Override
    public void checkNode() throws SemanticException {
        Callable currentMethodOrConstructor = Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor();
        if (currentMethodOrConstructor instanceof Constructor) throw new SemanticException(returnToken) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        }; else {
            Method currentMethod = (Method) currentMethodOrConstructor;
            Type returnType = expressionToReturn.checkExpression();
            if (!returnType.getTypeName().lexeme().equals(currentMethod.getReturnType().getTypeName().lexeme())) throw new SemanticException(returnType.getTypeName()) {
                @Override
                public String getDetailedErrorMessage() {
                    return "";
                }
            };
        }
    }
}
