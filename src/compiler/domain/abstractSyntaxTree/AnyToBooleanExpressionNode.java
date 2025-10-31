package compiler.domain.abstractSyntaxTree;

import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.ComparingNonConformingTypesException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class AnyToBooleanExpressionNode extends BinaryOperatorExpressionNode {

    public AnyToBooleanExpressionNode(Token operator, BasicExpressionNode leftExpression, ComposedExpressionNode rightExpression) {
        super(operator, leftExpression, rightExpression);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        PrimitiveType typeToReturn = new PrimitiveType(new Token("palabraReservadaboolean", "boolean", getOperator().lineNumber()));
        Type leftType = getLeftExpression().checkExpression();
        Type rightType = getRightExpression().checkExpression();
        if(!leftType.conformsTo(rightType)&&!rightType.conformsTo(leftType)) {
            SymbolTable symbolTable = Injector.getInjector().getSymbolTable();
            throw new ComparingNonConformingTypesException(getOperator(), symbolTable.getCurrentClass().getName(), symbolTable.getCurrentMethodOrConstructor().getName(),leftType.getTypeName(),rightType.getTypeName());
        }
        return typeToReturn;
    }

    @Override
    public boolean isCall() {
        return false;
    }

}
