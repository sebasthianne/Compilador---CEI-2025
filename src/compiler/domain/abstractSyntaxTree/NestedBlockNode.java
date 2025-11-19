package compiler.domain.abstractSyntaxTree;


import compiler.domain.Token;
import compiler.domain.Variable;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class NestedBlockNode extends BlockNode {
    private final BlockNode parentBlock;

    public NestedBlockNode(BlockNode parentBlock) {
        super();
        this.parentBlock = parentBlock;
    }


    @Override
    public Variable resolveNameExternal(Token name) throws SemanticException {
        return parentBlock.resolveName(name);
    }

    @Override
    public void declarationChecks(Token declaredVariableName) throws SemanticException {
        super.declarationChecks(declaredVariableName);
        parentBlock.declarationChecks(declaredVariableName);
    }
}
