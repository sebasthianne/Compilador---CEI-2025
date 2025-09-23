package compiler.lexicalAnalyzer;

import compiler.domain.Token;
import compiler.lexicalAnalyzer.lexicalExceptions.*;
import input.sourcemanager.SourceManager;

import java.io.IOException;
import java.util.Arrays;

public class LexicalAnalyzerImpl implements LexicalAnalyzer {
    private String currentLexeme;
    private char currentCharacter;
    private static final String[] reservedKeywords = {"class", "extends", "public", "static", "void", "boolean", "char", "int", "abstract", "final", "if", "else", "while", "return", "var", "this", "new", "null", "true", "false"};
    private ErrorData multiLineCommentErrorData;
    private final SourceManager sourceManager;

    public LexicalAnalyzerImpl(SourceManager manager) throws IOException {
        sourceManager = manager;
        updateCurrentCharacter();
    }

    private void updateCurrentCharacter() throws IOException {
        currentCharacter = sourceManager.getNextChar();
    }

    private void updateCurrentLexeme() {
        currentLexeme += currentCharacter;
    }

    private int lexemeLength() {
        return currentLexeme.length();
    }

    private boolean lexemeIsReservedKeyword() {
        return Arrays.asList(reservedKeywords).contains(currentLexeme);
    }

    @Override
    public Token getNextToken() throws LexicalException, IOException {
        currentLexeme = "";
        return initialState();
    }

