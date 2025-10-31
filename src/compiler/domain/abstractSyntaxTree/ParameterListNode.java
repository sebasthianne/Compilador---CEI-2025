package compiler.domain.abstractSyntaxTree;

import compiler.domain.Callable;
import compiler.domain.Parameter;
import compiler.domain.Token;
import compiler.domain.Type;
import compiler.semanticAnalyzer.semanticExceptions.ActualParameterDoesNotConformException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParameterListNode extends ASTNode {
    private final List<ExpressionNode> parameterList;
    private final Token callableName;
    private List<Type> parameterTypeList;

    public ParameterListNode(Token callableName) {
        this.callableName = callableName;
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
            if(!currentActualParameterType.conformsTo(currentFormalParameter.getType())) {
                throw new ActualParameterDoesNotConformException(callableName);
            }
        }
    }

}
