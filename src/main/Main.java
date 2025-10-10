package main;

import injector.Injector;
import input.sourcemanager.SourceManager;

import java.io.FileNotFoundException;
import java.io.IOException;

import static main.syntacticExecutionAndDeclarationChecks.SyntacticExecutionAndDeclarationChecks.executeSyntacticAnalysis;

public class Main {
    private static final Injector injector = Injector.getInjector();

    public static void main(String[] args) {
        SourceManager source = injector.getSource();
        boolean open = tryOpen(args, source);
        if (open) {
            tryAnalysis(source);
        }
    }

    private static boolean tryOpen(String[] args, SourceManager source) {
        try {
            source.open(args[0]);
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Error, no encontró archivo válido en la ruta seleccionada");
            return false;
        }
    }

    private static void tryAnalysis(SourceManager source) {
        try {
            executeSyntacticAnalysis(injector.getSyntacticAnalyzer(injector.getLexicalAnalyzer(source)));
        } catch (IOException e) {
            System.out.println("Error, ocurrió un error durante la lectura del archivo");
        } finally {
            try {
                source.close();
            } catch (IOException e) {
                System.out.println("Error, el archivo no se cerró correctamente");
            }
        }
    }

}
