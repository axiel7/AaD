/*
 * Realizado por Axel Lopez - 2DAM
 * @author: axiel7
 */
package com.axiel7.images01;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainMenu extends JFrame {

    public MainMenu(Connection connection) {
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 460, 300);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JButton saveButton = new JButton("Subir imagen");
        contentPane.add(BorderLayout.WEST, saveButton);
        saveButton.addActionListener(e -> {
            SaveImage saveImage = new SaveImage(connection);
            saveImage.setVisible(true);
        });

        JButton showButton = new JButton("Ver Imagen");
        contentPane.add(BorderLayout.EAST, showButton);
        showButton.addActionListener(e -> {
            ReadImage readImage = new ReadImage(connection);
            readImage.setVisible(true);
        });

    }

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

            MainMenu mainMenu = new MainMenu(conexion);
            mainMenu.setVisible(true);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
