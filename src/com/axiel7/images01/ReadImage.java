/*
 * Realizado por Axel Lopez - 2DAM
 * @author: axiel7
 */
package com.axiel7.images01;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;

public class ReadImage extends JFrame {

    public ReadImage(Connection connection) {
        super("Ver Imagen");
        setBounds(100, 100, 460, 300);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JButton listButton = new JButton("Ver listado");
        listButton.setBounds(288, 25, 150, 23);
        contentPane.add(listButton);
        listButton.addActionListener(e -> {
            ListImages listImages = new ListImages(connection);
            listImages.setVisible(true);
        });

        JTextField textField = new JTextField();
        textField.setToolTipText("Nombre de la imagen");
        textField.setBounds(288, 65, 150, 23);
        contentPane.add(textField);

        JButton loadButton = new JButton("Cargar imagen");
        loadButton.setBounds(288, 95, 150, 23);
        contentPane.add(loadButton);
        loadButton.addActionListener(e -> {
            Image image = new Image(readImage(connection, textField.getText()));
            contentPane.add(image);
            contentPane.repaint();
        });

    }

    private File readImage(Connection connection, String name) {
        File file = new File("local_" + name);
        try {
            PreparedStatement st = connection.prepareStatement("SELECT imagen FROM imagenes WHERE nombre='" + name + "';");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Blob bytesImagen = rs.getBlob(1);
                InputStream is = bytesImagen.getBinaryStream();

                FileOutputStream fw = new FileOutputStream(file);

                // Bucle de lectura del blob y escritura en el fichero, de 1024
                // en 1024 bytes.
                byte[] bytes = new byte[1024];
                int read = is.read(bytes);
                while (read > 0) {
                    fw.write(bytes);
                    read = is.read(bytes);
                }
                fw.close();
                is.close();
            }
            st.close();
            rs.close();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
        return file;
    }

    public static class Image extends JPanel {
        private final File file;
        public Image(File file) {
            setSize(200, 200);
            this.file = file;
        }

        @Override
        public void paint(Graphics graphics) {
            Dimension height = getSize();

            //Se selecciona la imagen que tenemos en el paquete de la //ruta del programa
            ImageIcon Img = new ImageIcon(file.getAbsolutePath());

            //Se dibuja la imagen que tenemos en el paquete Images //dentro de un panel
            graphics.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);

            setOpaque(false);
            super.paintComponent(graphics);
        }
    }
}
