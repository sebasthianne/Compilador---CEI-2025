package compiler.domain.abstractSyntaxTree;

import compiler.domain.Literal;
import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.SymbolTable;
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
    public void generate() {
        SymbolTable symbolTable = Injector.getInjector().getSymbolTable();
        String charName = "char" + symbolTable.getCharCounter();
        symbolTable.incrementCharCounter();
        SourceManager source = Injector.getInjector().getSource();
        source.generate(".DATA");
        source.generate(charName + ": DW '" + charLiteral.getLiteralValue().lexeme() + "'");
        source.generate(".CODE");
        source.generate("PUSH "+charName);
    }
}
