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
        }; else methodReturnChecks((Method) currentMethodOrConstructor);
    }

    private void methodReturnChecks(Method currentMethodOrConstructor) throws SemanticException {
        Type methodReturnType = currentMethodOrConstructor.getReturnType();
        if (expressionToReturn != null) {
            Type returnType = expressionToReturn.checkExpression();
            if (methodReturnType == null) throw new SemanticException(returnType.getTypeName()) {
                @Override
                public String getDetailedErrorMessage() {
                    return "";
                }
            };
            if (!returnType.getTypeName().lexeme().equals(methodReturnType.getTypeName().lexeme()))
                throw new SemanticException(returnType.getTypeName()) {
                    @Override
                    public String getDetailedErrorMessage() {
                        return "";
                    }
                };
        } else {
            if (methodReturnType != null) throw new SemanticException(methodReturnType.getTypeName()) {
                @Override
                public String getDetailedErrorMessage() {
                    return "";
                }
            };
        }
    }
}
