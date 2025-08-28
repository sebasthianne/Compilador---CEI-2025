package injector;

import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.LexicalAnalyzerImpl;
import sourcemanager.SourceManager;
import sourcemanager.SourceManagerImpl;

import java.io.IOException;

public class Injector {

    private static Injector injector;

    private Injector(){}

    public static Injector getInjector(){
        if(injector==null){
            injector= new Injector();
        }
        return injector;
    }

    public LexicalAnalyzer getLexicalAnalyzer(SourceManager sourceManager) throws IOException {
        return new LexicalAnalyzerImpl(sourceManager);
    }

    public SourceManager getSource(){
        return new SourceManagerImpl();
    }
}
