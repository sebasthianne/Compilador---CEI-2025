package compiler.domain.abstractSyntaxTree;


import compiler.domain.Token;
import compiler.domain.Type;

public class NestedBlockNode extends BlockNode {
    BlockNode parentBlock;

    @Override
    public Type resolveNameExternal(Token variableName) {
        return parentBlock.resolveName(variableName);
    }

    //TODO: Ask if it's preferable for the parent-child relationship to be on blocks or on nodes.
}
