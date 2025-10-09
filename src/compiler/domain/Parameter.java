package compiler.domain;

public class Parameter {
    private final Token name;
    private final Type type;

    public Parameter(Token name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Token getName() {
        return name;
    }
}
