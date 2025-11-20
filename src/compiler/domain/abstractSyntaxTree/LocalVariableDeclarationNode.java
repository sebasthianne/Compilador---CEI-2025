package compiler.domain.abstractSyntaxTree;

import compiler.domain.*;
import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.DeclaredVariableIsNullTypeException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

import javax.lang.model.type.NullType;

public class LocalVariableDeclarationNode extends StatementNode {
    private final Token declaredVariableName;
    private final ExpressionNode assignedExpression;
    private final Token assignmentToken;
    private LocalVariable localVariable;

    public LocalVariableDeclarationNode(Token declaredVariableName, ExpressionNode assignedExpression, Token assignmentToken) {
        this.declaredVariableName = declaredVariableName;
        this.assignedExpression = assignedExpression;
        this.assignmentToken = assignmentToken;
    }

    @Override
    public void checkNode() throws SemanticException {
        SymbolTable symbolTable = Injector.getInjector().getSymbolTable();
        BlockNode currentBlock = symbolTable.getCurrentBlock();
        Type expressionType = assignedExpression.checkExpression();
        if(expressionType instanceof NullType) throw new DeclaredVariableIsNullTypeException(assignmentToken);
        currentBlock.declarationChecks(declaredVariableName);
        localVariable = new LocalVariable(declaredVariableName, expressionType);
        Callable currentMethodOrConstructor = symbolTable.getCurrentMethodOrConstructor();
        localVariable.setOffset(currentMethodOrConstructor.getCurrentVariableOffset());
        currentMethodOrConstructor.decrementCurrentVariableOffset();
        currentBlock.putLocalVariable(localVariable);
        currentMethodOrConstructor.incrementVariableCount();
    }

    @Override
    public void generate() {
        assignedExpression.generate();
        Injector.getInjector().getSource().generate("STORE "+localVariable.getOffset());
    }
}
