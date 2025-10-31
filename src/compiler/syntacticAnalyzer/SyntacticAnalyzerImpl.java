package compiler.syntacticAnalyzer;

import compiler.domain.*;
import compiler.domain.Class;
import compiler.domain.abstractSyntaxTree.*;
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
        Token name;
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
        Constructor m = (Constructor) symbolTable.getCurrentMethodOrConstructor();
        CallableBodyBlockNode methodOrConstructorBody = new CallableBodyBlockNode();
        m.setBody(methodOrConstructorBody);
        Injector.getInjector().getSymbolTable().setCurrentBlock(methodOrConstructorBody);
        blockNonTerminal(methodOrConstructorBody);
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

    private void formalArgumentsNonTerminal() throws SyntacticException, LexicalException, IOException, SemanticException {
        match("abreParéntesis");
        optionalFormalArgumentListNonTerminal();
        match("cierraParéntesis");
    }

    private void optionalFormalArgumentListNonTerminal() throws SyntacticException, LexicalException, IOException, SemanticException {
        if (isType(currentToken)) formalArgumentListNonTerminal();
    }

    private void formalArgumentListNonTerminal() throws SyntacticException, LexicalException, IOException, SemanticException {
        formalArgumentNonTerminal();
        moreArgumentsNonTerminal();
    }

    private void moreArgumentsNonTerminal() throws SyntacticException, LexicalException, IOException, SemanticException {
        if (currentToken.name().equals("coma")) {
            match("coma");
            formalArgumentNonTerminal();
            moreArgumentsNonTerminal();
        }
    }

    private void formalArgumentNonTerminal() throws SyntacticException, LexicalException, IOException, SemanticException {
        Type type = typeNonTerminal();
        symbolTable.getCurrentMethodOrConstructor().addParameter(new Parameter(currentToken,type));
        match("idMetVar");
    }

    private void optionalBlockNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("abreLlave")) {
            Method m = (Method) symbolTable.getCurrentMethodOrConstructor();
            CallableBodyBlockNode methodOrConstructorBody = new CallableBodyBlockNode();
            m.setBody(methodOrConstructorBody);
            Injector.getInjector().getSymbolTable().setCurrentBlock(methodOrConstructorBody);
            blockNonTerminal(methodOrConstructorBody);
        } else if (currentToken.name().equals("puntoYComa")) {
            match("puntoYComa");
        } else
            throw new UnexpectedSymbolInContextException("{ o ;", currentToken, "Un método requiere un bloque como cuerpo o punto y coma");
    }

    private void blockNonTerminal(BlockNode block) throws SyntacticException, LexicalException, IOException {
        match("abreLlave");
        statementListNonTerminal(block);
        match("cierraLlave");
    }

    private void statementListNonTerminal(BlockNode block) throws SyntacticException, LexicalException, IOException {
        if (isStatementFirst(currentToken)) {
            StatementNode newStatement = statementNonTerminal();
            Injector.getInjector().getSymbolTable().setCurrentBlock(block);
            if(newStatement != null) block.addStatement(newStatement);
            statementListNonTerminal(block);
        }
    }

    private StatementNode statementNonTerminal() throws SyntacticException, LexicalException, IOException {
        StatementNode returnStatement=null;
        if (isExpressionFirst(currentToken)) {
            returnStatement = assignmentOrCallNonTerminal();
            returnStatement.setSemicolonToken(currentToken);
            match("puntoYComa");
        } else {
            switch (currentToken.name()) {
                case "puntoYComa":
                    match("puntoYComa");
                    break;
                case "palabraReservadavar":
                    returnStatement = localVariableNonTerminal();
                    returnStatement.setSemicolonToken(currentToken);
                    match("puntoYComa");
                    break;
                case "palabraReservadareturn":
                    returnStatement = returnNonTerminal();
                    returnStatement.setSemicolonToken(currentToken);
                    match("puntoYComa");
                    break;
                case "palabraReservadaif":
                    returnStatement = ifNonTerminal();
                    break;
                case "palabraReservadawhile":
                    returnStatement = whileNonTerminal();
                    break;
                case "abreLlave":
                    SymbolTable symbolTable = Injector.getInjector().getSymbolTable();
                    BlockNode parentBlock = symbolTable.getCurrentBlock();
                    NestedBlockNode newNestedBlock = new NestedBlockNode(parentBlock);
                    symbolTable.setCurrentBlock(newNestedBlock);
                    blockNonTerminal(newNestedBlock);
                    returnStatement =  symbolTable.getCurrentBlock();
                    break;
                default:
                    throw new UnexpectedSymbolInContextException("while, if, return, var, {, ; o primero de operando", currentToken, "Dentro de un bloque o como cuerpo de una expresión while if o else se espera una sentencia");
            }
        }
        return returnStatement;
    }

    private ExpressionNode assignmentOrCallNonTerminal() throws SyntacticException, LexicalException, IOException {
        return expressionNonTerminal();
    }

    private LocalVariableDeclarationNode localVariableNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("palabraReservadavar");
        Token variableName = currentToken;
        match("idMetVar");
        match("asignación");
        ExpressionNode assignedExpression= composedExpressionNonTerminal();
        return new LocalVariableDeclarationNode(variableName,assignedExpression);
    }

    private ReturnStatementNode returnNonTerminal() throws SyntacticException, LexicalException, IOException {
        ReturnStatementNode returnStatement = new ReturnStatementNode(currentToken);
        match("palabraReservadareturn");
        optionalExpressionNonTerminal(returnStatement);
        return returnStatement;
    }

    private void optionalExpressionNonTerminal(ReturnStatementNode returnStatement) throws SyntacticException, LexicalException, IOException {
        if (isExpressionFirst(currentToken)) returnStatement.setExpressionToReturn(expressionNonTerminal());
    }

    private IfStatementNode ifNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("palabraReservadaif");
        match("abreParéntesis");
        ExpressionNode checkExpression = expressionNonTerminal();
        match("cierraParéntesis");
        StatementNode ifBody = statementNonTerminal();
        return optionalElseNonTerminal(checkExpression, ifBody);
    }

    private IfStatementNode optionalElseNonTerminal(ExpressionNode checkExpression, StatementNode ifBody) throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("palabraReservadaelse")) {
            match("palabraReservadaelse");
            StatementNode elseBody = statementNonTerminal();
            return new IfElseStatementNode(checkExpression,ifBody,elseBody);
        }
        else return new IfStatementNode(checkExpression,ifBody);
    }

    private WhileStatementNode whileNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("palabraReservadawhile");
        match("abreParéntesis");
        ExpressionNode condition = expressionNonTerminal();
        match("cierraParéntesis");
        StatementNode body = statementNonTerminal();
        return new WhileStatementNode(condition,body);
    }

    private ExpressionNode expressionNonTerminal() throws SyntacticException, LexicalException, IOException {
        ComposedExpressionNode expression = composedExpressionNonTerminal();
        return optionalAssignmentNonTerminal(expression);
    }

    private ExpressionNode optionalAssignmentNonTerminal(ComposedExpressionNode leftExpression) throws SyntacticException, LexicalException, IOException {
        if (isAssignmentFirst(currentToken)) {
            Token assignmentToken = assignmentOperatorNonTerminal();
            ComposedExpressionNode rightExpression = composedExpressionNonTerminal();
            return new AssignmentNode(leftExpression,rightExpression,assignmentToken);
        }
        return leftExpression;
    }

    private Token assignmentOperatorNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("asignación")) {
            Token asignmentToken = currentToken;
            match("asignación");
            return asignmentToken;
        } else throw new UnexpectedSymbolInContextException("=", currentToken, "");
    }

    private ComposedExpressionNode composedExpressionNonTerminal() throws SyntacticException, LexicalException, IOException {
        BasicExpressionNode basicExpression = basicExpressionNonTerminal();
        return moreBasicExpressionsNonTerminal(basicExpression);
    }

    private ComposedExpressionNode moreBasicExpressionsNonTerminal(BasicExpressionNode leftExpression) throws SyntacticException, LexicalException, IOException {
        if (isBinaryOperator(currentToken)) {
            Token operator = currentToken;
            OperatorType operatorType = binaryOperatorNonTerminal();
            BasicExpressionNode rightExpressionFirstTerm = basicExpressionNonTerminal();
            ComposedExpressionNode rightExpression = moreBasicExpressionsNonTerminal(rightExpressionFirstTerm);
            return switch (operatorType) {
                case BOOLEAN_TO_BOOLEAN -> new BooleanToBooleanExpressionNode(operator,leftExpression,rightExpression);
                case ANY_TO_BOOLEAN -> new AnyToBooleanExpressionNode(operator,leftExpression,rightExpression);
                case INT_TO_BOOLEAN -> new IntToBooleanExpressionNode(operator,leftExpression,rightExpression);
                case INT_TO_INT -> new IntToIntExpressionNode(operator,leftExpression,rightExpression);
            };
        }
        return leftExpression;
    }

    private OperatorType binaryOperatorNonTerminal() throws SyntacticException, LexicalException, IOException {
        switch (currentToken.name()) {
            case "or":
                match("or");
                return OperatorType.BOOLEAN_TO_BOOLEAN;
            case "and":
                match("and");
                return OperatorType.BOOLEAN_TO_BOOLEAN;
            case "igual":
                match("igual");
                return OperatorType.ANY_TO_BOOLEAN;
            case "desigual":
                match("desigual");
                return OperatorType.ANY_TO_BOOLEAN;
            case "menor":
                match("menor");
               return OperatorType.INT_TO_BOOLEAN;
            case "mayor":
                match("mayor");
                return OperatorType.INT_TO_BOOLEAN;
            case "menorOIgual":
                match("menorOIgual");
                return OperatorType.INT_TO_BOOLEAN;
            case "mayorOIgual":
                match("mayorOIgual");
                return OperatorType.INT_TO_BOOLEAN;
            case "suma":
                match("suma");
                return OperatorType.INT_TO_INT;
            case "resta":
                match("resta");
                return OperatorType.INT_TO_INT;
            case "por":
                match("por");
                return OperatorType.INT_TO_INT;
            case "dividido":
                match("dividido");
                return OperatorType.INT_TO_INT;
            case "modulo":
                match("modulo");
                return OperatorType.INT_TO_INT;
            default:
                throw new UnexpectedSymbolInContextException("operador binario", currentToken, "");
        }
    }

    private BasicExpressionNode basicExpressionNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (isUnaryOperator(currentToken)) {
            Token operator = currentToken;
            unaryOperatorNonTerminal();
            BasicExpressionNode operand = operandNonTerminal();
            if(operator.name().equals("not")) return new BooleanUnaryOperatorExpressionNode(operator,operand);
            else return new IntUnaryOperatorExpressionNode(operator,operand);
        } else if (isOperandFirst(currentToken)) {
            return operandNonTerminal();
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

    private BasicExpressionNode operandNonTerminal() throws SyntacticException, LexicalException, IOException {
        if (isPrimitiveLiteral(currentToken)) return primitiveNonTerminal();
        else if (isReferencePrimaryFirst(currentToken)) return referenceNonTerminal();
        else throw new UnexpectedSymbolInContextException("tipo primitivo o primero de referencia", currentToken, "");
    }

    private PrimitiveLiteralNode primitiveNonTerminal() throws SyntacticException, LexicalException, IOException {
        PrimitiveLiteralNode literal;
        switch (currentToken.name()) {
            case "charLiteral":
                literal = new CharLiteralNode(currentToken);
                match("charLiteral");
                break;
            case "intLiteral":
                literal = new IntLiteralNode(currentToken);
                match("intLiteral");
                break;
            case "palabraReservadanull":
                literal = new NullLiteralNode(currentToken);
                match("palabraReservadanull");
                break;
            case "palabraReservadatrue":
                literal = new BooleanLiteralNode(currentToken);
                match("palabraReservadatrue");
                break;
            case "palabraReservadafalse":
                literal = new BooleanLiteralNode(currentToken);
                match("palabraReservadafalse");
                break;
            default:
                throw new UnexpectedSymbolInContextException("true, false, null, literal entero, literal caracter", currentToken, "");
        }
        return literal;
    }

    private ReferenceNode referenceNonTerminal() throws SyntacticException, LexicalException, IOException {
        PrimaryNode primary = primaryNonTerminal();
        return chainedReferenceNonTerminal(primary);
    }

    private ReferenceNode chainedReferenceNonTerminal(ReferenceNode referencedObject) throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("punto")) {
             referencedObject.setChainedReference(chainedVariableOrMethodNonTerminal());
        }
        return referencedObject;
    }

    private PrimaryNode primaryNonTerminal() throws SyntacticException, LexicalException, IOException {
        switch (currentToken.name()) {
            case "palabraReservadathis":
                ThisNode thisNode = new ThisNode(currentToken);
                match("palabraReservadathis");
                return thisNode;
            case "stringLiteral":
                match("stringLiteral");
                return new StringLiteralNode();
            case "idMetVar":
                return variableAccessOrMethodCallNonTerminal();
            case "palabraReservadanew":
                return constructorCallNonTerminal();
            case "idClase":
                return staticMethodCallNonTerminal();
            case "abreParéntesis":
                return parenthesizedExpressionNonTerminal();
            default:
                throw new UnexpectedSymbolInContextException("this, new, (, id de clase, id de método o variable, o literal string", currentToken, "");
        }
    }

    private PrimaryNode variableAccessOrMethodCallNonTerminal() throws SyntacticException, LexicalException, IOException {
        Token name = currentToken;
        match("idMetVar");
        return (PrimaryNode) optionalActualArgumentsNonTerminal(name,false);
    }

    private ConstructorCallNode constructorCallNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("palabraReservadanew");
        Token constructorName = currentToken;
        match("idClase");
        ParameterListNode parameterListNode = actualArgumentsNonTerminal(constructorName);
        return new ConstructorCallNode(constructorName,parameterListNode);
    }

    private ParenthesizedExpressionNode parenthesizedExpressionNonTerminal() throws SyntacticException, LexicalException, IOException {
        match("abreParéntesis");
        ExpressionNode subExpression = expressionNonTerminal();
        match("cierraParéntesis");
        return new ParenthesizedExpressionNode(subExpression);
    }

    private StaticMethodCallNode staticMethodCallNonTerminal() throws SyntacticException, LexicalException, IOException {
        Token className = currentToken;
        match("idClase");
        match("punto");
        Token methodName = currentToken;
        match("idMetVar");
        ParameterListNode parameterList = actualArgumentsNonTerminal(methodName);
        return new StaticMethodCallNode(methodName,className,parameterList);
    }

    private ReferenceNode optionalActualArgumentsNonTerminal(Token name,boolean isChained) throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("abreParéntesis")) {
            ParameterListNode parameterList = actualArgumentsNonTerminal(name);
            if (isChained) return new ChainedMethodCallNode(name,parameterList);
            else return new MethodCallNode(name,parameterList);
        } else if(isChained) return new ChainedVariableNode(name);
               else return new VariableNode(name);
    }

    private ParameterListNode actualArgumentsNonTerminal(Token callableName) throws SyntacticException, LexicalException, IOException {
        ParameterListNode parameterList = new ParameterListNode(callableName);
        match("abreParéntesis");
        optionalExpressionListNonTerminal(parameterList);
        match("cierraParéntesis");
        return parameterList;
    }

    private void optionalExpressionListNonTerminal(ParameterListNode parameterList) throws SyntacticException, LexicalException, IOException {
        if (isExpressionFirst(currentToken)) {
            expressionListNonTerminal(parameterList);
        }
    }

    private void expressionListNonTerminal(ParameterListNode parameterList) throws SyntacticException, LexicalException, IOException {
        parameterList.addParameter(expressionNonTerminal());
        moreExpressionsNonTerminal(parameterList);
    }

    private void moreExpressionsNonTerminal(ParameterListNode parameterList) throws SyntacticException, LexicalException, IOException {
        if (currentToken.name().equals("coma")) {
            match("coma");
            expressionListNonTerminal(parameterList);
        }
    }

    private ChainedReferenceNode chainedVariableOrMethodNonTerminal() throws SyntacticException, LexicalException, IOException {
        Token pointToken = currentToken;
        match("punto");
        Token name = currentToken;
        match("idMetVar");
        ChainedReferenceNode chainedReference = (ChainedReferenceNode) optionalActualArgumentsNonTerminal(name,true);
        chainedReference.setPointToken(pointToken);
        return chainedReference;
    }

}
