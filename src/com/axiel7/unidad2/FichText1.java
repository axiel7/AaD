/*
 * FichText1.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad2;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FichText1 {

    public static void main(String[] args) {
        try {
            String downloadsPath = "C:\\Users\\axiel\\Downloads\\"; //Ruta del directorio contenedor de los ficheros a usar
            Character[] charsRead = readChars(downloadsPath + "File.txt");
            writeChars(charsRead, downloadsPath + "NewFile.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Lee los caracteres uno a uno de un fichero y devuelve un array compuesto de estos
    private static Character[] readChars(String path) throws IOException {
        File f = new File(path);
        FileReader fr = new FileReader(f);

        List<Character> chars = new ArrayList<>();
        int i;
        while ((i = fr.read()) != -1) {
            chars.add((char) i );
        }

        fr.close();

        return chars.toArray(new Character[0]);
    }

    // Escribe los caracteres en mayus pasados como parámetro en un nuevo fichero
    private static void writeChars(Character[] charsToWrite, String path) throws IOException {
        File f = new File(path);
        FileWriter fw = new FileWriter(f);

        for (Character c : charsToWrite) {
            fw.write(Character.toUpperCase(c));
        }

        fw.close();

    }
}
