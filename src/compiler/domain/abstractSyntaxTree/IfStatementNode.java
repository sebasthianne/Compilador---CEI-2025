package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.IfCheckNotBooleanException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;
import inout.sourcemanager.SourceManager;

public class IfStatementNode extends StatementNode {
    protected final ExpressionNode ifCheckExpression;
    protected final StatementNode ifBody;
    protected final Token ifToken;

    public IfStatementNode(ExpressionNode ifCheckExpression, StatementNode ifBody, Token ifToken) {
        this.ifCheckExpression = ifCheckExpression;
        this.ifBody = ifBody;
        this.ifToken = ifToken;
    }

    @Override
    public void checkNode() throws SemanticException {
        ifBody.setInExpression(false);
        Type ifCheckExpressionReturnType = ifCheckExpression.checkExpression();
        if(!ifCheckExpressionReturnType.isBoolean()) throw new IfCheckNotBooleanException(ifToken,ifCheckExpressionReturnType);
        else ifBody.checkNode();
    }

    @Override
    public void generate() {
        ifCheckExpression.generate();
        SourceManager source = Injector.getInjector().getSource();
        SymbolTable symbolTable = Injector.getInjector().getSymbolTable();
        source.generate("BF endIF"+ symbolTable.getIfStatementCounter());
        ifBody.generate();
        source.generate("endIF"+symbolTable.getIfStatementCounter()+":");
        symbolTable.incrementIfStatementCount();
    }
}
