package compiler.domain.abstractSyntaxTree;

import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.semanticAnalyzer.semanticExceptions.WhileConditionNotBooleanException;
import injector.Injector;
import inout.sourcemanager.SourceManager;

public class WhileStatementNode extends StatementNode {
    private final ExpressionNode whileConditionExpression;
    private final StatementNode whileBody;
    private final Token whileToken;

    public WhileStatementNode(ExpressionNode whileConditionExpression, StatementNode whileBody, Token whileToken) {
        this.whileConditionExpression = whileConditionExpression;
        this.whileBody = whileBody;
        this.whileToken = whileToken;
    }

    @Override
    public void checkNode() throws SemanticException {
        whileBody.setInExpression(false);
        Type ifCheckExpressionReturnType = whileConditionExpression.checkExpression();
        if(!ifCheckExpressionReturnType.isBoolean()) throw new WhileConditionNotBooleanException(whileToken,ifCheckExpressionReturnType);
        else whileBody.checkNode();
    }

    @Override
    public void generate() {
        SourceManager source = Injector.getInjector().getSource();
        SymbolTable symbolTable = Injector.getInjector().getSymbolTable();
        source.generate("beginWhile"+symbolTable.getWhileStatementCounter()+":");
        whileConditionExpression.generate();
        source.generate("BF endWhile"+symbolTable.getWhileStatementCounter());
        whileBody.generate();
        source.generate("JUMP beginWhile"+symbolTable.getWhileStatementCounter());
        source.generate("endWhile"+symbolTable.getWhileStatementCounter()+":");
        symbolTable.incrementWhileStatementCounter();
    }
}
