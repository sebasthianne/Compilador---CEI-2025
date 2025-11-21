package compiler.domain.abstractSyntaxTree;

public abstract class ComposedExpressionNode extends ExpressionNode {
    protected boolean isLeftSideOfAssignment=false;
    public abstract boolean isAssignable();

    public void setLeftSideOfAssignment() {
        isLeftSideOfAssignment = true;
    }

}