    private Token initialState() throws LexicalException, IOException {
        if (Character.isDigit(currentCharacter)) {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return intLiteralAcceptingState();
        }
        if (Character.isUpperCase(currentCharacter)) {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return classIdentifierAcceptingState();
        }
        if (Character.isLowerCase(currentCharacter)) {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return methodVariableIdentifierAcceptingState();
        }
        if (Character.isWhitespace(currentCharacter)) {
            updateCurrentCharacter();
            return initialState();
        }
        if (currentCharacter == '>') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return greaterThanAcceptingState();
        }
        if (currentCharacter == '<') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return lessThanAcceptingState();
        }
        if (currentCharacter == '!') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return notOperatorAcceptingState();
        }
        if (currentCharacter == '=') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return assignmentAcceptingState();
        }
        if (currentCharacter == '&') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return andState();
        }
        if (currentCharacter == '|') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return orState();
        }
        if (currentCharacter == '%') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return modulusAcceptingState();
        }
        if (currentCharacter == '+') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return plusAcceptingState();
        }
        if (currentCharacter == '-') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return minusAcceptingState();
        }
        if (currentCharacter == '*') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return timesAcceptingState();
        }
        if (currentCharacter == '/') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return slashAcceptingState();
        }
        if (currentCharacter == '(') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return openParenthesisAcceptingState();
        }
        if (currentCharacter == ')') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return closeParenthesisAcceptingState();
        }
        if (currentCharacter == '{') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return openBracketAcceptingState();
        }
        if (currentCharacter == '}') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return closeBracketAcceptingState();
        }
        if (currentCharacter == ';') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return semiColonAcceptingState();
        }
        if (currentCharacter == '.') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return pointAcceptingState();
        }
        if (currentCharacter == ',') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return commaAcceptingState();
        }
        if (currentCharacter == ':') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return colonAcceptingState();
        }
        if (currentCharacter == SourceManager.END_OF_FILE) {
            updateCurrentLexeme();
            return endOfFileAcceptingState();
        }
        if (currentCharacter == '\'') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return characterNonAcceptingState();
        }
        if (currentCharacter == '"') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return stringNonAcceptingState();
        }
        int columnNumber = sourceManager.getColumnNumber();
        updateCurrentLexeme();
        updateCurrentCharacter();
        throw new ForeignCharacterException(currentLexeme, sourceManager.getLineNumber(), columnNumber, sourceManager.getCurrentLine());
    }

    private Token slashAcceptingState() throws LexicalException, IOException {
        if (currentCharacter == '/') {
            currentLexeme = "";
            updateCurrentCharacter();
            return simpleCommentNonAcceptingState();
        } else if (currentCharacter == '*') {
            currentLexeme = "";
            multiLineCommentErrorData = new ErrorData("", sourceManager.getLineNumber(), sourceManager.getColumnNumber(), sourceManager.getCurrentLine());
            updateCurrentCharacter();
            return multiLineCommentNonAcceptingState();
        }
        return new Token("dividido", currentLexeme, sourceManager.getLineNumber());
    }

    private Token multiLineCommentNonAcceptingState() throws LexicalException, IOException {
        if (currentCharacter == SourceManager.END_OF_FILE) {
            throw new UnclosedMultiLineCommentException(multiLineCommentErrorData);
        } else if (currentCharacter == '*') {
            updateCurrentCharacter();
            return multiLineCommentEndState();
        } else {
            updateCurrentCharacter();
            return multiLineCommentNonAcceptingState();
        }
    }

    private Token multiLineCommentEndState() throws LexicalException, IOException {
        if (currentCharacter == '/') {
            updateCurrentCharacter();
            return initialState();
        } else {
            updateCurrentCharacter();
            return multiLineCommentNonAcceptingState();
        }
    }

    private Token simpleCommentNonAcceptingState() throws LexicalException, IOException {
        if (currentCharacter == '\n' || currentCharacter == SourceManager.END_OF_FILE) {
            return initialState();
        } else {
            updateCurrentCharacter();
            return simpleCommentNonAcceptingState();
        }
    }


    private Token stringNonAcceptingState() throws LexicalException, IOException {
        if (currentCharacter == '\n' || currentCharacter == SourceManager.END_OF_FILE) {
            throw new UnclosedStringException(currentLexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), sourceManager.getCurrentLine());
        } else if (currentCharacter == '\\') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return escapedOnStringNonAcceptingState();
        } else if (currentCharacter == '"') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return stringEndAcceptingState();
        } else {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return stringNonAcceptingState();
        }
    }

    private Token escapedOnStringNonAcceptingState() throws LexicalException, IOException {
        if (currentCharacter == '\n' || currentCharacter == SourceManager.END_OF_FILE) {
            throw new UnclosedStringException(currentLexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), sourceManager.getCurrentLine());
        } else {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return stringNonAcceptingState();
        }
    }

    private Token stringEndAcceptingState() {
        return new Token("stringLiteral", currentLexeme, sourceManager.getLineNumber());
    }

    private Token characterNonAcceptingState() throws LexicalException, IOException {
        if (currentCharacter == '\n') {
            throw new NewLineInCharacterException(currentLexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), sourceManager.getCurrentLine());
        } else if (currentCharacter == SourceManager.END_OF_FILE) {
            throw new EndOfFileInCharacterException(currentLexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), sourceManager.getCurrentLine());
        } else if (currentCharacter == '\'') {
            throw new EmptyCharacterException(currentLexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), sourceManager.getCurrentLine());
        } else if (currentCharacter == '\\') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return escapedCharacterNonAcceptingState();
        } else {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return characterEndState();
        }
    }

    private Token escapedCharacterNonAcceptingState() throws LexicalException, IOException {
        if (currentCharacter == '\n') {
            throw new NewLineInCharacterException(currentLexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), sourceManager.getCurrentLine());
        } else if (currentCharacter == SourceManager.END_OF_FILE) {
            throw new EndOfFileInCharacterException(currentLexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), sourceManager.getCurrentLine());
        } else {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return characterEndState();
        }
    }

    private Token characterEndState() throws LexicalException, IOException {
        if (currentCharacter == '\'') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return new Token("charLiteral", currentLexeme, sourceManager.getLineNumber());
        } else {
            throw new MultipleCharactersInCharacterException(currentLexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), sourceManager.getCurrentLine());
        }
    }

    private Token endOfFileAcceptingState() {
        return new Token("endOfFile", currentLexeme, sourceManager.getLineNumber());
    }

    private Token colonAcceptingState() {
        return new Token("dosPuntos", currentLexeme, sourceManager.getLineNumber());
    }

    private Token commaAcceptingState() {
        return new Token("coma", currentLexeme, sourceManager.getLineNumber());
    }

    private Token pointAcceptingState() {
        return new Token("punto", currentLexeme, sourceManager.getLineNumber());
    }

    private Token semiColonAcceptingState() {
        return new Token("puntoYComa", currentLexeme, sourceManager.getLineNumber());
    }

    private Token closeBracketAcceptingState() {
        return new Token("cierraLlave", currentLexeme, sourceManager.getLineNumber());
    }

    private Token openBracketAcceptingState() {
        return new Token("abreLlave", currentLexeme, sourceManager.getLineNumber());
    }

    private Token closeParenthesisAcceptingState() {
        return new Token("cierraParéntesis", currentLexeme, sourceManager.getLineNumber());
    }

    private Token openParenthesisAcceptingState() {
        return new Token("abreParéntesis", currentLexeme, sourceManager.getLineNumber());
    }

    private Token timesAcceptingState() {
        return new Token("por", currentLexeme, sourceManager.getLineNumber());
    }

    private Token modulusAcceptingState() {
        return new Token("modulo", currentLexeme, sourceManager.getLineNumber());
    }

    private Token minusAcceptingState() throws IOException {
        if (currentCharacter == '-') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return new Token("decremento", currentLexeme, sourceManager.getLineNumber());
        } else {
            return new Token("resta", currentLexeme, sourceManager.getLineNumber());
        }
    }

    private Token plusAcceptingState() throws IOException {
        if (currentCharacter == '+') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return new Token("incremento", currentLexeme, sourceManager.getLineNumber());
        } else {
            return new Token("suma", currentLexeme, sourceManager.getLineNumber());
        }
    }

    private Token orState() throws LexicalException, IOException {
        if (currentCharacter == '|') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return new Token("or", currentLexeme, sourceManager.getLineNumber());
        } else
            throw new OrNotDoubledException(currentLexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), sourceManager.getCurrentLine());
    }

    private Token andState() throws LexicalException, IOException {
        if (currentCharacter == '&') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return new Token("and", currentLexeme, sourceManager.getLineNumber());
        } else
            throw new AndNotDoubledException(currentLexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), sourceManager.getCurrentLine());
    }

    private Token assignmentAcceptingState() throws IOException {
        if (currentCharacter == '=') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return new Token("igual", currentLexeme, sourceManager.getLineNumber());
        } else {
            return new Token("asignación", currentLexeme, sourceManager.getLineNumber());
        }
    }

    private Token notOperatorAcceptingState() throws IOException {
        if (currentCharacter == '=') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return new Token("desigual", currentLexeme, sourceManager.getLineNumber());
        } else {
            return new Token("not", currentLexeme, sourceManager.getLineNumber());
        }
    }

    private Token lessThanAcceptingState() throws IOException {
        if (currentCharacter == '=') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return new Token("menorOIgual", currentLexeme, sourceManager.getLineNumber());
        } else {
            return new Token("menor", currentLexeme, sourceManager.getLineNumber());
        }
    }

    private Token greaterThanAcceptingState() throws IOException {
        if (currentCharacter == '=') {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return new Token("mayorOIgual", currentLexeme, sourceManager.getLineNumber());
        } else {
            return new Token("mayor", currentLexeme, sourceManager.getLineNumber());
        }
    }

    private Token methodVariableIdentifierAcceptingState() throws IOException {
        if (currentCharacter == '_' || Character.isLetterOrDigit(currentCharacter)) {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return methodVariableIdentifierAcceptingState();
        } else if (lexemeIsReservedKeyword()) {
            return new Token("palabraReservada" + currentLexeme, currentLexeme, sourceManager.getLineNumber());
        } else {
            return new Token("idMetVar", currentLexeme, sourceManager.getLineNumber());
        }
    }

    private Token classIdentifierAcceptingState() throws IOException {
        if (currentCharacter == '_' || Character.isLetterOrDigit(currentCharacter)) {
            updateCurrentLexeme();
            updateCurrentCharacter();
            return classIdentifierAcceptingState();
        } else {
            return new Token("idClase", currentLexeme, sourceManager.getLineNumber());
        }
    }

    private Token intLiteralAcceptingState() throws LexicalException, IOException {
        if (Character.isDigit(currentCharacter)) {
            if (lexemeLength() == 9)
                throw new NumberTooLongException(currentLexeme, sourceManager.getLineNumber(), sourceManager.getColumnNumber(), sourceManager.getCurrentLine());
            updateCurrentLexeme();
            updateCurrentCharacter();
            return intLiteralAcceptingState();
        } else {
            return new Token("intLiteral", currentLexeme, sourceManager.getLineNumber());
        }
    }


}
