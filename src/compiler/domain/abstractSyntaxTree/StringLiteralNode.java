package compiler.domain.abstractSyntaxTree;

import compiler.domain.Literal;
import compiler.domain.ReferenceType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.SymbolTable;
import injector.Injector;
import input.sourcemanager.SourceManager;

public class StringLiteralNode extends PrimaryNode {
    public final Literal stringLiteral;

    public StringLiteralNode(Token stringLiteral) {
        this.stringLiteral = new Literal(new ReferenceType(new Token("idClase","String",-1)),stringLiteral);
    }

    @Override
    public Type checkExpressionWithoutReference(){
        return stringLiteral.getType();
    }

    @Override
    public boolean isAssignableWithoutReference() {
        return false;
    }

    @Override
    public boolean isCallWithoutReference() {
        return false;
    }

    @Override
    public void generate() {
        SymbolTable symbolTable = Injector.getInjector().getSymbolTable();
        String stringName = "string" + symbolTable.getStringCounter();
        symbolTable.incrementStringCounter();
        SourceManager.generate(".DATA");
        SourceManager.generate(stringName + ": DW \"" + stringLiteral.getLiteralValue().lexeme() + "\", 0");
        SourceManager.generate(".CODE");
        SourceManager.generate("PUSH "+stringName);
    }

}
