/*
 * Realizado por Axel Lopez - 2DAM
 * @author: axiel7
 */
package com.axiel7.images01;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.sql.*;

public class SaveImage extends JFrame {

    private final JPanel contentPane;
    private final JTextField textFile;
    private final JTextArea textCompleted;

    public SaveImage(Connection connection) {
        super("Guardar Imagen");
        setBounds(100, 100, 460, 300);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        textFile = new JTextField();
        textFile.setToolTipText("Ruta de la imagen");
        textFile.setBounds(52, 26, 209, 23);
        contentPane.add(textFile);
        textFile.setColumns(10);

        textCompleted = new JTextArea("Subida completada!");
        textCompleted.setBounds(52, 56, 160, 23);
        textCompleted.setVisible(false);
        contentPane.add(textCompleted);

        JButton chooseButton = new JButton("Seleccionar...");
        chooseButton.setBounds(288, 25, 150, 23);
        contentPane.add(chooseButton);

        JButton submitButton = new JButton("Subir fichero");
        submitButton.setBounds(288, 55, 150, 23);
        submitButton.setVisible(false);
        contentPane.add(submitButton);

        chooseButton.addActionListener(e -> {
            File file = chooseFile();
            // mostrar botón de subida una vez seleccionado un fichero
            if (!textFile.getText().isEmpty()) { submitButton.setVisible(true); }
            submitButton.addActionListener(e1 -> {
                insertImg(connection, file);
                textCompleted.setVisible(true); // mostrar texto de operación exitosa
            });
        });
    }

    private File chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(".jpg, .jpeg, .png", "png", "jpg", "jpeg");
        fileChooser.setFileFilter(filter);
        int selection = fileChooser.showOpenDialog(contentPane); // muestra dialogo y guarda resultado
        if (selection==JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile(); //obtiene fichero
            textFile.setText(file.getAbsolutePath()); //visualiza el path del fichero
            return file;
        }
        else {
            return new File("");
        }
    }
    private static void insertImg(Connection connection, File file) {
        try {
            FileInputStream is = new FileInputStream(file);
            // Se prepara un statement para añadirle valores posteriormente
            PreparedStatement statement = connection.prepareStatement("INSERT INTO imagenes VALUES(null,?,?)");
            statement.setBlob(1, is);
            statement.setString(2, file.getName());
            // ejecuta y cierra
            statement.execute();
            is.close();
            statement.close();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
