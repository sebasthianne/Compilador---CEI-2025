package main;

import injector.Injector;
import lexicalClient.LexicalClient;
import sourcemanager.SourceManager;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    private static final Injector injector=Injector.getInjector();

    public static void main(String[] args){
        SourceManager source = injector.getSource();
        boolean open= tryOpen(args, source);
        LexicalClient lexicalClient= new LexicalClient();
        if(open) {
            tryAnalysis(lexicalClient, source);
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

    private static void tryAnalysis(LexicalClient lexicalClient, SourceManager source) {
        try {
            lexicalClient.executeLexicalAnalysis(injector.getLexicalAnalyzer(source));
        } catch (IOException e) {
            System.out.println("Error, ocurrió un error durante la lectura del archivo");
        }
        finally {
            try {
                source.close();
            } catch (IOException e) {
                System.out.println("Error, el archivo no se cerró correctamente");
            }
        }
    }
}
