/*
 * FichText2.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad2.ej4;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FichText2 {

    public static void main(String[] args) {
        try {
            String downloadsPath = "C:\\Users\\axiel\\Downloads\\"; //Ruta del directorio contenedor de los ficheros a usar
            int sumatorio = sumNums(downloadsPath + "File.txt");
            System.out.println("Suma: " + sumatorio);
            writeFile(String.valueOf(sumatorio), downloadsPath + "Sumatorio.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int sumNums(String path) throws IOException {
        File f = new File(path);
        BufferedReader bfr = new BufferedReader(new FileReader(f));

        String line;
        int suma = 0;
        while ((line = bfr.readLine()) != null) {
            suma += Integer.parseInt(line);
        }

        bfr.close();

        return suma;
    }

    private static void writeFile(String text, String path) throws IOException {
        File f = new File(path);
        FileWriter fw = new FileWriter(f);

        fw.write(text);
        fw.close();
    }
}
