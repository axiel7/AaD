/*
 * MenuNba.java
 * Realizado por Axel Lopez
 * 2DAM
 */
package com.axiel7.unidad1;

import java.sql.*;
import java.util.Scanner;

public class MenuNba {
    public static void main(String[] args) {
        try {
            //Conexión mysql
            Class.forName("com.mysql.jdbc.Driver");
            //Conexión desde casa
            Connection conexion = DriverManager
                    .getConnection("jdbc:mysql://iescierva.net:14306/aad_16?useSSL=false", "aad_16", "987456348");
            //Conexión UTP
            /*Connection conexion = DriverManager
                    .getConnection("jdbc:mysql://172.20.254.161/aad_16?useSSL=false", "aad_16", "987456348");
            */

            Scanner scanner = new Scanner(System.in);
            Statement statement = conexion.createStatement();

            showMenu(statement, scanner);

            scanner.close();
            statement.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
    private static void showMenu(Statement statement, Scanner scanner) {
        System.out.print("\n1. Mostrar todas las divisiones que hay en cada conferencia" +
                "\n2. Mostrar equipos por conferencia (este/oeste)" +
                "\n3. Listar los jugadores de un equipo" +
                "\n4. Mostrar los partidos en los que ha jugado un equipo (local o visitante)" +
                "\n0. Salir" +
                "\nSeleccione:");
        int action = scanner.nextInt();
        switch (action) {
            case 1:
                mostrarDivisiones(statement);
                break;
            case 2:
                mostrarEquipos(statement, scanner);
                break;
            case 3:
                mostrarJugadores(statement, scanner);
                break;
            case 4:
                mostrarPartidos(statement, scanner);
                break;
            case 0:
                break;
        }
    }

    private static void mostrarPartidos(Statement statement, Scanner scanner) {
        System.out.print("Introduzca un equipo: ");
        String equipo = scanner.next();
        System.out.print("Indica si es local o visitante:\n1. Local\n2. Visitante\n");
        int type = scanner.nextInt();
        String sql = "";
        switch (type) {
            case 1 -> sql = "SELECT * FROM partidos WHERE equipo_local='" + equipo + "';";
            case 2 -> sql = "SELECT * FROM partidos WHERE equipo_visitante='" + equipo + "';";
        }
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columns = rsmd.getColumnCount();
            for (int i=1; i<columns; i++) {
                System.out.print(rsmd.getColumnName(i) + " | ");
            }
            System.out.println();
            while (resultSet.next()) {
                for (int i=1; i<columns; i++) {
                    System.out.print(resultSet.getString(i) + " | ");
                }
                System.out.println();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void mostrarJugadores(Statement statement, Scanner scanner) {
        System.out.print("Introduzca equipo: ");
        String equipo = scanner.next();
        String sql = "SELECT Nombre FROM jugadores WHERE Nombre_equipo='" + equipo + "';";
        System.out.println("Jugadores del equipo " + equipo);
        printResultSet(statement, sql);
    }

    private static void mostrarEquipos(Statement statement, Scanner scanner) {
        System.out.print("Introduzca conferencia (este/oeste): ");
        String conferencia = scanner.next();
        String sql = "SELECT DISTINCT Nombre FROM equipos WHERE Conferencia='" + conferencia + "';";
        System.out.println("Equipos de la conferencia " + conferencia);
        printResultSet(statement, sql);
    }

    private static void mostrarDivisiones(Statement statement) {
        String[] conferencias = {"Este", "Oeste"};
        for (String conferencia : conferencias) {
            String sql = "SELECT DISTINCT Division FROM equipos WHERE Conferencia='" + conferencia + "';";
            System.out.println("Divisiones de la conferencia " + conferencia);
            printResultSet(statement, sql);
        }
    }

    private static void printResultSet(Statement statement, String sql) {
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                System.out.print(resultSet.getString(1) + " | ");
            }
            System.out.println();
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }
}
