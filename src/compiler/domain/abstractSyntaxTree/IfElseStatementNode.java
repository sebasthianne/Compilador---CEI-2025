package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;
import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;
import inout.sourcemanager.SourceManager;

public class IfElseStatementNode extends IfStatementNode {
    private final StatementNode elseBody;

    public IfElseStatementNode(ExpressionNode ifCheckExpression, StatementNode ifBody, Token ifToken, StatementNode elseBody) {
        super(ifCheckExpression,ifBody,ifToken);
        this.elseBody = elseBody;
    }

    @Override
    public void checkNode() throws SemanticException {
        super.checkNode();
        elseBody.setInExpression(false);
        elseBody.checkNode();
    }

    @Override
    public void generate() {
        ifCheckExpression.generate();
        SourceManager source = Injector.getInjector().getSource();
        SymbolTable symbolTable = Injector.getInjector().getSymbolTable();
        source.generate("BF else"+ symbolTable.getIfStatementCounter());
        ifBody.generate();
        source.generate("JUMP endIF"+ symbolTable.getIfStatementCounter());
        source.generate("else"+ symbolTable.getIfStatementCounter()+":");
        elseBody.generate();
        source.generate("endIF"+symbolTable.getIfStatementCounter()+":");
        symbolTable.incrementIfStatementCount();
    }
}
