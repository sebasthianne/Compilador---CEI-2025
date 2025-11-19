package inout.sourcemanager;
//Author: Juan Dingevan

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SourceManager {
    void open(String filePath) throws FileNotFoundException;

    void close() throws IOException;

    char getNextChar() throws IOException;

    int getLineNumber();

    int getColumnNumber();

    String getCurrentLine();

    public static final char END_OF_FILE = (char) 26;

    void generate(String s);

    void setOutputFilePath(String outputFilePath);

    void createOutputFile() throws IOException;

    String getOutputFilePath();
}
