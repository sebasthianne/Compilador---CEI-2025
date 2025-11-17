package compiler.domain.abstractSyntaxTree;

import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public abstract class ASTNode {
    public abstract void checkNode() throws SemanticException;

    public void generate(){}
}
