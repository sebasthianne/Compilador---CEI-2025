package compiler.domain.abstractSyntaxTree;

import compiler.domain.ReferenceType;
import compiler.domain.Token;
import compiler.domain.Type;

public class StringLiteralNode extends PrimaryNode {
    @Override
    public Type checkExpressionWithoutReference(){
        return new ReferenceType(new Token("idClase","String",-1));
    }

    @Override
    public boolean isAssignable() {
        return false;
    }
}
