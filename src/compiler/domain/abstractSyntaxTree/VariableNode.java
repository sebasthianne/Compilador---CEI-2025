package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;
import compiler.domain.Type;
import compiler.domain.Variable;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class VariableNode extends PrimaryNode {
    private final Token variableName;
    private Variable variable;

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
        variable = currentBlock.resolveName(variableName);
        return variable.getType();
    }

    @Override
    public boolean isCallWithoutReference() {
        return false;
    }

    public boolean isNameResolved(){
        return variable!=null;
    }
}
