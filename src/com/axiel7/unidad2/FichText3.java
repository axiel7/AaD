/*
 * FichText3.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad2;

import java.io.*;
import java.util.Scanner;

public class FichText3 {

    public static void main(String[] args) {
        try {
            String downloadsPath = "C:\\Users\\axiel\\Downloads\\"; //Ruta del directorio contenedor de los ficheros a usar
            Scanner scanner = new Scanner(System.in);
            System.out.print("Numero de veces a sumar: ");
            int num = scanner.nextInt();
            int sum = sumNum(num);
            writeFile(String.valueOf(sum), downloadsPath + "Suma.txt");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int sumNum(int num) {
        int sum = 0;
        for (int i = 0; i <= num; i++) {
            sum += i;
        }
        return sum;
    }

    private static void writeFile(String text, String path) throws IOException {
        File f = new File(path);
        FileWriter fw = new FileWriter(f);

        fw.write(text);
        fw.close();
    }
}
