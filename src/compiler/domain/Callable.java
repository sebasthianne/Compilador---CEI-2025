package compiler.domain;

import java.util.ArrayList;
import java.util.List;

public class Callable {
    private final Token name;
    private final List<Parameter> parameterList;


    public Callable(Token name) {
        this.name = name;
        parameterList= new ArrayList<>(50);
    }

    public Token getName() {
        return name;
    }

    public Iterable<Parameter> getParameterList(){
        return parameterList;
    }

    public void addParameter(Parameter parameter){
        parameterList.add(parameter);
    }
}
