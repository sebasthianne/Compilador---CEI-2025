package compiler.domain;


public class Variable {

    protected final Token name;
    protected final Type type;
    protected Integer offset;

    public Variable(Token name, Type type) {
        this.name = name;
        this.type=type;
        offset = null;
    }

    public Token getName() {
        return name;
    }

    public Type getType(){
        return type;
    }

    public boolean isOffsetCalculated(){
        return offset!=null;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
