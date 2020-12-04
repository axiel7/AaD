/*
 * BorraFICH.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad2.ej1;

import java.io.File;

public class BorraFICH {
    //argumento 1: ruta de un directorio
    //argumento 2: nombre de un fichero/directorio
    public static void main(String[] args) {
        String folderName = args[0];
        File file = new File(folderName + File.pathSeparator + args[1]);

        if (file.exists() && file.isFile()) {
            boolean deleted = file.delete();
            if (deleted) { System.out.println("Archivo eliminado."); }
        }
        else if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();

            if (files != null) { //comprueba si el directorio contiene ficheros
                for (File f : files) {
                    String name = f.getName();
                    boolean fileDeleted = f.delete();
                    if (fileDeleted) { System.out.println("Archivo " + name + " eliminado."); }
                }
            }
            boolean directoryDeleted = file.delete();
            if (directoryDeleted) { System.out.println("Directorio eliminado."); }
        }
    }
}
