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
    public boolean isAssignableWithoutReference() {
        return true;
    }

    @Override
    public Type checkExpressionWithoutReference() throws SemanticException {
        BlockNode currentBlock = Injector.getInjector().getSymbolTable().getCurrentBlock();
        return currentBlock.resolveName(variableName);
    }

    @Override
    public boolean isCallWithoutReference() {
        return false;
    }
}
