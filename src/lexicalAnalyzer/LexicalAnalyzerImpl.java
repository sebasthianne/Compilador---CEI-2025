package lexicalAnalyzer;

import sourcemanager.SourceManager;

import java.io.IOException;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
    private String currentLexeme;
    private char currentCharacter;
    private SourceManager sourceManager;

    public LexicalAnalyzerImpl(SourceManager manager) throws IOException {
        sourceManager= manager;
        updateCurrentCharacter();
    }

    private void updateCurrentCharacter() throws IOException {
        currentCharacter= sourceManager.getNextChar();
    }

    private void updateCurrentLexeme() throws IOException {
        currentLexeme+= currentCharacter;
    }

    @Override
    public Token getNextToken() throws LexicalException, IOException {
        currentLexeme="";
        return null;
    }

    //TODO: Implement State Machine

}
