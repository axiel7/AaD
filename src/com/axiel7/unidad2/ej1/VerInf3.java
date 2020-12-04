/*
 * VerInf3.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad2.ej1;

import java.io.File;

public class VerInf3 {
    //argumento 1: ruta de un directorio
    //argumento 2: tamaño(KB) máximo de los ficheros a mostrar
    public static void main(String[] args) {
        File d = new File(args[0]);
        int maxKb = Integer.parseInt(args[1]);

        if(d.exists() && d.isDirectory()){
            File[] files = d.listFiles();

            if (files != null) { //comprueba si el directorio contiene ficheros
                for (File f : files) {
                    // .length devuelve en bytes por lo que multiplicamos por 1000
                    if (f.length() < (maxKb * 1000)) {
                        System.out.println("INFORMACIÓN SOBRE EL FICHERO:");
                        System.out.println("Nombre del fichero  : "+f.getName());
                        System.out.println("Ruta                : "+f.getPath());
                        System.out.println("Ruta absoluta       : "+f.getAbsolutePath());
                        System.out.println("Se puede leer       : "+f.canRead());
                        System.out.println("Se puede escribir   : "+f.canWrite());
                        System.out.println("Tamaño              : "+f.length());
                        System.out.println("Es un directorio    : "+f.isDirectory());
                        System.out.println("Es un fichero       : "+f.isFile());
                        System.out.println("Nombre del directorio padre: "+f.getParent());
                    }
                }
            }
        }
    }
}

