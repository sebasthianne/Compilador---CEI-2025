package compiler.domain.abstractSyntaxTree;

import compiler.domain.Callable;
import compiler.domain.Constructor;
import compiler.domain.Parameter;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParameterListNode extends ASTNode {
    private final List<Parameter> parameterList;

    public ParameterListNode() {
        this.parameterList = new ArrayList<>();
    }


    public void addParameter(Parameter p){
        parameterList.add(p);
    }

    public int size() {
        return parameterList.size();
    }

    @Override
    public void checkNode() throws SemanticException {
        for(Parameter p : parameterList){
            p.checkParameter();
        }
    }

    public void checkParameterMatch(Callable methodOrConstructor) throws SemanticException {
        Iterator<Parameter> formalParameters = methodOrConstructor.getParameterList().iterator();
        Iterator<Parameter> actualParameters = parameterList.iterator();
        while(formalParameters.hasNext() && actualParameters.hasNext()){
            Parameter currentFormalParameter = formalParameters.next();
            Parameter currentActualParameter = actualParameters.next();
            if(!currentFormalParameter.getType().compareType(currentActualParameter.getType())) throw new SemanticException(currentActualParameter.getName()) {
                @Override
                public String getDetailedErrorMessage() {
                    return "";
                }
            };
        }
    }

    //TODO: Complete rewrite after I solve name resolution
}
