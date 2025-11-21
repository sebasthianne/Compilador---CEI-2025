package compiler.domain.abstractSyntaxTree;

import compiler.domain.Literal;
import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;
import inout.sourcemanager.SourceManager;

public class CharLiteralNode extends PrimitiveLiteralNode {
    private final Literal charLiteral;

    public CharLiteralNode(Token charLiteralValue) {
        charLiteral = new Literal(new PrimitiveType(new Token("palabraReservadachar","char", charLiteralValue.lineNumber())),charLiteralValue);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        return charLiteral.getType();
    }

    @Override
    public boolean isCall() {
        return false;
    }

    @Override
    public boolean isVoidMethodCall() {
        return false;
    }

    @Override
    public void generate() {
        SourceManager source = Injector.getInjector().getSource();
        source.generate("PUSH '"+charLiteral.getLiteralValue().lexeme()+"'");
    }
}
