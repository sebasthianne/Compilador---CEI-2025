package compiler.domain;

public class Class extends Callable{
    private final Token name;
    private Token inheritsFrom;
    private final Token modifier;

    public Class(Token name, Token modifier) {
        this.name = name;
        this.modifier=modifier;
        this.inheritsFrom=null;
    }

    public Token getName() {
        return name;
    }

    public void setInheritsFrom(Token inheritsFrom) {
        this.inheritsFrom = inheritsFrom;
    }

    public Token getInheritsFrom() {
        return inheritsFrom;
    }
}
