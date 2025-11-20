package compiler.domain;

import compiler.GenerationUtils;
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

    public Class getClassDeclaredIn() {
        return classDeclaredIn;
    }

    @Override
    public int getCurrentParameterOffset() {
        int baseParameterOffset = currentParameterOffset;
        if(isStatic()) return baseParameterOffset+3;
        else return baseParameterOffset+4;
    }

    @Override
    public void generate() {
        if(isAbstract()) Injector.getInjector().getSource().generate("NOP");
        else super.generate();
    }

    @Override
    protected String label() {
        return GenerationUtils.getMethodLabel(this);
    }
}
