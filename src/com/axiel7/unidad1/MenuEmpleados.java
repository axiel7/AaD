/*
 * Realizado por Axel Lopez - 2DAM
 * @author: axiel7
 */
package com.axiel7.unidad1;

import java.sql.*;
import java.util.Scanner;

public class MenuEmpleados {
    public static void main(String[] args) {
        try {
            //Conexión mysql
            Class.forName("com.mysql.jdbc.Driver");
            //Conexión desde casa
            Connection conexion = DriverManager
                    .getConnection("jdbc:mysql://iescierva.net:14306/aad_16", "aad_16", "987456348");
            //Conexión UTP
            /*Connection conexion = DriverManager
                    .getConnection("jdbc:mysql://172.20.254.161/aad_16", "aad_16", "987456348");
            */

            Scanner scanner = new Scanner(System.in);
            Statement statement = conexion.createStatement();
            showMenu(statement, scanner);

            scanner.close();
            statement.close();
            conexion.close();

        } catch (ClassNotFoundException | SQLException cn) {
            cn.printStackTrace();
        }
    }
    private static void showMenu(Statement statement, Scanner scanner) {
        System.out.print("\n1. Añadir empleado\n2. Borrar empleado\n3. Actualizar datos\n" +
                "4. Mostrar empleados\n0. Salir\nSeleccione:");
        int action = scanner.nextInt();
        switch (action) {
            case 0:
                break;
            case 1:
                insertEmpl(statement, scanner);
                break;
            case 2:
                deleteEmpl(statement, scanner);
                break;
            case 3:
                updateEmpl(statement, scanner);
                break;
            case 4:
                showAllEmpl(statement, scanner);
                break;
        }
    }
    private static void insertEmpl(Statement statement, Scanner scanner) {
        String sql = insertEmplSql(scanner);
        System.out.println(sql);
        int rows;
        try {
            rows = statement.executeUpdate(sql);
            System.out.println("Filas afectadas: " + rows);
            showMenu(statement, scanner);
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.printf("HA OCURRIDO UNA EXCEPCIÓN:%n");
            System.out.printf("Mensaje   : %s %n", e.getMessage());
            System.out.printf("SQL estado: %s %n", e.getSQLState());
            System.out.printf("Código error : %s %n", e.getErrorCode());
        }
    }
    private static String insertEmplSql(Scanner scanner) {
        System.out.print("Num empleado: ");
        int empNo = scanner.nextInt();
        System.out.print("Apellido: ");
        String apellido = scanner.next();
        System.out.print("Oficio: ");
        String oficio = scanner.next();
        System.out.print("Salario: ");
        int salario = scanner.nextInt();
        System.out.print("Num depart: ");
        int departNum = scanner.nextInt();
        return "INSERT INTO empleados (emp_no, apellido, oficio,salario, dept_no) "
                + " VALUES (" + empNo + ",'" + apellido + "','" + oficio + "'," + salario + "," + departNum + ")";
    }
    private static void deleteEmpl(Statement statement, Scanner scanner) {
        try {
            showAllEmpl(statement, null);
            System.out.print("Introduzca el num empleado a eliminar: ");
            int empNo = scanner.nextInt();
            String sql = "DELETE FROM empleados WHERE emp_no=" + empNo;
            System.out.println(sql);
            statement.execute(sql);
            showMenu(statement, scanner);
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }
    private static void updateEmpl(Statement statement, Scanner scanner) {
        try {
            showAllEmpl(statement, null);
            System.out.println("Este programa solo permite actualizar datos de tipo String.");
            System.out.print("Introduzca el num empleado: ");
            int empNo = scanner.nextInt();
            System.out.print("Escribe el campo para actualizar: ");
            String field = scanner.next();
            System.out.print("Nuevo valor: ");
            String value = scanner.next();
            String sql = "UPDATE empleados SET " + field + "='" + value + "' WHERE emp_no=" + empNo;
            statement.execute(sql);
            showMenu(statement, scanner);
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }
    private static void showAllEmpl(Statement statement, Scanner scanner) {
        try {
            String sql = "SELECT * FROM empleados";
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            System.out.println("emp_no, apellido, oficio, salario, dept_no");
            while (resultSet.next()) {
                System.out.printf("%d, %s, %s, %d, %d %n",
                        resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), resultSet.getInt(5));
            }
            if (scanner!=null) {
                showMenu(statement, scanner);
            }
        } catch (SQLException t) {
            t.printStackTrace();
        }
    }
}
