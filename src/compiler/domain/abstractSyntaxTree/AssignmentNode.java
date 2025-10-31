package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.AssignedTypeDoesNotConformException;
import compiler.semanticAnalyzer.semanticExceptions.AssigningToNonAssignableException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class AssignmentNode extends ExpressionNode {
    private final ComposedExpressionNode variableAssignedTo;
    private final ComposedExpressionNode assignedExpression;
    private final Token assignmentToken;

    public AssignmentNode(ComposedExpressionNode variableAssignedTo, ComposedExpressionNode assignedExpression, Token assignmentToken) {
        this.variableAssignedTo = variableAssignedTo;
        this.assignedExpression = assignedExpression;
        this.assignmentToken = assignmentToken;
    }

    @Override
    public Type checkExpression() throws SemanticException {
        if(!variableAssignedTo.isAssignable()) throw new AssigningToNonAssignableException(assignmentToken);
        Type assignedToType = variableAssignedTo.checkExpression();
        Type assignedType = assignedExpression.checkExpression();
        if(!assignedType.conformsTo(assignedToType)) {
            Token semicolonToken = this.semicolonToken;
            throw new AssignedTypeDoesNotConformException(semicolonToken, Injector.getInjector().getSymbolTable().getCurrentClass().getName(), Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor().getName(),assignedToType.getTypeName(),assignedType.getTypeName());
        }
        return assignedToType;
    }

    @Override
    public boolean isCall() {
        return false;
    }

}
