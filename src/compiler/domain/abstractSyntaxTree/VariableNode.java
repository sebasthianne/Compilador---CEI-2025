package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class VariableNode extends PrimaryNode {
    private final Token variableName;

    public VariableNode(Token variableName) {
        this.variableName = variableName;
    }

    @Override
    public boolean isAssignable() {
        return true;
    }

    @Override
    public Type checkExpression() throws SemanticException {
        BlockNode currentBlock = Injector.getInjector().getSymbolTable().getCurrentBlock();
        return currentBlock.resolveName(variableName);
    }
}
