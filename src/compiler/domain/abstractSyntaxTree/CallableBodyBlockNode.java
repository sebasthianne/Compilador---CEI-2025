package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;
import compiler.domain.Type;

public class CallableBodyBlockNode extends BlockNode {
    @Override
    public Type resolveNameExternal(Token variableName) {
        return null;
    } //TODO Implement external name resolution
}
