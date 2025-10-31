package compiler.domain.abstractSyntaxTree;

import compiler.domain.NullType;
import compiler.domain.PrimitiveType;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.ChainedToNullException;
import compiler.semanticAnalyzer.semanticExceptions.ChainedToPrimitiveException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public abstract class ChainedReferenceNode extends ReferenceNode {
    private Token pointToken;

    protected void checkTypeChainable(Type chainedTo) throws SemanticException {
        chainedTo.checkType();
        if(chainedTo instanceof PrimitiveType) {
            throw new ChainedToPrimitiveException(pointToken,chainedTo.getTypeName());
        }
        if(chainedTo instanceof NullType) throw new ChainedToNullException(pointToken);
    }

    @Override
    public Type checkExpression() throws SemanticException {
        throw new RuntimeException("Excepción de Prueba, encadenado requiere referencia. No debería ocurrir durante la ejecución normal del programa.");
    }

    public abstract Type checkChainedReference(Type chainedTo) throws SemanticException;

    public void setPointToken(Token pointToken) {
        this.pointToken = pointToken;
    }

}
