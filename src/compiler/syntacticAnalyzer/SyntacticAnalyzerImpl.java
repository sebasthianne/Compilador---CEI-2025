package compiler.syntacticAnalyzer;

import compiler.domain.Token;
import compiler.lexicalAnalyzer.LexicalAnalyzer;
import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import compiler.syntacticAnalyzer.syntacticExceptions.MismatchException;
import compiler.syntacticAnalyzer.syntacticExceptions.UnexpectedSymbolInContextException;
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
            break;
            case "palabraReservadaabstract": match("palabraReservadaabstract");
            break;
            case "palabraReservadafinal": match("palabraReservadafinal");
            break;
            default: throw new UnexpectedSymbolInContextException("modificador",currentToken,"");
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
             else throw new UnexpectedSymbolInContextException("public, modificador, void o tipo",currentToken,"");
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
        } else throw new UnexpectedSymbolInContextException("modificador, void o tipo",currentToken,"");
    }

    private void attributeMethodEndNonTerminal() throws SyntacticException, LexicalException, IOException{
        if(currentToken.name().equals("puntoYComa")) attributeEndNonTerminal();
        else if (currentToken.name().equals("abreParéntesis")) methodEndNonTerminal();
            else throw new UnexpectedSymbolInContextException("; o (",currentToken,"Declaración de atributo o método");
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

    private void methodTypeNonTerminal() throws SyntacticException, LexicalException, IOException{
        if (currentToken.name().equals("palabraReservadavoid")) match("palabraReservadavoid");
        else if(isType(currentToken)) typeNonTerminal();
        else throw new UnexpectedSymbolInContextException("void o tipo",currentToken,"");
    }

    private void typeNonTerminal() throws SyntacticException, LexicalException, IOException {
        if(isPrimitiveType(currentToken)) primitiveTypeNonTerminal();
        else if (currentToken.name().equals("idClase")) match("idClase");
        else throw new UnexpectedSymbolInContextException("class o tipo primitivo",currentToken,"");
    }

    private void primitiveTypeNonTerminal() throws SyntacticException, LexicalException, IOException{
        switch (currentToken.name()){
            case "palabraReservadachar": match("palabraReservadachar");
            break;
            case "palabraReservadaboolean": match("palabraReservadaboolean");
            break;
            case "palabraReservadaint": match("palabraReservadaint");
            break;
            default: throw new UnexpectedSymbolInContextException("tipo primitivo",currentToken,"");
        }
    }

    private void formalArgumentsNonTerminal() throws SyntacticException, LexicalException, IOException{
        match("abreParéntesis");
        optionalFormalArgumentListNonTerminal();
        match("cierraParéntesis");
    }

    private void optionalFormalArgumentListNonTerminal() throws SyntacticException, LexicalException, IOException {
        if(isType(currentToken)) formalArgumentListNonTerminal();
    }

    private void formalArgumentListNonTerminal() throws SyntacticException, LexicalException, IOException {
        formalArgumentNonTerminal();
        moreArgumentsNonTerminal();
    }

    private void moreArgumentsNonTerminal() throws SyntacticException, LexicalException, IOException {
        if(currentToken.name().equals("coma")){
            match("coma");
            formalArgumentNonTerminal();
            moreArgumentsNonTerminal();
        }
    }

    private void formalArgumentNonTerminal() throws SyntacticException, LexicalException, IOException {
        typeNonTerminal();
        match("idMetVar");
    }

    private void optionalBlockNonTerminal() throws SyntacticException, LexicalException, IOException{
        if(currentToken.name().equals("abreLlave")){
            blockNonTerminal();
        }
    }

    private void blockNonTerminal() throws SyntacticException, LexicalException, IOException{
        match("abreLlave");
        statementListNonTerminal();
        match("cierraLlave");
    }

    private void statementListNonTerminal() throws SyntacticException, LexicalException, IOException{
        if(isStatementFirst(currentToken)){
            statementNonTerminal();
            statementListNonTerminal();
        }
    }

    private void statementNonTerminal() throws SyntacticException, LexicalException, IOException{
        if(isExpressionFirst(currentToken)) assignmentOrCallNonTerminal();
        else {
            switch (currentToken.name()){
                case "puntoYComa": match("puntoYComa");
                break;
                case "palabraReservadavar": localVariableNonTerminal();
                match("puntoYComa");
                break;
                case "palabraReservadareturn": returnNonTerminal();
                match("puntoYComa");
                break;
                case "palabraReservadaif": ifNonTerminal();
                break;
                case "palabraReservadawhile": whileNonTerminal();
                break;
                case "abreLlave": blockNonTerminal();
                break;
                default: throw new UnexpectedSymbolInContextException("while, if, return, var, {, ; o primero de operando",currentToken,"");
            }
        }
    }

    private void assignmentOrCallNonTerminal() throws SyntacticException, LexicalException, IOException{
        expressionNonTerminal();
    }

    private void localVariableNonTerminal() throws SyntacticException, LexicalException, IOException{
        match("palabraReservadavar");
        match("idMetVar");
        match("asignación");
        composedExpressionNonTerminal();
    }

    private void returnNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("palabraReservadareturn");
        optionalExpressionNonTerminal();
    }

    private void optionalExpressionNonTerminal() throws SyntacticException, LexicalException, IOException {
        if(isExpressionFirst(currentToken)) expressionNonTerminal();
    }

    private void ifNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("palabraReservadaif");
        match("abreParéntesis");
        expressionNonTerminal();
        match("cierraParéntesis");
        statementNonTerminal();
        optionalElseNonTerminal();
    }

    private void optionalElseNonTerminal() throws SyntacticException, LexicalException, IOException {
        if(currentToken.name().equals("palabraReservadaelse")) {
            match("palabraReservadaelse");
            statementNonTerminal();
        }
    }

    private void whileNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("palabraReservadawhile");
        match("abreParéntesis");
        expressionNonTerminal();
        match("cierraParéntesis");
        statementNonTerminal();
    }

    private void expressionNonTerminal() throws SyntacticException, LexicalException, IOException {
        composedExpressionNonTerminal();
        optionalAssignmentNonTerminal();
    }

    private void optionalAssignmentNonTerminal() throws SyntacticException, LexicalException, IOException {
        if(isAssignmentFirst(currentToken)){
            assignmentOperatorNonTerminal();
            composedExpressionNonTerminal();
        }
    }

    private void assignmentOperatorNonTerminal() throws SyntacticException, LexicalException, IOException {
        switch (currentToken.name()){
            case "asignación" : match("asignación");
            break;
            case "suma" : match("suma");
            match("asignación");
            break;
            case "resta" : match("resta");
            match("asignación");
            break;
            default: throw new UnexpectedSymbolInContextException("=, + o -",currentToken,"");
        }
    }

    private void composedExpressionNonTerminal() throws SyntacticException, LexicalException, IOException {
        basicExpressionNonTerminal();
        moreExpressionsNonTerminal();
    }

    private void moreExpressionsNonTerminal() throws SyntacticException, LexicalException, IOException {
        if(isBinaryOperator(currentToken)){
            binaryOperatorNonTerminal();
            basicExpressionNonTerminal();
            moreExpressionsNonTerminal();
        }
    }

    private void binaryOperatorNonTerminal() throws SyntacticException, LexicalException, IOException {
        switch (currentToken.name()){
            case "or": match("or");
            break;
            case "and": match("and");
            break;
            case "igual": match("igual");
            break;
            case "desigual": match("desigual");
            break;
            case "menor": match("menor");
            break;
            case "mayor": match("mayor");
            break;
            case "menorOIgual": match("menorOIgual");
            break;
            case "mayorOIgual": match("mayorOIgual");
            break;
            case "suma": match("suma");
            break;
            case "resta": match("resta");
            break;
            case "por": match("por");
            break;
            case "dividido": match("dividido");
            break;
            case "modulo": match("modulo");
            break;
            default: throw new UnexpectedSymbolInContextException("operador binario",currentToken,"");
        }
    }

    private void basicExpressionNonTerminal() throws SyntacticException, LexicalException, IOException {
        if(isUnaryOperator(currentToken)){
            unaryOperatorNonTerminal();
            operandNonTerminal();
        } else if (isOperandFirst(currentToken)) {
            operandNonTerminal();
        } else throw new UnexpectedSymbolInContextException("operador unario o primero de operando",currentToken,"");
    }

    private void unaryOperatorNonTerminal() throws SyntacticException, LexicalException, IOException {
        switch (currentToken.name()){
            case "suma": match("suma");
            break;
            case "incremento": match("incremento");
            break;
            case "resta": match("resta");
            break;
            case "decremento": match("decremento");
            break;
            case "not": match("not");
            break;
            default: throw new UnexpectedSymbolInContextException("+, ++, -, -- o !",currentToken,"");
        }
    }

    private void operandNonTerminal() throws SyntacticException, LexicalException, IOException {
        if(isPrimitiveLiteral(currentToken)) primitiveNonTerminal();
        else if (isReferencePrimaryFirst(currentToken)) referenceNonTerminal();
        else throw new UnexpectedSymbolInContextException("tipo primitivo o primero de referencia",currentToken,"");
    }

    private void primitiveNonTerminal() throws SyntacticException, LexicalException, IOException {
        switch (currentToken.name()){
            case "charLiteral": match("charLiteral");
            break;
            case "intLiteral": match("intLiteral");
            break;
            case "palabraReservadanull": match("palabraReservadanull");
            break;
            case "palabraReservadatrue": match("palabraReservadatrue");
            break;
            case "palabraReservadafalse": match("palabraReservadafalse");
            break;
            default: throw new UnexpectedSymbolInContextException("true, false, null, literal entero, literal caracter",currentToken,"");
        }
    }

    private void referenceNonTerminal() throws SyntacticException, LexicalException, IOException {
    }


    //TODO: Finnish grammar implementation

}
