package compiler.semanticAnalyzer;

import compiler.domain.*;
import compiler.domain.Class;
import compiler.semanticAnalyzer.semanticExceptions.*;

import java.util.HashMap;

public class SymbolTableImpl implements SymbolTable {
    private final HashMap<String,Class> classTable;
    private Class currentClass;
    private Callable currentMethodOrConstructor;
    private boolean currentCallableIsConstructor;
    private Block currentBlock;

    public SymbolTableImpl(){
        classTable= new HashMap<>(9973);
        addPredefinedClasses();
    }

    private void addPredefinedClasses() {
        createObject();
        createString();
        createSystem();
    }

    private void createSystem() {
        try {
            addClass(new Class(new Token("idClase","System",-1),null));
            getCurrentClass().setInheritsFrom(new Token("idClase","Object",-1));
            addMethod(new Method(new Token("idMetVar","read",-1),new Token("palabraReservadastatic","static",-1),new PrimitiveType(new Token("palabraReservadaint","int",-1))));
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printI",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","i",-1),new PrimitiveType(new Token("palabraReservadaint","int",-1))));
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printB",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","b",-1),new PrimitiveType(new Token("palabraReservadaboolean","boolean",-1))));
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printC",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","c",-1),new PrimitiveType(new Token("palabraReservadachar","char",-1))));
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printS",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","s",-1),new ReferenceType(new Token("idClase","String",-1))));
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","println",-1),new Token("palabraReservadastatic","static",-1),null));
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printIln",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","i",-1),new PrimitiveType(new Token("palabraReservadaint","int",-1))));
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printBln",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","b",-1),new PrimitiveType(new Token("palabraReservadaboolean","boolean",-1))));
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printCln",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","c",-1),new PrimitiveType(new Token("palabraReservadachar","char",-1))));
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printSln",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","s",-1),new ReferenceType(new Token("idClase","String",-1))));
            insertCurrentMethodOrConstructorInTable();
        } catch (SemanticException e) {
            System.out.println("Está excepción nunca debería ocurrir acá");
        }
    }

    private void createString() {
        try {
            addClass(new Class(new Token("idClase","String",-1),null));
            getCurrentClass().setInheritsFrom(new Token("idClase","Object",-1));
        } catch (SemanticException e) {
            System.out.println("Está excepción nunca debería ocurrir acá");
        }
    }

    private void createObject() {
        try {
            addClass(new Class(new Token("idClase","Object",-1),null));
            addMethod(new Method(new Token("idMetVar","debugPrint",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","i",-1),new PrimitiveType(new Token("palabraReservadaint","int",-1))));
            insertCurrentMethodOrConstructorInTable();
        } catch (SemanticException e) {
            System.out.println("Está excepción nunca debería ocurrir acá");
        }
    }

    @Override
    public void addClass(Class c) throws SemanticException {
        if(currentClass!=null){
            Class previousClassWithName = classTable.put(currentClass.getName().lexeme(),currentClass);
            if(previousClassWithName!=null) throw new ReusedClassNameException(currentClass.getName());
        }
        currentClass=c;
    }

    @Override
    public void addMethod(Method m) {
        currentMethodOrConstructor=m;
        currentCallableIsConstructor=false;
    }

    @Override
    public Class getCurrentClass() {
        return currentClass;
    }

    @Override
    public void addConstructor(Constructor c) {
        currentMethodOrConstructor=c;
        currentCallableIsConstructor=true;
    }

    @Override
    public Iterable<Class> getTable() {
        return classTable.values();
    }

    @Override
    public Class getClass(Token name) {
        return classTable.get(name.lexeme());
    }

    @Override
    public Callable getCurrentMethodOrConstructor() {
        return currentMethodOrConstructor;
    }

    @Override
    public void insertCurrentMethodOrConstructorInTable() throws SemanticException {
        if(currentCallableIsConstructor){
            Constructor currentConstructor=(Constructor) currentMethodOrConstructor;
            if(!currentMethodOrConstructor.getName().lexeme().equals(currentClass.getName().lexeme())) throw new ConstructorNameClassMismatchException(currentMethodOrConstructor.getName());
            if(currentClass.containsConstructor(currentConstructor)) throw new ReusedConstructorInClassException(currentMethodOrConstructor.getName(),currentClass.getName(),currentConstructor.getArity());
            currentClass.addConstructor(currentConstructor);
        } else {
            if(currentClass.containsMethod((Method) currentMethodOrConstructor)){
                throw new ReusedMethodInClassException(currentMethodOrConstructor.getName(),currentClass.getName(), currentMethodOrConstructor.getArity());
            }
            currentClass.addMethod((Method) currentMethodOrConstructor);
        }
    }

    @Override
    public void checkSymbolTable() throws SemanticException {
        for(Class c : this.getTable()){
            currentClass=c;
            currentClass.checkClass();
        }
    }

    @Override
    public void consolidate() throws SemanticException {
        for(Class c : this.getTable()){
            currentClass=c;
            currentClass.consolidate();
        }
    }

    @Override
    public Constructor resolveConstructor(Token className, int arity) throws SemanticException {
        Class constructorsClass = getClass(className);
        Constructor constructor = constructorsClass.getConstructor(arity);
        if(constructor==null) throw new SemanticException(className) {
            @Override
            public String getDetailedErrorMessage() {
                return "";
            }
        };
        return constructor;
    }

    @Override
    public Block getCurrentBlock() {
        return currentBlock;
    }

    @Override
    public void setCurrentBlock(Block currentBlock) {
        this.currentBlock = currentBlock;
    }
}
