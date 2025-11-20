package compiler.domain.abstractSyntaxTree;

public abstract class ReferenceNode extends BasicExpressionNode {
    protected ChainedReferenceNode chainedReference;

    public void setChainedReference(ChainedReferenceNode chainedReference) {
        this.chainedReference = chainedReference;
        if(!isInExpression()) this.chainedReference.setInExpression(false);
    }

    @Override
    public boolean isCall() {
        if(chainedReference!=null) return chainedReference.isCall();
        return isCallWithoutReference();
    }

    @Override
    public boolean isAssignable() {
        if(chainedReference!=null) return chainedReference.isAssignable();
        else return isAssignableWithoutReference();
    }

    @Override
    public void setLeftSideOfAssignment() {
        if(chainedReference!=null) chainedReference.setLeftSideOfAssignment();
        else isLeftSideOfAssignment = true;
    }

    @Override
    public final void generate() {
        generateWithoutReference();
        if(chainedReference!=null) chainedReference.generate();
    }

    public void generateWithoutReference(){

    }

    public abstract boolean isAssignableWithoutReference();

    public abstract boolean isCallWithoutReference();
}
