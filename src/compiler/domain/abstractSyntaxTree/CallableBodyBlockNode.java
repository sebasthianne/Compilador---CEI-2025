package compiler.domain.abstractSyntaxTree;

import compiler.domain.*;
import compiler.domain.Class;
import compiler.semanticAnalyzer.semanticExceptions.AttributeCouldNotBeResolvedException;
import compiler.semanticAnalyzer.semanticExceptions.DeclaredVariableSharesNameWithParameterException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

public class CallableBodyBlockNode extends BlockNode {
    @Override
    public Variable resolveNameExternal(Token name) throws SemanticException {
        Callable currentMethodOrConstructor = Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor();
        Parameter parameter = currentMethodOrConstructor.getParameter(name);
        if(parameter != null) return parameter;
        else {
            if(currentMethodOrConstructor instanceof Method method && method.isStatic()) throw new AttributeCouldNotBeResolvedException(name);
            Class currentClass = Injector.getInjector().getSymbolTable().getCurrentClass();
            return currentClass.resolveAttribute(name);
        }
    }

    @Override
    public void declarationChecks(Token declaredVariableName) throws SemanticException {
        super.declarationChecks(declaredVariableName);
        Callable currentMethodOrConstructor = Injector.getInjector().getSymbolTable().getCurrentMethodOrConstructor();
        if(currentMethodOrConstructor.getParameter(declaredVariableName)!=null) throw new DeclaredVariableSharesNameWithParameterException(declaredVariableName,Injector.getInjector().getSymbolTable().getCurrentClass().getName(),currentMethodOrConstructor.getName());
    }

}
