package compiler.domain.abstractSyntaxTree;

import compiler.GenerationUtils;
import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.IntUnaryNotIntOperatorException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class IntUnaryOperatorExpressionNode extends UnaryOperatorExpressionNode { ;

    public IntUnaryOperatorExpressionNode(Token operator, BasicExpressionNode expression) {
        super(operator,expression);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        Type typeToReturn= new PrimitiveType(new Token("palabraReservadaint","int", operator.lineNumber()));
        Type type = expression.checkExpression();
        if(!type.compareType(typeToReturn)) {
            throw new IntUnaryNotIntOperatorException(operator,type.getTypeName());
        }
        return typeToReturn;
    }

    @Override
    public boolean isVoidMethodCall() {
        return false;
    }

    @Override
    public void generate() {
        if(GenerationUtils.getUnaryOperation(operator).equals("ADD")||GenerationUtils.getUnaryOperation(operator).equals("SUB")){
            Injector.getInjector().getSource().generate("PUSH 1");
        }
        super.generate();
    }
}
