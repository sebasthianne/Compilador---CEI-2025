package compiler.domain.abstractSyntaxTree;

import compiler.domain.Callable;
import compiler.domain.Class;
import compiler.domain.Parameter;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class CallableBodyBlockNode extends BlockNode {
    @Override
    public Type resolveNameExternal(Token name) throws SemanticException {
        Callable currentMethodOrConstructor = Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor();
        Parameter parameter = currentMethodOrConstructor.getParameter(name);
        if(parameter != null) return parameter.getType();
        else {
            Class currentClass = Injector.getInjector().getSymbolTable().getCurrentClass();
            return currentClass.resolveAttribute(name);
        }
    }

    @Override
    public void declarationChecks(Token declaredVariableName) throws SemanticException {
        super.declarationChecks(declaredVariableName);
        Callable currentMethodOrConstructor = Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor();
        if(currentMethodOrConstructor.getParameter(declaredVariableName)!=null) throw new SemanticException(declaredVariableName) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
    }
}
