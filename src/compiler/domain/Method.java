package compiler.domain;

import java.util.ArrayList;
import java.util.List;

public class Method extends Callable{
    private final Type returnType;
    private final Token modifier;

    public Method(Token name, Token modifier, Type returnType) {
        super(name);
        this.returnType=returnType;
        this.modifier=modifier;
    }


}
