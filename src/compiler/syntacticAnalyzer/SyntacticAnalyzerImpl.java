package compiler.syntacticAnalyzer;

import compiler.domain.Token;
import compiler.lexicalAnalyzer.LexicalAnalyzer;
import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import compiler.syntacticAnalyzer.syntacticExceptions.InvalidAttributeOrMethodDeclarationException;
import compiler.syntacticAnalyzer.syntacticExceptions.MismatchException;
import compiler.syntacticAnalyzer.syntacticExceptions.NeverHappensException;
import compiler.syntacticAnalyzer.syntacticExceptions.SyntacticException;
import static compiler.syntacticAnalyzer.SyntacticUtils.*;

import java.io.IOException;



public class SyntacticAnalyzerImpl implements SyntacticAnalyzer {
    Token currentToken;
    LexicalAnalyzer lexicalAnalyzer;

    public SyntacticAnalyzerImpl(LexicalAnalyzer aLEX){
        lexicalAnalyzer=aLEX;
    }

    public void match(String tokenName) throws SyntacticException, LexicalException, IOException {
        if(tokenName.equals(currentToken.name())){
            currentToken=lexicalAnalyzer.getNextToken();
        } else throw new MismatchException(tokenName,currentToken);
    }

    @Override
    public void performAnalysis() throws SyntacticException, LexicalException, IOException {
        currentToken=lexicalAnalyzer.getNextToken();
        initialNonTerminal();
    }

    private void initialNonTerminal() throws SyntacticException, LexicalException, IOException{
        classListNonTerminal();
        match("endOfFile");
    }

    private void classListNonTerminal() throws SyntacticException, LexicalException, IOException{
        if(currentToken.name().equals("palabraReservadaclass")||isModifier(currentToken)) {
            classNonTerminal();
            classListNonTerminal();
        }
    }

    private void classNonTerminal() throws SyntacticException, LexicalException, IOException{
        optionalModifierNonTerminal();
        match("palabraReservadaclass");
        match("idClase");
        optionalInheritanceNonTerminal();
        match("abreLlave");
        memberListNonTerminal();
        match("cierraLlave");
    }

    private void optionalModifierNonTerminal() throws SyntacticException, LexicalException, IOException{
        if(isModifier(currentToken)){
            modifierNonTerminal();
        }
    }

    private void modifierNonTerminal() throws SyntacticException, LexicalException, IOException{
        switch (currentToken.name()){
            case "palabraReservadastatic": match("palabraReservadastatic");
            case "palabraReservadaabstract": match("palabraReservadaabstract");
            case "palabraReservadafinal": match("palabraReservadafinal");
            default: throw new NeverHappensException("N/A",currentToken);
        }
    }

    private void optionalInheritanceNonTerminal() throws SyntacticException, LexicalException, IOException{
        if(currentToken.name().equals("palabraReservadaextends")){
            match("palabraReservadaextends");
            match("idClase");
        }
    }

    private void memberListNonTerminal() throws SyntacticException, LexicalException, IOException{
        if(isMemberFirst(currentToken)){
            memberNonTerminal();
            memberListNonTerminal();
        }
    }

    private void memberNonTerminal() throws SyntacticException, LexicalException, IOException{
        if(isMethodOrAttributeFirst(currentToken)) attributeOrMethodNonTerminal();
        else if (currentToken.name().equals("palabraReservadapublic")) constructorNonTerminal();
             else throw new NeverHappensException("N/A",currentToken);
    }

    private void attributeOrMethodNonTerminal() throws SyntacticException, LexicalException, IOException {
        if(isModifier(currentToken)){
            modifierNonTerminal();
            methodTypeNonTerminal();
            match("idMetVar");
            methodEndNonTerminal();
        } else if (currentToken.name().equals("palabraReservadavoid")) {
            match("palabraReservadavoid");
            match("idMetVar");
            methodEndNonTerminal();
        } else if (isType(currentToken)) {
            typeNonTerminal();
            match("idMetVar");
            attributeMethodEndNonTerminal();
        } else throw new NeverHappensException("N/A",currentToken);
    }
    // TODO: Fix mistake, modifier can also precede void (fix in grammar)

    private void attributeMethodEndNonTerminal() throws SyntacticException, LexicalException, IOException{
        if(currentToken.name().equals("puntoYComa")) attributeEndNonTerminal();
        else if (currentToken.name().equals("abreParéntesis")) methodEndNonTerminal();
            else throw new InvalidAttributeOrMethodDeclarationException("; o (",currentToken);
    }

    private void attributeEndNonTerminal() throws SyntacticException, LexicalException, IOException{
        match("puntoYComa");
    }

    private void methodEndNonTerminal() throws SyntacticException, LexicalException, IOException{
        formalArgumentsNonTerminal();
        optionalBlockNonTerminal();
    }

    private void constructorNonTerminal() throws SyntacticException, LexicalException, IOException{
        match("palabraReservadapublic");
        match("idClase");
        formalArgumentsNonTerminal();
        blockNonTerminal();
    }

    //TODO: Finnish grammar implementation

}
