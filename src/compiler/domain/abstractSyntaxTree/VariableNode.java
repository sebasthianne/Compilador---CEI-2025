package compiler.domain.abstractSyntaxTree;

import compiler.domain.Attribute;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.domain.Variable;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;
import inout.sourcemanager.SourceManager;

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

    @Override
    public boolean isVoidMethodCallWithoutReference() {
        return false;
    }


    @Override
    public void generateWithoutReference() {
        SourceManager source = Injector.getInjector().getSource();
        if(variable instanceof Attribute){
            source.generate("LOAD 3");
            if(!isLeftSideOfAssignment) source.generate("LOADREF "+variable.getOffset());
            else {
                source.generate("SWAP");
                source.generate("STOREREF "+variable.getOffset());
            }
        } else {
            if(!isLeftSideOfAssignment) source.generate("LOAD "+variable.getOffset());
            else source.generate("STORE "+variable.getOffset());
        }
    }
}
