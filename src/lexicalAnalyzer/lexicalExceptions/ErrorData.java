package lexicalAnalyzer.lexicalExceptions;

public record ErrorData(String lexeme, int lineNumber, int columnNumber) {
    @Override
    public String toString() {
        return "Error Léxico en linea "+lineNumber+" columna "+columnNumber+" con lexema: "+lexeme+".";
    }
}
