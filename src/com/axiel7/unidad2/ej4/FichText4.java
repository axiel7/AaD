/*
 * FichText4.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad2.ej4;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FichText4 {

    public static void main(String[] args) {
        try {
            String downloadsPath = "C:\\Users\\axiel\\Downloads\\"; //Ruta del directorio contenedor de los ficheros a usar

            List<String> personas = new ArrayList<>();
            Collections.addAll(personas, args);
            personas.sort(String.CASE_INSENSITIVE_ORDER);

            writeFile(personas, downloadsPath + "Personas.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(List<String> strings, String path) throws IOException {
        File f = new File(path);
        FileWriter fw = new FileWriter(f);

        for (String str : strings) {
            fw.write(str);
            fw.write("\n");
        }
        fw.close();
    }
}
