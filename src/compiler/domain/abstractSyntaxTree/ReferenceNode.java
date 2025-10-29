package compiler.domain.abstractSyntaxTree;

public abstract class ReferenceNode extends BasicExpressionNode {
    protected ChainedReferenceNode chainedReference;

    public void setChainedReference(ChainedReferenceNode chainedReference) {
        this.chainedReference = chainedReference;
    }

}
