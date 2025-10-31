package compiler.domain.abstractSyntaxTree;

import compiler.domain.*;
import compiler.semanticAnalyzer.semanticExceptions.*;
import injector.Injector;


public class ReturnStatementNode extends StatementNode {
    private ExpressionNode expressionToReturn;
    private final Token returnToken;

    public ReturnStatementNode(Token returnToken) {
        this.returnToken = returnToken;
    }

    @Override
    public void checkNode() throws SemanticException {
        Callable currentMethodOrConstructor = Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor();
        if (currentMethodOrConstructor instanceof Constructor) {
            throw new ReturnStatementInConstructorException(this.returnToken);
        } else methodReturnChecks((Method) currentMethodOrConstructor);
    }

    private void methodReturnChecks(Method currentMethodOrConstructor) throws SemanticException {
        Type methodReturnType = currentMethodOrConstructor.getReturnType();
        if (expressionToReturn != null) {
            Type returnType = expressionToReturn.checkExpression();
            if (methodReturnType == null) throw new ReturnedValueInVoidMethodException(returnType,Injector.getInjector().getSymbolTable().getCurrentClass().getName(),Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor().getName());
            if (!returnType.conformsTo(methodReturnType))
                throw new ReturnTypeMismatchException(returnType,methodReturnType.getTypeName());
        } else {
            if (methodReturnType != null) throw new EmptyReturnInNonVoidMethodException(methodReturnType,Injector.getInjector().getSymbolTable().getCurrentClass().getName(), Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor().getName());
        }
    }

    public void setExpressionToReturn(ExpressionNode expressionToReturn) {
        this.expressionToReturn = expressionToReturn;
    }

}
