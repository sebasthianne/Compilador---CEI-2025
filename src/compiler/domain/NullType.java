package compiler.domain;

import compiler.semanticAnalyzer.semanticExceptions.SemanticException;

public class NullType extends ReferenceType {
    public NullType(Token typeName) {
        super(typeName);
        if(!typeName.name().equals("palabraReservadanull")) throw new RuntimeException("Excepción de control: tipo nulo asignado con token otro que null. No debería ocurrir en ningún caso durante la ejecución normal del compilador.");
    }

    @Override
    public boolean conformsTo(Type type) {
        return type instanceof ReferenceType;
    }

    @Override
    public void checkType() throws SemanticException {
    }
}
