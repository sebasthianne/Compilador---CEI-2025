package compiler.domain.abstractSyntaxTree;

import compiler.domain.LocalVariable;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.NonValidStatementExpression;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.semanticAnalyzer.semanticExceptions.VariableAlreadyDeclaredInBlockException;
import injector.Injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class BlockNode extends StatementNode {
    private final HashMap<String, LocalVariable> localVariablesTable;
    private final List<StatementNode> statementsTable;

    public BlockNode(){
        this.localVariablesTable = new HashMap<>(47);
        this.statementsTable = new ArrayList<>(50);
    }

    @Override
    public void checkNode() throws SemanticException {
        setBlockAsCurrent();
        for (StatementNode s : statementsTable){
            s.checkNode();
            if(s instanceof ExpressionNode && !(s instanceof AssignmentNode || s instanceof MethodCallNode || s instanceof ConstructorCallNode)) throw new NonValidStatementExpression(s);
            setBlockAsCurrent();
        }
    }

    private void setBlockAsCurrent() {
        Injector.getInjector().getSymbolTable().setCurrentBlock(this);
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
        if(getLocalVariable(declaredVariableName)!=null) throw new VariableAlreadyDeclaredInBlockException(declaredVariableName);
    }

    public void addStatement(StatementNode statement){
        statementsTable.add(statement);
    }

    protected abstract Type resolveNameExternal(Token name) throws SemanticException;

}
