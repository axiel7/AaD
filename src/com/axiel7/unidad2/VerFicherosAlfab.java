/*
 * VerFicherosAlfab.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad2;

import java.io.File;
import java.util.*;

public class VerFicherosAlfab {
    //argumento 1: ruta de un directorio
    public static void main(String[] args) {
        File d = new File(args[0]);
        List<String> fileNames = new ArrayList<>();

        if(d.exists() && d.isDirectory()) {
            File[] files = d.listFiles();

            if (files != null) { //comprueba si el directorio contiene ficheros
                for (File f : files) {
                    fileNames.add(f.getName());
                }
                fileNames.sort(String.CASE_INSENSITIVE_ORDER); //orden alfabetico
                fileNames.sort(Comparator.reverseOrder()); //orden inverso
                for (String name : fileNames) {
                    System.out.println(name);
                }
            }
        }
    }
}

