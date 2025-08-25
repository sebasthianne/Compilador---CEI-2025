import lexicalAnalyzer.LexicalAnalyzer;
import lexicalAnalyzer.LexicalAnalyzerImpl;
import sourcemanager.SourceManager;

import java.io.IOException;

public class Injector {
    public LexicalAnalyzer getLexicalAnalyzer(SourceManager sourceManager) throws IOException {
        return new LexicalAnalyzerImpl(sourceManager);
    }
}
