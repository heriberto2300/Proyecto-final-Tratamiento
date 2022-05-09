package main;

import clasificador.Arbol;
import clasificador.KNN;
import datos.Datos;
import datos.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static boolean filtrar = true;
    public static String filtradoT;
    public static String filtradoP;
    public static Scanner x;
    
    public static int[] mejoresAtributos;
    
    public static void main(String[] args) {
        x = new Scanner(System.in);
        
        /*RECUPERACION DE ARGUMENTOS*/
        int k = Integer.parseInt(args[0]);
        String detallesDatos = args[1];
        boolean detallesKNN = Boolean.parseBoolean(args[2]);
        String rutaFolder = args[3] + "/";
        String nombreT = args[4];
        String nombreP = args[5];
        
        filtradoT = nombreT.replace(".txt", "-filtrado.txt");
        filtradoP = nombreP.replace(".txt", "-filtrado.txt");
        
        try {
            runProyecto(k, detallesDatos, detallesKNN, rutaFolder, nombreT, nombreP);
            
            runProyecto(k, detallesDatos, detallesKNN, rutaFolder, filtradoT, filtradoP);
        } catch (InterruptedException ex) {}
        x.close();
    }
    
    public static void runProyecto(int k, String detallesDatos, boolean detallesKNN, String ruta, String nombreT, String nombreP) throws InterruptedException {

        /*CONSTRUCCION DEL CONJUNTO DE DATOS*/
        ArrayList<String> entrenamiento = Files.leerDatos(ruta + nombreT);
        ArrayList<String> prueba = Files.leerDatos(ruta + nombreP);
        Datos datos = new Datos(entrenamiento, detallesDatos);
        
        /*CLASIFICADOR KNN*/
        KNN knn = new KNN(datos, prueba, k, detallesKNN); 
        Thread threadKNN = new Thread(knn);
        System.out.println("------------INICIANDO CLASIFICADOR KNN----------\n");
        threadKNN.start();
        threadKNN.join();
        System.out.println("\n------------CLASIFICADOR KNN FINALIZADO----------\n");
        x.nextLine();
        
        /*CONVERSION DE DATOS A .ARFF*/
        System.out.println("\nCONVIRTIENDO DATOS A FORMATO .ARFF");
        nombreT = nombreT.replace(".txt", ".arff");
        nombreP = nombreP.replace(".txt", ".arff");
        String cabecera;
        if(!filtrar) {
            cabecera = Files.initCabeceraARFF(datos.getTipoAtributos(), datos.getCabecera(), datos.getTotalClases(), mejoresAtributos);
        }else {
            cabecera = Files.initCabeceraARFF(datos.getTipoAtributos(), datos.getCabecera(), datos.getTotalClases());
        }
        Files.crearARFF(entrenamiento, cabecera, ruta + nombreT);
        Files.crearARFF(prueba, cabecera, ruta + nombreP);
        System.out.println("FINALIZADO\n");
        x.nextLine();
        
        /*ARBOL DE DECISION C4.5*/
        if(!filtrar) {
            System.out.print("Desea ejecutar C4.5? S/N: ");
            String respuesta = x.nextLine();
            if(respuesta.equals("N")) {
                System.exit(0);
            }
        }
        Arbol c45 = new Arbol(ruta + nombreT, ruta + nombreP);
        c45.showInput(filtrar);
        Thread threadC45 = new Thread(c45);
        System.out.println("------------INICIANDO ARBOL DE DECISION----------\n");
        threadC45.start();
        threadC45.join();
        System.out.println("\n------------CLASIFICADOR C4.5 FINALIZADO----------\n");
        
        if(filtrar) {
            mejoresAtributos = c45.getMejoresAtributos();
            Files.filtrar(entrenamiento, mejoresAtributos, datos.getCabecera(), ruta + filtradoT);
            Files.filtrar(prueba, mejoresAtributos, datos.getCabecera(), ruta + filtradoP);
            filtrar = false;
            System.out.println("DATOS FILTRADOS EN BASE A LOS ATRIBUTOS SELECCIONADOS");
            System.out.println(Arrays.toString(mejoresAtributos) + "\n");
            x.nextLine();
        }
        
    }
}