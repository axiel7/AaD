/*
 * VerFicherosAlfab.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad2.ej3;

import java.io.File;
import java.util.Scanner;

public class BorrarDirA {
    //argumento 1: ruta de un directorio
    public static void main(String[] args) {
        File d = new File(args[0]);
        borrarDir(d);
    }
    private static void borrarDir(File dir) {
        if(dir.exists() && dir.isDirectory()) {
            File[] files = dir.listFiles();
            Scanner scanner = new Scanner(System.in);

            if (files != null) { //comprueba si el directorio contiene ficheros
                for (File f : files) {
                    if (f.isFile()) { // si es archivo pregunta si quiere borrar
                        System.out.println("Borrar fichero " + f.getName() + "? y/n");
                        String option = scanner.next();
                        if (option.equals("y")) {
                            f.delete();
                        }
                    }
                    //si es directorio se vuelve a llamar a la funcion para recorrer su contenido
                    else if (f.isDirectory()) {
                        borrarDir(f);
                    }
                }
            }
        }
    }
}

