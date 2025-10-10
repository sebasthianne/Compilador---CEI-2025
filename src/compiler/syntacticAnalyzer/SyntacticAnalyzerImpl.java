package compiler.syntacticAnalyzer;

import compiler.domain.*;
import compiler.domain.Class;
import compiler.lexicalAnalyzer.LexicalAnalyzer;
import compiler.lexicalAnalyzer.lexicalExceptions.LexicalException;
import compiler.semanticAnalyzer.SymbolTable;
import compiler.semanticAnalyzer.semanticExceptions.ReusedAttributeNameInClassException;
import compiler.semanticAnalyzer.semanticExceptions.SemanticException;
import compiler.syntacticAnalyzer.syntacticExceptions.MismatchException;
import compiler.syntacticAnalyzer.syntacticExceptions.UnexpectedSymbolInContextException;
import compiler.syntacticAnalyzer.syntacticExceptions.SyntacticException;
import injector.Injector;

import static compiler.syntacticAnalyzer.SyntacticUtils.*;

import java.io.IOException;


public class SyntacticAnalyzerImpl implements SyntacticAnalyzer {
    private Token currentToken;
    private final LexicalAnalyzer lexicalAnalyzer;
    private final SymbolTable symbolTable;

    public SyntacticAnalyzerImpl(LexicalAnalyzer ALex) {
        lexicalAnalyzer = ALex;
        symbolTable= Injector.getInjector().getSymbolTable();
    }

    public void match(String tokenName) throws SyntacticException, LexicalException, IOException {
        if (tokenName.equals(currentToken.name())) {
            currentToken = lexicalAnalyzer.getNextToken();
        } else throw new MismatchException(tokenName, currentToken);
    }

    @Override
    public void performAnalysis() throws SyntacticException, LexicalException, IOException, SemanticException {
        currentToken = lexicalAnalyzer.getNextToken();
        initialNonTerminal();
    }

    private void initialNonTerminal() throws SyntacticException, LexicalException, IOException, SemanticException {
        classListNonTerminal();
        match("endOfFile");
    }

    private void classListNonTerminal() throws SyntacticException, LexicalException, IOException, SemanticException {
        if (currentToken.name().equals("palabraReservadaclass") || isModifier(currentToken)) {
            classNonTerminal();
            classListNonTerminal();
        }
    }

    private void classNonTerminal() throws SyntacticException, LexicalException, IOException, SemanticException {
        Token modifier=optionalModifierNonTerminal();
        match("palabraReservadaclass");
        symbolTable.addClass(new Class(currentToken,modifier));
        match("idClase");
        optionalInheritanceNonTerminal();
        if(symbolTable.getCurrentClass().getInheritsFrom()==null){
            symbolTable.getCurrentClass().setInheritsFrom(new Token("idClase","Object",-1));
        }
        match("abreLlave");
        memberListNonTerminal();
        match("cierraLlave");
    }

