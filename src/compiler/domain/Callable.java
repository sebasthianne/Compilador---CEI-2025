package compiler.domain;

import compiler.semanticAnalyzer.semanticExceptions.ReusedParameterException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

import java.util.ArrayList;
import java.util.List;

public class Callable {
    private final Token name;
    private final List<Parameter> parameterList;
    private int arity;


    public Callable(Token name) {
        this.name = name;
        parameterList= new ArrayList<>(50);
        arity=0;
    }

    public Token getName() {
        return name;
    }

    public Iterable<Parameter> getParameterList(){
        return parameterList;
    }

    public Parameter getParameter(Token parameterName){
        for (Parameter p : parameterList){
            if(p.getName().lexeme().equals(parameterName.lexeme())) return p;
        }
        return null;
    }

    public void addParameter(Parameter parameter) throws SemanticException {
        for(Parameter p : parameterList){
            if(p.getName().lexeme().equals(parameter.getName().lexeme())) throw new ReusedParameterException(parameter.getName(), Injector.getInjector().getSymbolTable().getCurrentClass().getName(),name);
        }
        parameterList.add(parameter);
        arity++;
    }

    public int getArity() {
        return arity;
    }
}
