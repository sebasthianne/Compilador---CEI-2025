package compiler.domain.abstractSyntaxTree;

import compiler.GenerationUtils;
import compiler.domain.*;
import compiler.semanticAnalyzer.semanticExceptions.*;
import injector.Injector;
import inout.sourcemanager.SourceManager;


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
            if (methodReturnType == null) throw new ReturnedValueInVoidMethodException(returnToken,returnType,Injector.getInjector().getSymbolTable().getCurrentClass().getName(),Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor().getName());
            if (!returnType.conformsTo(methodReturnType))
                throw new ReturnTypeMismatchException(returnToken,returnType,methodReturnType.getTypeName());
        } else {
            if (methodReturnType != null) throw new EmptyReturnInNonVoidMethodException(returnToken,methodReturnType,Injector.getInjector().getSymbolTable().getCurrentClass().getName(), Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor().getName());
        }
    }

    public void setExpressionToReturn(ExpressionNode expressionToReturn) {
        this.expressionToReturn = expressionToReturn;
    }

    @Override
    public void generate() {
        SourceManager source = Injector.getInjector().getSource();
        if(expressionToReturn!=null){
            expressionToReturn.generate();
            Callable currentMethodOrConstructor = Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor();
            source.generate("STORE "+ currentMethodOrConstructor.getCurrentParameterOffset());
            source.generate("STOREFP");
            source.generate(GenerationUtils.getReturnInstruction(currentMethodOrConstructor));
        }

    }
}
