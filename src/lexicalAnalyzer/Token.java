package lexicalAnalyzer;

public record Token(String name, String lexeme, int lineNumber) {
    @Override
    public String toString(){
        return "("+name+","+lexeme+","+lineNumber+")";
    }
}
