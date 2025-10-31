package compiler.domain.abstractSyntaxTree;

import compiler.domain.LocalVariable;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.DeclaredVariableIsNullTypeException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

import javax.lang.model.type.NullType;

public class LocalVariableDeclarationNode extends StatementNode {
    private final Token declaredVariableName;
    private final ExpressionNode assignedExpression;
    private final Token assignmentToken;

    public LocalVariableDeclarationNode(Token declaredVariableName, ExpressionNode assignedExpression, Token assignmentToken) {
        this.declaredVariableName = declaredVariableName;
        this.assignedExpression = assignedExpression;
        this.assignmentToken = assignmentToken;
    }

    @Override
    public void checkNode() throws SemanticException {
        BlockNode currentBlock = Injector.getInjector().getSymbolTable().getCurrentBlock();
        Type expressionType = assignedExpression.checkExpression();
        if(expressionType instanceof NullType) throw new DeclaredVariableIsNullTypeException(assignmentToken);
        currentBlock.declarationChecks(declaredVariableName);
        currentBlock.putLocalVariable(new LocalVariable(declaredVariableName,expressionType));
    }

}
