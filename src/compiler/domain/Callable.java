package compiler.domain;

import compiler.GenerationUtils;
import compiler.domain.abstractSyntaxTree.CallableBodyBlockNode;
import compiler.semanticAnalyzer.semanticExceptions.ReusedParameterException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import injector.Injector;
import inout.sourcemanager.SourceManager;

import java.util.ArrayList;
import java.util.List;

public abstract class Callable {
    private final Token name;
    private final List<Parameter> parameterList;
    private int arity;
    private CallableBodyBlockNode body;
    private int variableCount;
    private int currentVariableOffset;
    protected int currentParameterOffset;
    protected Integer offset;


    public Callable(Token name) {
        this.name = name;
        parameterList= new ArrayList<>(50);
        arity=0;
        variableCount =0;
        currentVariableOffset=0;
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
            p.setOffset(getCurrentParameterOffset());
            currentParameterOffset++;
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
        variableCount++;
    }

    public void decrementCurrentVariableOffset(){
        currentVariableOffset--;
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

    public abstract int getCurrentParameterOffset();

    public void generate(){
        generateReturnAndLink();
        SourceManager source = Injector.getInjector().getSource();
        if(variableCount>0) source.generate("RMEM "+variableCount);
        body.generate();
        source.generate("FMEM "+variableCount);
        generateDefaultReturn();
    }

    private void generateDefaultReturn() {
        SourceManager source = Injector.getInjector().getSource();
        source.generate("STOREFP");
        source.generate(GenerationUtils.getReturnInstruction(this));
    }

    private void generateReturnAndLink() {
        SourceManager source = Injector.getInjector().getSource();
        source.generate(label()+": LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
    }

    protected abstract String label();

    public int getCurrentVariableOffset() {
        return currentVariableOffset;
    }


}
