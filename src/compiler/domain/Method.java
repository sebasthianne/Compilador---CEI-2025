package compiler.domain;

import compiler.semanticAnalyzer.semanticExceptions.AbstractMethodInConcreteClassException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;


public class Method extends Callable{
    private final Type returnType;
    private final Token modifier;

    public Method(Token name, Token modifier, Type returnType) {
        super(name);
        this.returnType=returnType;
        this.modifier=modifier;
    }

    public boolean isAbstract(){
        return (modifier != null && modifier.name().equals("palabraReservadaabstract"));
    }


    public void checkMethod() throws SemanticException {
        Class currentClass = Injector.getInjector().getSymbolTable().getCurrentClass();
        if(isAbstract()&&!currentClass.isAbstract()) throw new AbstractMethodInConcreteClassException(getName(),currentClass.getName());
        if(returnType!=null) returnType.checkType();
        for(Parameter p:getParameterList()){
            p.checkParameter();
        }
    }
}
