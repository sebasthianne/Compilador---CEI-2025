package compiler.domain.abstractSyntaxTree;

import compiler.domain.LocalVariable;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class LocalVariableDeclarationNode extends StatementNode {
    private final Token declaredVariableName;
    private final ExpressionNode assignedExpression;

    public LocalVariableDeclarationNode(Token declaredVariableName, ExpressionNode assignedExpression) {
        this.declaredVariableName = declaredVariableName;
        this.assignedExpression = assignedExpression;
    }

    @Override
    public void checkNode() throws SemanticException {
        BlockNode currentBlock = Injector.getInjector().getSymbolTable().getCurrentBlock();
        Type expressionType = assignedExpression.checkExpression();
        currentBlock.declarationChecks(declaredVariableName);
        currentBlock.putLocalVariable(new LocalVariable(declaredVariableName,expressionType));
    }
}
