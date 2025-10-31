package compiler.domain;

import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.AbstractMethodInConcreteClassException;
import compiler.semanticAnalyzer.semanticExceptions.AbstractMethodWithoutEmptyBodyException;
import compiler.semanticAnalyzer.semanticExceptions.ConcreteMethodWithNoBodyException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;


public class Method extends Callable{
    private final Type returnType;
    private final Token modifier;
    private boolean isStatementChecked;
    private Class classDeclaredIn;

    public Method(Token name, Token modifier, Type returnType) {
        super(name);
        this.returnType=returnType;
        this.modifier=modifier;
        isStatementChecked=false;
    }

    public boolean isAbstract(){
        return (modifier != null && modifier.name().equals("palabraReservadaabstract"));
    }

    public boolean isStatic(){
        return (modifier != null && modifier.name().equals("palabraReservadastatic"));
    }

    public boolean isFinal(){
        return (modifier != null && modifier.name().equals("palabraReservadafinal"));
    }


    public void checkMethod() throws SemanticException {
        Class currentClass = Injector.getInjector().getSymbolTable().getCurrentClass();
        if(isAbstract()) {
            if(!currentClass.isAbstract()) throw new AbstractMethodInConcreteClassException(getName(),currentClass.getName());
            if(!isEmptyBody()) throw new AbstractMethodWithoutEmptyBodyException(getName(),currentClass.getName());
        }
        if(!isAbstract()&&isEmptyBody()) throw new ConcreteMethodWithNoBodyException(getName(),currentClass.getName());
        if(returnType!=null) returnType.checkType();
        for(Parameter p:getParameterList()){
            p.checkParameter();
        }
    }

    @Override
    public void checkBody() throws SemanticException {
            Injector.getInjector().getSymbolTable().setCurrentClass(classDeclaredIn);
            super.checkBody();
            isStatementChecked = true;
    }

    public Type getReturnType() {
        return returnType;
    }

    public void setClassDeclaredIn(Class classDeclaredIn) {
        this.classDeclaredIn = classDeclaredIn;
    }

    public boolean isStatementChecked() {
        return isStatementChecked;
    }
}
