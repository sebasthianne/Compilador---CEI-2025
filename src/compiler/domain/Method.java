package compiler.domain;

import java.util.ArrayList;
import java.util.List;

public class Method extends Callable{
    private final Type returnType;

    public Method(Token name, Type returnType) {
        super(name);
        this.returnType=returnType;
    }


}
