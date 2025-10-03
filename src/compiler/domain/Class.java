package compiler.domain;

public class Class extends Callable{
    private final Token name;

    public Class(Token name) {
        this.name = name;
    }

    public Token getName() {
        return name;
    }
}
