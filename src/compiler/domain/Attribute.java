package compiler.domain;

public class Attribute {
    private final Token name;
    private final Type type;

    public Attribute(Token name, Type type) {
        this.name = name;
        this.type=type;
    }

    public Token getName() {
        return name;
    }
}
