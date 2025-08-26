package lexicalClient;

import lexicalAnalyzer.LexicalAnalyzer;

public class LexicalClient {
    LexicalAnalyzer lexicalAnalyzer;

    public LexicalClient(){
        lexicalAnalyzer= null;
    }

    public void executeLexicalAnalysis(LexicalAnalyzer analyzer){
        lexicalAnalyzer= analyzer;
    }

    //TODO: Implement client for the lexical analyzer
}
