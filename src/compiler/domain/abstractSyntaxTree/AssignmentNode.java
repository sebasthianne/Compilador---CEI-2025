package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

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
        if(!variableAssignedTo.isAssignable()) throw new SemanticException(assignmentToken) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
        Type assignedToType = variableAssignedTo.checkExpression();
        if(!assignedToType.compareType(assignedExpression.checkExpression())) throw new SemanticException(semicolonToken) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
        return assignedToType;
    }
}
