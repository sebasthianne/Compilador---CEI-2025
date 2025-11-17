package compiler.domain;


public class PrimitiveType extends Type {
    public PrimitiveType(Token typeName) {
        super(typeName);
    }

    @Override
    public void checkType() {
    }

    @Override
    public boolean isBoolean() {
        return getTypeName().name().equals("palabraReservadaboolean");
    }

    @Override
    public boolean conformsTo(Type t) {
        return compareType(t);
    }


}
