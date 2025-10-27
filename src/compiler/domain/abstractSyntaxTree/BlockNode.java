package compiler.domain.abstractSyntaxTree;

import compiler.domain.Block;
import compiler.domain.LocalVariable;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BlockNode extends StatementNode {
    private final HashMap<String, LocalVariable> localVariablesTable;
    private final List<StatementNode> statementsTable;

    public BlockNode(){
        Injector.getInjector().getSymbolTable().setCurrentBlock(this);
        this.localVariablesTable = new HashMap<>(47);
        this.statementsTable = new ArrayList<>(50);
    }

    @Override
    public void checkNode() throws SemanticException {
        Injector.getInjector().getSymbolTable().setCurrentBlock(this);
        for (StatementNode s : statementsTable){
            s.checkNode();
        }
    }

    public Type resolveName(Token name) throws SemanticException{
        LocalVariable localVariable = resolveNameLocal(name);
        if(localVariable==null) return resolveNameExternal(name);
        else return localVariable.getType();
    }

    public void putLocalVariable(LocalVariable localVariable){
        localVariablesTable.put(localVariable.getName().lexeme(),localVariable);
    }

    public LocalVariable getLocalVariable(Token variableName){
        return localVariablesTable.get(variableName.lexeme());
    }

    private LocalVariable resolveNameLocal(Token variableName) {
        return getLocalVariable(variableName);
    }

    public void declarationChecks(Token declaredVariableName) throws SemanticException{
        if(getLocalVariable(declaredVariableName)!=null) throw new SemanticException(declaredVariableName) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
    }

    protected abstract Type resolveNameExternal(Token name) throws SemanticException;

}
