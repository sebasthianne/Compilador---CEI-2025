package compiler.domain;

import compiler.GenerationUtils;
import compiler.semanticAnalyzer.semanticExceptions.AbstractClassHasConstructorException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;


public class Constructor extends Callable{

    public Constructor(Token name) {
        super(name);
    }

    public void checkConstructor() throws SemanticException {
        Class currentClass = Injector.getInjector().getSymbolTable().getCurrentClass();
        if(currentClass.isAbstract()) throw new AbstractClassHasConstructorException(getName(),currentClass.getName());
        for(Parameter p:getParameterList()){
            p.checkParameter();
        }
    }

    @Override
    public int getCurrentParameterOffset() {
        return currentParameterOffset+4;
    }

    @Override
    protected String label() {
        return GenerationUtils.getConstructorLabel(this);
    }
}
