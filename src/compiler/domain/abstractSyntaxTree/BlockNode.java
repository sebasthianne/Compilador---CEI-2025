package compiler.domain.abstractSyntaxTree;

import compiler.domain.LocalVariable;
import compiler.domain.Token;
import compiler.domain.Variable;
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
            s.setInExpression(false);
            s.checkNode();
            if(s instanceof ExpressionNode e && !(s instanceof AssignmentNode || e.isCall())) throw new NonValidStatementExpression(s);
            setBlockAsCurrent();
        }
    }

    private void setBlockAsCurrent() {
        Injector.getInjector().getSymbolTable().setCurrentBlock(this);
    }

    public Variable resolveName(Token name) throws SemanticException{
        LocalVariable localVariable = resolveNameLocal(name);
        if(localVariable==null) return resolveNameExternal(name);
        else return localVariable;
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

    protected abstract Variable resolveNameExternal(Token name) throws SemanticException;

    @Override
    public void generate() {
        for(StatementNode s:statementsTable){
            s.generate();
            if(s instanceof ExpressionNode e && !e.isVoidMethodCall()) Injector.getInjector().getSource().generate("POP");
        }
    }
}
