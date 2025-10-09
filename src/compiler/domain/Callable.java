package compiler.domain;

public class Callable {
    private final Token name;

    public Callable(Token name) {
        this.name = name;
    }

    public Token getName() {
        return name;
    }
}
