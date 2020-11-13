/*
 * EjercicioMetadata.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EjercicioMetadata {
    public static void main(String[] args) {
        try {
            //Conexión mysql
            Class.forName("com.mysql.jdbc.Driver");
            //Conexión desde casa
            Connection conexion = DriverManager
                    .getConnection("jdbc:mysql://iescierva.net:14306?useSSL=false", "lector", "lector");
            //Conexión UTP
            /*Connection conexion = DriverManager
                    .getConnection("jdbc:mysql://iescierva.net:14306?useSSL=false", "lector", "lector");
            */

            DatabaseMetaData dbmd = conexion.getMetaData();
            ResultSet catalogResult = dbmd.getCatalogs(); //obtiene todos los catálogos

            Scanner scanner = new Scanner(System.in);
            System.out.print("Nombre de la tabla a buscar: ");
            String tableName = scanner.next();

            System.out.println("Catálogos con la tabla " + tableName + ":");
            List<String> foundCatalogs = new ArrayList<>();
            int auxCatalogIndex = 0;
            while (catalogResult.next()) {
                //busca la tabla en todos los catálogos
                ResultSet tableResult = dbmd.getTables(catalogResult.getString(1), null, tableName, null);
                while (tableResult.next()) {
                    String catalog = tableResult.getString(1);
                    System.out.println(auxCatalogIndex + ". " + catalog);
                    foundCatalogs.add(catalog);
                    auxCatalogIndex++;
                }
            }

            System.out.println("(El índice -1 representa todos los catálogos)");
            System.out.print("\nEscriba el número del catálogo para mostrar su información: ");
            int catalogIndex = scanner.nextInt();
            Statement statement = conexion.createStatement();
            if (catalogIndex==-1) {
                for (String catalog : foundCatalogs) {
                    printTableInfo(statement, tableName, catalog);
                }
            }
            else {
                printTableInfo(statement, tableName, foundCatalogs.get(catalogIndex));
            }

            conexion.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void printTableInfo(Statement statement, String tableName, String catalog) {
        try {
            //query para seleccionar todos los campos de la tabla del catálogo elegido
            ResultSet infoResult = statement.executeQuery("SELECT * FROM " + catalog + "." + tableName + ";");
            ResultSetMetaData rsmd = infoResult.getMetaData();
            int columns = rsmd.getColumnCount();
            System.out.println("Recuperadas " + columns + " columnas de la tabla " + tableName + " del catálogo " + catalog);
            System.out.println("Columna | Nombre | Tipo | Tamaño");
            for (int i=1; i<=columns; i++) {
                System.out.print(i + " | ");
                System.out.print(rsmd.getColumnName(i) + " | ");
                System.out.print(rsmd.getColumnTypeName(i) + " | ");
                System.out.print(rsmd.getColumnDisplaySize(i));
                System.out.println();
            }
            System.out.println();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
