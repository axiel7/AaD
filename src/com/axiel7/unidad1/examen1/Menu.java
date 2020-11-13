package com.axiel7.unidad1.examen1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
            String path = Menu.class.getResource("/com/axiel7/unidad1/examen1/centros_sqlite.db").getPath();
            Connection connection = DriverManager.getConnection("jdbc:sqlite:"+path);

            Scanner scanner = new Scanner(System.in);
            Statement statement = connection.createStatement();

            boolean exit = false;
            while (!exit) {
                exit = showMenu(statement, scanner, connection);
            }

            scanner.close();
            statement.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean showMenu(Statement statement, Scanner scanner, Connection connection) {
        System.out.print("\n1. Mostrar profesores" +
                "\n2. Insertar profesor" +
                "\n3. Eliminar CENTRO" +
                "\n4. Mostrar tablas BBDD" +
                "\n5. Crear Tabla (Incompleto)" +
                "\n0. Salir" +
                "\nSeleccione:");
        int action = scanner.nextInt();
        switch (action) {
            case 1:
                showProfesores(statement);
                return false;
            case 2:
                insertProfesor(statement, scanner);
                return false;
            case 3:
                deleteCentro(statement, scanner);
                return false;
            case 4:
                showTables(connection);
                return false;
            case 5:
                createTable();
                return false;
            case 0:
                return true;
            default:
                return false;
        }
    }

    private static void createTable() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Conexión desde casa
            Connection conexion = DriverManager
                    .getConnection("jdbc:mysql://iescierva.net:14306/aad_16?useSSL=false", "aad_16", "987456348");
            //Conexión UTP
            /*Connection conexion = DriverManager
                    .getConnection("jdbc:mysql://172.20.254.161/aad_16?useSSL=false", "aad_16", "987456348");
            */
            Statement statement = conexion.createStatement();

            String dropTable = "DROP TABLE IF EXISTS ASIG_ESPEC_Axel;";
            statement.executeUpdate(dropTable);

            String createTable = "CREATE TABLE ASIG_ESPEC_Axel (" +
                    "NOMBRE_ESPECIALIDAD VARCHAR(25)," +
                    "NOMBRE_ASIGNATURA VARCHAR(30)" +
                    ");";
            statement.executeUpdate(createTable);

            String getEspecialidades = "SELECT NOMBRE_ESPE FROM C1_ESPECIALIDAD";
            ResultSet rsEspec = statement.executeQuery(getEspecialidades);
            List<String> especialidades = new ArrayList<>();
            while (rsEspec.next()) {
                especialidades.add(rsEspec.getString(1));
            }

            String getAsignaturas = "SELECT NOMBRE_ASI FROM C1_ASIGNATURAS";
            ResultSet rsAsi = statement.executeQuery(getAsignaturas);
            List<String> asiganturas = new ArrayList<>();
            while (rsAsi.next()) {
                asiganturas.add(rsAsi.getString(1));
            }

            System.out.println("No me dió tiempo a completar este ejercicio");
            //TODO

            statement.close();
            conexion.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showTables(Connection connection) {
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, null, "%", null);
            System.out.println("Tablas:");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void deleteCentro(Statement statement, Scanner scanner) {
        System.out.println("Código del centro a eliminar: ");
        int cod = scanner.nextInt();
        try {
            String sqlProf = "DELETE FROM C1_PROFESORES WHERE COD_CENTRO=" + cod;
            String sqlCen = "DELETE FROM C1_CENTROS WHERE COD_CENTRO=" + cod;

            statement.executeUpdate(sqlProf);
            statement.executeUpdate(sqlCen);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private static void insertProfesor(Statement statement, Scanner scanner) {
        System.out.print("Código profesor: ");
        int codProf = scanner.nextInt();

        System.out.print("Nombre, Apellido: ");
        String name = scanner.next();

        boolean validEspec = false;
        String espec = "";
        while (!validEspec) {
            System.out.print("Especialidad: ");
            espec = scanner.next();
            validEspec = checkValue("ESPECIALIDAD", espec, "C1_ESPECIALIDAD", statement);
            if (!validEspec) { System.out.println("No existe esa especialidad."); }
        }

        System.out.print("Jefe dpto. : ");
        int boss = scanner.nextInt();

        System.out.print("Fecha nacimiento (yyyy-mm-dd): ");
        String birth = scanner.next();

        System.out.print("Sexo (H/M): ");
        String sex = scanner.next();

        boolean validCen = false;
        int codCen = 0;
        while (!validCen) {
            System.out.print("Código centro: ");
            codCen = scanner.nextInt();
            validCen = checkValue("COD_CENTRO", String.valueOf(codCen), "C1_CENTROS", statement);
            if (!validCen) { System.out.println("No existe ese código de centro."); }
        }
        try {
            String sql = "INSERT INTO C1_PROFESORES VALUES (" + codProf + ",'" + name + "','" + espec + "'," + boss + ",'"
                    + birth + "','" + sex + "'," + codCen + ");";
            statement.executeUpdate(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static boolean checkValue(String name, String value, String table, Statement statement) {
        try {
            String sql = "SELECT " + name + " FROM " + table;
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                if (resultSet.getString(1).equals(value)) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private static void showProfesores(Statement statement) {
        try {
            String sql = "SELECT NOMBRE_APE, NOM_CENTRO FROM C1_PROFESORES,C1_CENTROS \n" +
                    "WHERE C1_PROFESORES.COD_CENTRO=C1_CENTROS.COD_CENTRO \n" +
                    "ORDER BY NOM_CENTRO;";
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Nombre, Apellido | Centro");
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " | " + resultSet.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
