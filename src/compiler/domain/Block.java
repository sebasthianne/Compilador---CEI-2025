package compiler.domain;


import java.util.HashMap;

public class Block {
    private final HashMap<String,LocalVariable> localVariablesTable;

    public Block() {
        this.localVariablesTable = new HashMap<>(47);
    }

    public void putLocalVariable(LocalVariable localVariable){
        localVariablesTable.put(localVariable.getName().lexeme(),localVariable);
    }

    public LocalVariable getLocalVariable(Token variableName){
        return localVariablesTable.get(variableName.lexeme());
    }
}