    private Token optionalModifierNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (isModifier(currentToken)) {
            return modifierNonTerminal();
        }
        return null;
    }

    private Token modifierNonTerminal() throws SyntacticException, LexicalException, IOException {
        Token modifier=currentToken;
        switch (currentToken.name()) {
            case "palabraReservadastatic":
                match("palabraReservadastatic");
                break;
            case "palabraReservadaabstract":
                match("palabraReservadaabstract");
                break;
            case "palabraReservadafinal":
                match("palabraReservadafinal");
                break;
            default:
                throw new UnexpectedSymbolInContextException("modificador", currentToken, "");
        }
        return modifier;
    }

    private void optionalInheritanceNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("palabraReservadaextends")) {
            match("palabraReservadaextends");
            symbolTable.getCurrentClass().setInheritsFrom(currentToken);
            match("idClase");
        }
    }

    private void memberListNonTerminal() throws SyntacticException, LexicalException, IOException, SemanticException {
        if (isMemberFirst(currentToken)) {
            memberNonTerminal();
            memberListNonTerminal();
        }
    }

    private void memberNonTerminal() throws SyntacticException, LexicalException, IOException, SemanticException {
        if (isMethodOrAttributeFirst(currentToken)) attributeOrMethodNonTerminal();
        else if (currentToken.name().equals("palabraReservadapublic")) constructorNonTerminal();
        else throw new UnexpectedSymbolInContextException("public, modificador, void o tipo", currentToken, "");
    }

    private void attributeOrMethodNonTerminal() throws SyntacticException, LexicalException, IOException, SemanticException {
        Token modifier= null;
        Type type = null;
        Token name = null;
        if (isModifier(currentToken)) {
            modifier = modifierNonTerminal();
            type = methodTypeNonTerminal();
            name = currentToken;
            match("idMetVar");
            methodEndNonTerminal(modifier, type, name);
        } else if (currentToken.name().equals("palabraReservadavoid")) {
            match("palabraReservadavoid");
            name= currentToken;
            match("idMetVar");
            methodEndNonTerminal(modifier, type, name);
        } else if (isType(currentToken)) {
            type = typeNonTerminal();
            name = currentToken;
            match("idMetVar");
            attributeMethodEndNonTerminal(type, name);
        } else throw new UnexpectedSymbolInContextException("modificador, void o tipo", currentToken, "");
    }

    private void attributeMethodEndNonTerminal(Type type, Token name) throws SyntacticException, LexicalException, IOException, SemanticException {
        if (currentToken.name().equals("puntoYComa")) attributeEndNonTerminal(type, name);
        else if (currentToken.name().equals("abreParéntesis")) methodEndNonTerminal(null, type, name);
        else
            throw new UnexpectedSymbolInContextException("; o (", currentToken, "Declaración de atributo o método, requiere lista de parámetros (caso método) o punto y coma (caso atributo)");
    }

    private void attributeEndNonTerminal(Type type, Token name) throws SyntacticException, LexicalException, IOException, SemanticException {
        if(symbolTable.getCurrentClass().getAttribute(name)!=null) throw new ReusedAttributeNameInClassException(name,symbolTable.getCurrentClass().getName());
        symbolTable.getCurrentClass().addAttribute(new Attribute(name,type));
        match("puntoYComa");
    }

    private void methodEndNonTerminal(Token modifier, Type type, Token name) throws SyntacticException, LexicalException, IOException, SemanticException {
        symbolTable.addMethod(new Method(name,modifier,type));
        formalArgumentsNonTerminal();
        optionalBlockNonTerminal();
        symbolTable.insertCurrentMethodOrConstructorInTable();
    }

    private void constructorNonTerminal() throws SyntacticException, LexicalException, IOException, SemanticException {
        match("palabraReservadapublic");
        symbolTable.addConstructor(new Constructor(currentToken));
        match("idClase");
        formalArgumentsNonTerminal();
        blockNonTerminal();
        symbolTable.insertCurrentMethodOrConstructorInTable();
    }

    private Type methodTypeNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("palabraReservadavoid")) {
            match("palabraReservadavoid");
            return null;
        }
        else if (isType(currentToken)) return typeNonTerminal();
        else throw new UnexpectedSymbolInContextException("void o tipo", currentToken, "");
    }

    private Type typeNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (isPrimitiveType(currentToken)) return primitiveTypeNonTerminal();
        else if (currentToken.name().equals("idClase")) return referenceTypeNonTerminal();
        else
            throw new UnexpectedSymbolInContextException("class o tipo primitivo", currentToken, "Dentro de la declaración de un atributo, método o parámetro formal, se espera un tipo");
    }

    private Type referenceTypeNonTerminal() throws SyntacticException, LexicalException, IOException {
        Type type = new ReferenceType(currentToken);
        match("idClase");
        return type;
    }

    private Type primitiveTypeNonTerminal() throws SyntacticException, LexicalException, IOException {
        Type type=new PrimitiveType(currentToken);
        switch (currentToken.name()) {
            case "palabraReservadachar":
                match("palabraReservadachar");
                break;
            case "palabraReservadaboolean":
                match("palabraReservadaboolean");
                break;
            case "palabraReservadaint":
                match("palabraReservadaint");
                break;
            default:
                throw new UnexpectedSymbolInContextException("tipo primitivo", currentToken, "");
        }
        return type;
    }

    private void formalArgumentsNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("abreParéntesis");
        optionalFormalArgumentListNonTerminal();
        match("cierraParéntesis");
    }

    private void optionalFormalArgumentListNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (isType(currentToken)) formalArgumentListNonTerminal();
    }

    private void formalArgumentListNonTerminal() throws SyntacticException, LexicalException, IOException {
        formalArgumentNonTerminal();
        moreArgumentsNonTerminal();
    }

    private void moreArgumentsNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("coma")) {
            match("coma");
            formalArgumentNonTerminal();
            moreArgumentsNonTerminal();
        }
    }

    private void formalArgumentNonTerminal() throws SyntacticException, LexicalException, IOException {
        Type type = typeNonTerminal();
        symbolTable.getCurrentMethodOrConstructor().addParameter(new Parameter(currentToken,type));
        match("idMetVar");
    }

    private void optionalBlockNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("abreLlave")) {
            blockNonTerminal();
        } else if (currentToken.name().equals("puntoYComa")) {
            match("puntoYComa");
        } else
            throw new UnexpectedSymbolInContextException("{ o ;", currentToken, "Un método requiere un bloque como cuerpo o punto y coma");
    }

    private void blockNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("abreLlave");
        statementListNonTerminal();
        match("cierraLlave");
    }

    private void statementListNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (isStatementFirst(currentToken)) {
            statementNonTerminal();
            statementListNonTerminal();
        }
    }

    private void statementNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (isExpressionFirst(currentToken)) {
            assignmentOrCallNonTerminal();
            match("puntoYComa");
        } else {
            switch (currentToken.name()) {
                case "puntoYComa":
                    match("puntoYComa");
                    break;
                case "palabraReservadavar":
                    localVariableNonTerminal();
                    match("puntoYComa");
                    break;
                case "palabraReservadareturn":
                    returnNonTerminal();
                    match("puntoYComa");
                    break;
                case "palabraReservadaif":
                    ifNonTerminal();
                    break;
                case "palabraReservadawhile":
                    whileNonTerminal();
                    break;
                case "abreLlave":
                    blockNonTerminal();
                    break;
                default:
                    throw new UnexpectedSymbolInContextException("while, if, return, var, {, ; o primero de operando", currentToken, "Dentro de un bloque o como cuerpo de una expresión while if o else se espera una sentencia");
            }
        }
    }

    private void assignmentOrCallNonTerminal() throws SyntacticException, LexicalException, IOException {
        expressionNonTerminal();
    }

    private void localVariableNonTerminal() throws SyntacticException, LexicalException, IOException {
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
        if (isExpressionFirst(currentToken)) expressionNonTerminal();
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
        if (currentToken.name().equals("palabraReservadaelse")) {
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
        if (isAssignmentFirst(currentToken)) {
            assignmentOperatorNonTerminal();
            composedExpressionNonTerminal();
        }
    }

    private void assignmentOperatorNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("asignación")) {
            match("asignación");
        } else throw new UnexpectedSymbolInContextException("=", currentToken, "");
    }

    private void composedExpressionNonTerminal() throws SyntacticException, LexicalException, IOException {
        basicExpressionNonTerminal();
        moreBasicExpressionsNonTerminal();
    }

    private void moreBasicExpressionsNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (isBinaryOperator(currentToken)) {
            binaryOperatorNonTerminal();
            basicExpressionNonTerminal();
            moreBasicExpressionsNonTerminal();
        }
    }

    private void binaryOperatorNonTerminal() throws SyntacticException, LexicalException, IOException {
        switch (currentToken.name()) {
            case "or":
                match("or");
                break;
            case "and":
                match("and");
                break;
            case "igual":
                match("igual");
                break;
            case "desigual":
                match("desigual");
                break;
            case "menor":
                match("menor");
                break;
            case "mayor":
                match("mayor");
                break;
            case "menorOIgual":
                match("menorOIgual");
                break;
            case "mayorOIgual":
                match("mayorOIgual");
                break;
            case "suma":
                match("suma");
                break;
            case "resta":
                match("resta");
                break;
            case "por":
                match("por");
                break;
            case "dividido":
                match("dividido");
                break;
            case "modulo":
                match("modulo");
                break;
            default:
                throw new UnexpectedSymbolInContextException("operador binario", currentToken, "");
        }
    }

    private void basicExpressionNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (isUnaryOperator(currentToken)) {
            unaryOperatorNonTerminal();
            operandNonTerminal();
        } else if (isOperandFirst(currentToken)) {
            operandNonTerminal();
        } else
            throw new UnexpectedSymbolInContextException("operador unario o primero de operando", currentToken, "Dentro de una declaración de variable, asignación, o expresión como sentencia, una expresión básica debe comenzar con un operador unario u operando");
    }

    private void unaryOperatorNonTerminal() throws SyntacticException, LexicalException, IOException {
        switch (currentToken.name()) {
            case "suma":
                match("suma");
                break;
            case "incremento":
                match("incremento");
                break;
            case "resta":
                match("resta");
                break;
            case "decremento":
                match("decremento");
                break;
            case "not":
                match("not");
                break;
            default:
                throw new UnexpectedSymbolInContextException("+, ++, -, -- o !", currentToken, "");
        }
    }

    private void operandNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (isPrimitiveLiteral(currentToken)) primitiveNonTerminal();
        else if (isReferencePrimaryFirst(currentToken)) referenceNonTerminal();
        else throw new UnexpectedSymbolInContextException("tipo primitivo o primero de referencia", currentToken, "");
    }

    private void primitiveNonTerminal() throws SyntacticException, LexicalException, IOException {
        switch (currentToken.name()) {
            case "charLiteral":
                match("charLiteral");
                break;
            case "intLiteral":
                match("intLiteral");
                break;
            case "palabraReservadanull":
                match("palabraReservadanull");
                break;
            case "palabraReservadatrue":
                match("palabraReservadatrue");
                break;
            case "palabraReservadafalse":
                match("palabraReservadafalse");
                break;
            default:
                throw new UnexpectedSymbolInContextException("true, false, null, literal entero, literal caracter", currentToken, "");
        }
    }

    private void referenceNonTerminal() throws SyntacticException, LexicalException, IOException {
        primaryNonTerminal();
        chainedReferenceNonTerminal();
    }

    private void chainedReferenceNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("punto")) {
            chainedVariableOrMethodNonTerminal();
            chainedReferenceNonTerminal();
        }
    }

    private void primaryNonTerminal() throws SyntacticException, LexicalException, IOException {
        switch (currentToken.name()) {
            case "palabraReservadathis":
                match("palabraReservadathis");
                break;
            case "stringLiteral":
                match("stringLiteral");
                break;
            case "idMetVar":
                variableAccessOrMethodCallNonTerminal();
                break;
            case "palabraReservadanew":
                constructorCallNonTerminal();
                break;
            case "idClase":
                staticMethodCallNonTerminal();
                break;
            case "abreParéntesis":
                parenthesizedExpressionNonTerminal();
                break;
            default:
                throw new UnexpectedSymbolInContextException("this, new, (, id de clase, id de método o variable, o literal string", currentToken, "");
        }
    }

    private void variableAccessOrMethodCallNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("idMetVar");
        optionalActualArgumentsNonTerminal();
    }

    private void constructorCallNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("palabraReservadanew");
        match("idClase");
        actualArgumentsNonTerminal();
    }

    private void parenthesizedExpressionNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("abreParéntesis");
        expressionNonTerminal();
        match("cierraParéntesis");
    }

    private void staticMethodCallNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("idClase");
        match("punto");
        match("idMetVar");
        actualArgumentsNonTerminal();
    }

    private void optionalActualArgumentsNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("abreParéntesis")) actualArgumentsNonTerminal();
    }

    private void actualArgumentsNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("abreParéntesis");
        optionalExpressionListNonTerminal();
        match("cierraParéntesis");
    }

    private void optionalExpressionListNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (isExpressionFirst(currentToken)) {
            expressionListNonTerminal();
        }
    }

    private void expressionListNonTerminal() throws SyntacticException, LexicalException, IOException {
        expressionNonTerminal();
        moreExpressionsNonTerminal();
    }

    private void moreExpressionsNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("coma")) {
            match("coma");
            expressionListNonTerminal();
        }
    }

    private void chainedVariableOrMethodNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("punto");
        match("idMetVar");
        optionalActualArgumentsNonTerminal();
    }

}
