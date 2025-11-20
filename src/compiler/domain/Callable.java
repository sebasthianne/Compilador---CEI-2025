package compiler.domain;

import compiler.domain.abstractSyntaxTree.CallableBodyBlockNode;
import compiler.semanticAnalyzer.semanticExceptions.ReusedParameterException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;

import java.util.ArrayList;
import java.util.List;

public class Callable {
    private final Token name;
    private final List<Parameter> parameterList;
    private int arity;
    private CallableBodyBlockNode body;
    private int parameterVariableCount;
    private int currentVariableOffset;
    protected Integer offset;


    public Callable(Token name) {
        this.name = name;
        parameterList= new ArrayList<>(50);
        arity=0;
        parameterVariableCount=0;
        currentVariableOffset=4;
        offset = null;
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
            p.setOffset(currentVariableOffset);
            currentVariableOffset++;
            parameterVariableCount++;
        }
        parameterList.add(parameter);
        arity++;
    }

    public int getArity() {
        return arity;
    }

    public void checkBody() throws SemanticException{
        Injector.getInjector().getSymbolTable().setCurrentMethodOrConstructor(this);
        body.checkNode();
    }

    public void setBody(CallableBodyBlockNode body) {
        this.body = body;
    }

    public boolean isEmptyBody(){
        return body == null;
    }

    public void incrementVariableCount(){
        parameterVariableCount++;
    }

    public void incrementCurrentVariableOffset(){
        currentVariableOffset++;
    }

    public boolean isOffsetCalculated(){
        return offset!=null;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
