package compiler.domain.abstractSyntaxTree;

public abstract class ReferenceNode extends BasicExpressionNode {
    protected ChainedReferenceNode chainedReference;

    public void setChainedReference(ChainedReferenceNode chainedReference) {
        this.chainedReference = chainedReference;
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

    public abstract boolean isAssignableWithoutReference();

    public abstract boolean isCallWithoutReference();
}
