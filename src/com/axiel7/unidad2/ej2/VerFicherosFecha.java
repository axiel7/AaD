/*
 * VerFicherosFecha.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad2.ej2;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VerFicherosFecha {
    //argumento 1: fecha (dd-MM-yyyy)
    //argumento 2: ruta de un directorio
    public static void main(String[] args) throws ParseException {
        File d = new File(args[1]);
        String fecha = args[0];
        SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatDate.parse(fecha); //convertir String a Date

        if(d.exists() && d.isDirectory()) {
            File[] files = d.listFiles();

            if (files != null) { //comprueba si el directorio contiene ficheros
                SimpleDateFormat formatMs = new SimpleDateFormat("SSS");
                for (File f : files) {
                    Date fileDate = formatMs.parse(String.valueOf(f.lastModified()));
                    if (fileDate.after(date)) { //compara Date del archivo con Date especificado
                        System.out.println(f.getName() + " , " + fileDate);
                    }
                }
            }
        }
    }
}
