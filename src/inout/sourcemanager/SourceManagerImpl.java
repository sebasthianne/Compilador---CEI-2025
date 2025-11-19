package inout.sourcemanager;
//Author: Juan Dingevan

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SourceManagerImpl implements SourceManager {
    private BufferedReader reader;
    private String currentLine;
    private int lineNumber;
    private int lineIndexNumber;
    private boolean mustReadNextLine;
    private String outputFilePath;
    private List<String> generatedCodeLines;


    public SourceManagerImpl() {
        currentLine = "";
        lineNumber = 0;
        lineIndexNumber = 0;
        mustReadNextLine = true;
        outputFilePath = null;
        generatedCodeLines = new ArrayList<>();
    }

    @Override
    public void open(String filePath) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);

        reader = new BufferedReader(inputStreamReader);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public char getNextChar() throws IOException {
        char currentChar = ' ';

        if (mustReadNextLine) {
            currentLine = reader.readLine();
            lineNumber++;
            lineIndexNumber = 0;
            mustReadNextLine = false;
        }

        if (lineIndexNumber < currentLine.length()) {
            currentChar = currentLine.charAt(lineIndexNumber);
        } else if (reader.ready()) {
            currentChar = '\n';
            mustReadNextLine = true;
        } else {
            currentChar = END_OF_FILE;
        }

        lineIndexNumber++;
        return currentChar;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public int getColumnNumber() {
        return lineIndexNumber;
    }

    @Override
    public String getCurrentLine() {
        return currentLine;
    }

    @Override
    public void generate(String s) {
        generatedCodeLines.add(s);
    }

    @Override
    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void createOutputFile() throws IOException {
        File outputFile = new File(outputFilePath);
        if(!outputFile.createNewFile()){
            outputFile.delete();
            outputFile.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        for (String s : generatedCodeLines){
            bufferedWriter.write(s);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
        generatedCodeLines = new ArrayList<>();
    }

    @Override
    public String getOutputFilePath() {
        return outputFilePath;
    }
}
