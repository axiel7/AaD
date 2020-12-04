/*
 * SueldosDep.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad2.ej5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class SueldosDep {

    public static void main(String[] args) {
        try {
            int dpto = Integer.parseInt(args[0]);
            readRandom(dpto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readRandom(int searchDpto) throws IOException {
        File f = new File("AleatorioEmple.dat");
        RandomAccessFile raf = new RandomAccessFile(f, "r");

        int id, dep;
        int pos = 0;
        double salario;
        char[] apellido = new char[10];
        char aux;
        boolean found = false;
        double sumaSalario = 0;

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

            if (dep == searchDpto) {
                found = true;
                sumaSalario += salario;
                System.out.println("ID | Apellido | Dpto | Salario");
                System.out.println(id + " | " + apellidoStr + " | " + dep + " | " + salario);
            }
            pos += 36;

        } while (raf.getFilePointer() != raf.length());

        if (!found) { System.out.println("Empleado no encontrado"); }
        else { System.out.println("Suma salarios: " + sumaSalario); }

        raf.close();
    }
}
