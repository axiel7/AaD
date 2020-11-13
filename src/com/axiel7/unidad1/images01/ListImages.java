/*
 * Realizado por Axel Lopez - 2DAM
 * @author: axiel7
 */
package com.axiel7.unidad1.images01;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ListImages extends JFrame {

    public ListImages(Connection connection) {
        super("Listado");
        setBounds(100, 100, 500, 300);

        //array de Strings con los títulos de las columnas
        String[] columnNames = getColumnNames(connection);

        //creamos el Modelo de la tabla con los datos anteriores
        DefaultTableModel dtm = new DefaultTableModel(null, columnNames);

        //se crea la Tabla con el modelo DefaultTableModel
        final JTable table = new JTable(dtm);

        // añade los datos de la tabla
        addTableData(connection, dtm);

        //se define el tamaño
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));

        //Creamos un JscrollPane y le agregamos la JTable
        JScrollPane scrollPane = new JScrollPane(table);

        //Agregamos el JScrollPane al contenedor
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private String[] getColumnNames(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM imagenes");
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columns = rsmd.getColumnCount();
            String[] columnNames = new String[columns];
            for (int i=1; i<=columns; i++) {
                columnNames[i-1] = rsmd.getColumnName(i);
            }
            statement.close();
            resultSet.close();
            return columnNames;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new String[0];
    }

    private void addTableData(Connection connection, DefaultTableModel dtm) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM imagenes");
            while (resultSet.next()) {
                Object[] row = { resultSet.getInt(1), resultSet.getBlob(2), resultSet.getString(3) };
                dtm.addRow(row);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
