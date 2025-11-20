package compiler.semanticAnalyzer;

import compiler.GenerationUtils;
import compiler.domain.*;
import compiler.domain.Class;
import compiler.domain.abstractSyntaxTree.BlockNode;
import compiler.domain.abstractSyntaxTree.CallableBodyBlockNode;
import compiler.semanticAnalyzer.semanticExceptions.*;
import injector.Injector;
import inout.sourcemanager.SourceManager;

import java.util.HashMap;

public class SymbolTableImpl implements SymbolTable {
    private final HashMap<String,Class> classTable;
    private Class currentClass;
    private Callable currentMethodOrConstructor;
    private boolean currentCallableIsConstructor;
    private BlockNode currentBlock;
    private int stringCounter;
    private int charCounter;
    private Method mainMethod;

    public SymbolTableImpl(){
        classTable= new HashMap<>(9973);
        addPredefinedClasses();
        stringCounter = 0;
        charCounter = 0;
        mainMethod = null;
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
            getCurrentClass().setCodeGenerated(true);
            addMethod(new Method(new Token("idMetVar","read",-1),new Token("palabraReservadastatic","static",-1),new PrimitiveType(new Token("palabraReservadaint","int",-1))));
            getCurrentMethodOrConstructor().setBody(new CallableBodyBlockNode());
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printI",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","i",-1),new PrimitiveType(new Token("palabraReservadaint","int",-1))));
            getCurrentMethodOrConstructor().setBody(new CallableBodyBlockNode());
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printB",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","b",-1),new PrimitiveType(new Token("palabraReservadaboolean","boolean",-1))));
            getCurrentMethodOrConstructor().setBody(new CallableBodyBlockNode());
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printC",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","c",-1),new PrimitiveType(new Token("palabraReservadachar","char",-1))));
            getCurrentMethodOrConstructor().setBody(new CallableBodyBlockNode());
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printS",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","s",-1),new ReferenceType(new Token("idClase","String",-1))));
            getCurrentMethodOrConstructor().setBody(new CallableBodyBlockNode());
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","println",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().setBody(new CallableBodyBlockNode());
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printIln",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","i",-1),new PrimitiveType(new Token("palabraReservadaint","int",-1))));
            getCurrentMethodOrConstructor().setBody(new CallableBodyBlockNode());
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printBln",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","b",-1),new PrimitiveType(new Token("palabraReservadaboolean","boolean",-1))));
            getCurrentMethodOrConstructor().setBody(new CallableBodyBlockNode());
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printCln",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","c",-1),new PrimitiveType(new Token("palabraReservadachar","char",-1))));
            getCurrentMethodOrConstructor().setBody(new CallableBodyBlockNode());
            insertCurrentMethodOrConstructorInTable();
            addMethod(new Method(new Token("idMetVar","printSln",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","s",-1),new ReferenceType(new Token("idClase","String",-1))));
            getCurrentMethodOrConstructor().setBody(new CallableBodyBlockNode());
            insertCurrentMethodOrConstructorInTable();
        } catch (SemanticException e) {
            System.out.println("Está excepción nunca debería ocurrir acá");
        }
    }

    private void createString() {
        try {
            addClass(new Class(new Token("idClase","String",-1),null));
            getCurrentClass().setInheritsFrom(new Token("idClase","Object",-1));
            getCurrentClass().setCodeGenerated(true);
        } catch (SemanticException e) {
            System.out.println("Está excepción nunca debería ocurrir acá");
        }
    }

    private void createObject() {
        try {
            addClass(new Class(new Token("idClase","Object",-1),null));
            getCurrentClass().setCodeGenerated(true);
            addMethod(new Method(new Token("idMetVar","debugPrint",-1),new Token("palabraReservadastatic","static",-1),null));
            getCurrentMethodOrConstructor().addParameter(new Parameter(new Token("idMetVar","i",-1),new PrimitiveType(new Token("palabraReservadaint","int",-1))));
            getCurrentMethodOrConstructor().setBody(new CallableBodyBlockNode());
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
            Method currentMethod = (Method) currentMethodOrConstructor;
            if(currentClass.containsMethod(currentMethod)){
                throw new ReusedMethodInClassException(currentMethodOrConstructor.getName(),currentClass.getName(), currentMethodOrConstructor.getArity());
            }
            if(mainMethod==null&&currentMethod.isStatic()&&currentMethod.getReturnType()==null&&currentMethod.getArity()==0&&currentMethod.getName().lexeme().equals("main")){
                mainMethod = currentMethod;
            }
            currentMethod.setClassDeclaredIn(currentClass);
            currentClass.addMethod(currentMethod);
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
        if(constructor==null) throw new ConstructorNotFoundException(className);
        return constructor;
    }

    @Override
    public BlockNode getCurrentBlock() {
        return currentBlock;
    }

    @Override
    public void setCurrentBlock(BlockNode currentBlock) {
        this.currentBlock = currentBlock;
    }

    @Override
    public void setCurrentMethodOrConstructor(Callable methodOrConstructor) {
        currentMethodOrConstructor=methodOrConstructor;
    }

    @Override
    public void setCurrentClass(Class currentClass) {
        this.currentClass=currentClass;
    }

    @Override
    public void statementChecks() throws SemanticException {
        for(Class c : getTable()){
            c.statementChecks();
        }
    }

    @Override
    public void incrementStringCounter() {
        stringCounter++;
    }

    @Override
    public int getStringCounter() {
        return stringCounter;
    }

    @Override
    public void incrementCharCounter() {
        charCounter++;
    }

    @Override
    public int getCharCounter() {
        return charCounter;
    }

    @Override
    public void generate() {
        generateProgramStart();
        generateSimpleHeapInit();
        generateSimpleMalloc();

        for(Class c : getTable()){
            if(!c.isCodeGenerated()) c.generate();
        }

        generateObject();
        generateString();
        generateSystem();
    }

    private void generateProgramStart() {
        SourceManager source = Injector.getInjector().getSource();
        source.generate(".CODE");
        source.generate("PUSH simple_heap_init");
        source.generate("CALL");
        source.generate("PUSH "+ GenerationUtils.getMethodLabel(mainMethod));
        source.generate("CALL");
        source.generate("HALT");
    }

    private void generateSimpleMalloc() {
        SourceManager source = Injector.getInjector().getSource();
        source.generate("simple_malloc: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("LOADHL");
        source.generate("DUP");
        source.generate("PUSH 1");
        source.generate("ADD");
        source.generate("STORE 4");
        source.generate("LOAD 3");
        source.generate("STOREHL");
        source.generate("STOREFP");
        source.generate("RET 1");
    }

    private void generateSimpleHeapInit() {
        Injector.getInjector().getSource().generate("simple_heap_init: RET 0");
    }

    private void generateObject() {
        SourceManager source = Injector.getInjector().getSource();
        source.generate(".DATA");
        source.generate("lblVTObject: NOP");
        source.generate(".CODE");
        source.generate("lblConstructor$0@Object: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("FMEM 0");
        source.generate("STOREFP");
        source.generate("RET 1");
        source.generate("lblMetdebugPrint$1@Object: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("LOAD 3");
        source.generate("IPRINT");
        source.generate("STOREFP");
        source.generate("RET 1");
    }

    private void generateString() {
        SourceManager source = Injector.getInjector().getSource();
        source.generate(".DATA");
        source.generate("lblVTString: NOP");
        source.generate(".CODE");
        source.generate("lblConstructor$0@String: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("FMEM 0");
        source.generate("STOREFP");
        source.generate("RET 1");
    }

    private void generateSystem() {
        SourceManager source = Injector.getInjector().getSource();
        source.generate(".DATA");
        source.generate("lblVTSystem: NOP");
        source.generate(".CODE");
        source.generate("lblConstructor$0@System: LOADFP");
        source.generate( "LOADSP");
        source.generate("STOREFP");
        source.generate("FMEM 0");
        source.generate("STOREFP");
        source.generate("RET 1");
        source.generate("lblMetprintC$1@System: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("LOAD 3");
        source.generate("CPRINT");
        source.generate("STOREFP");
        source.generate("RET 1");
        source.generate("lblMetprintS$1@System: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("LOAD 3");
        source.generate("SPRINT");
        source.generate("STOREFP");
        source.generate("RET 1");
        source.generate("lblMetprintln$0@System: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("PRNLN");
        source.generate("STOREFP");
        source.generate("RET 0");
        source.generate("lblMetprintCln$1@System: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("LOAD 3");
        source.generate("CPRINT");
        source.generate("PRNLN");
        source.generate("STOREFP");
        source.generate("RET 1");
        source.generate("lblMetprintSln$1@System: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("LOAD 3");
        source.generate("SPRINT");
        source.generate("PRNLN");
        source.generate("STOREFP");
        source.generate("RET 1");
        source.generate("lblMetread$0@System: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("READ");
        source.generate("PUSH 48");
        source.generate("SUB");
        source.generate("STORE 3");
        source.generate("STOREFP");
        source.generate("RET 0");
        source.generate("lblMetprintB$1@System: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("LOAD 3");
        source.generate("BPRINT");
        source.generate("STOREFP");
        source.generate("RET 1");
        source.generate("lblMetprintIln$1@System: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("LOAD 3");
        source.generate("IPRINT");
        source.generate("PRNLN");
        source.generate("STOREFP");
        source.generate("RET 1");
        source.generate("lblMetprintI$1@System: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("LOAD 3");
        source.generate("IPRINT");
        source.generate("STOREFP");
        source.generate("RET 1");
        source.generate("lblMetprintBln$1@System: LOADFP");
        source.generate("LOADSP");
        source.generate("STOREFP");
        source.generate("LOAD 3");
        source.generate("BPRINT");
        source.generate("PRNLN");
        source.generate("STOREFP");
        source.generate("RET 1");
    }
}
