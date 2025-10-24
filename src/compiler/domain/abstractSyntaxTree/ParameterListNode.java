package compiler.domain.abstractSyntaxTree;

import compiler.domain.Callable;
import compiler.domain.Parameter;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParameterListNode extends ASTNode {
    private final List<ExpressionNode> parameterList;
    private List<Type> parameterTypeList;

    public ParameterListNode() {
        this.parameterList = new ArrayList<>();
    }


    public void addParameter(ExpressionNode expressionNode){
        parameterList.add(expressionNode);
    }

    public int size() {
        return parameterList.size();
    }

    @Override
    public void checkNode() throws SemanticException {
        parameterTypeList= new ArrayList<>();
        for(ExpressionNode expressionNode : parameterList){
            parameterTypeList.add(expressionNode.checkExpression());
        }
    }

    public void checkParameterMatch(Callable methodOrConstructor) throws SemanticException {
        Iterator<Parameter> formalParameters = methodOrConstructor.getParameterList().iterator();
        Iterator<Type> actualParametersTypes = parameterTypeList.iterator();
        while(formalParameters.hasNext() && actualParametersTypes.hasNext()){
            Parameter currentFormalParameter = formalParameters.next();
            Type currentActualParameterType = actualParametersTypes.next();
            if(!currentFormalParameter.getType().compareType(currentActualParameterType)) throw new SemanticException(currentActualParameterType.getTypeName()) {
                @Override
                public String getDetailedErrorMessage() {
                    return "";
                }
            };
        }
    }

    //TODO: Minor rewrite, fixed main issues
}
