package compiler.domain.abstractSyntaxTree;

import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public abstract class PrimaryNode extends ReferenceNode {

    @Override
    public Type checkExpression() throws SemanticException {
        if(chainedReference!=null) return chainedReference.checkChainedReference(checkExpressionWithoutReference());
        else return checkExpressionWithoutReference();
    }

    public abstract Type checkExpressionWithoutReference() throws SemanticException;


}
