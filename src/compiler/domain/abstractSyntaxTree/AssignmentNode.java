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
        variableAssignedTo.setLeftSideOfAssignment();
        Type assignedType = assignedExpression.checkExpression();
        if(!assignedType.conformsTo(assignedToType)) {
            throw new AssignedTypeDoesNotConformException(assignmentToken, Injector.getInjector().getSymbolTable().getCurrentClass().getName(), Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor().getName(),assignedToType.getTypeName(),assignedType.getTypeName());
        }
        return assignedToType;
    }

    @Override
    public boolean isCall() {
        return false;
    }

    @Override
    public boolean isVoidMethodCall() {
        return false;
    }

    @Override
    public void generate() {
        assignedExpression.generate();
        Injector.getInjector().getSource().generate("DUP");
        variableAssignedTo.generate();
    }
}
