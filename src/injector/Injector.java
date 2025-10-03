package injector;

import compiler.lexicalAnalyzer.LexicalAnalyzer;
import compiler.lexicalAnalyzer.LexicalAnalyzerImpl;
import compiler.symbolTable.SymbolTable;
import compiler.symbolTable.SymbolTableImpl;
import compiler.syntacticAnalyzer.SyntacticAnalyzer;
import compiler.syntacticAnalyzer.SyntacticAnalyzerImpl;
import input.sourcemanager.SourceManager;
import input.sourcemanager.SourceManagerImpl;

import java.io.IOException;

public class Injector {

    private static Injector injector;

    private SymbolTable symbolTable;

    private Injector() {
    }

    public static Injector getInjector() {
        if (injector == null) {
            injector = new Injector();
        }
        return injector;
    }

    public SymbolTable getSymbolTable(){
        if(symbolTable==null){
            symbolTable=new SymbolTableImpl();
        }
        return symbolTable;
    }

    public LexicalAnalyzer getLexicalAnalyzer(SourceManager sourceManager) throws IOException {
        return new LexicalAnalyzerImpl(sourceManager);
    }

    public SyntacticAnalyzer getSyntacticAnalyzer(LexicalAnalyzer aLex) {
        return new SyntacticAnalyzerImpl(aLex);
    }

    public SourceManager getSource() {
        return new SourceManagerImpl();
    }

}
