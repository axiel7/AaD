/*
 * LocalizarAPELLIDO_Empleado.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class LocalizarAPELLIDO_Empleado {

    public static void main(String[] args) {
        try {
            String apellido = args[0];
            readRandom(apellido);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readRandom(String searchApellido) throws IOException {
        File f = new File("AleatorioEmple.dat");
        RandomAccessFile raf = new RandomAccessFile(f, "r");

        int id, dep;
        int pos = 0;
        double salario;
        char[] apellido = new char[10];
        char aux;
        boolean found = false;

        do {
            raf.seek(pos);
            id = raf.readInt();

            //recorro uno a uno los caracteres del apellido
            for (int i = 0; i < apellido.length; i++) {
                aux = raf.readChar();
                apellido[i] = aux;
            }
            String apellidoStr = new String(apellido);
            dep = raf.readInt();
            salario = raf.readDouble();

            if (apellidoStr.trim().equalsIgnoreCase(searchApellido)) {
                found = true;
                System.out.println("ID | Apellido | Dpto | Salario");
                System.out.println(id + " | " + apellidoStr + " | " + dep + " | " + salario);
                break;
            }
            pos += 36;

        } while (raf.getFilePointer() != raf.length());

        if (!found) { System.out.println("Empleado no encontrado"); }

        raf.close();
    }
}
