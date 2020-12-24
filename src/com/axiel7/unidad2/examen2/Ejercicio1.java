package com.axiel7.unidad2.examen2;

import java.io.*;
import java.util.Scanner;

public class Ejercicio1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre directorio: ");
        String directorio_pedido = scanner.next();
        File file = new File(directorio_pedido);

        if (file.exists()) { menu(file, scanner); }
        else { System.out.println("No existe."); }
    }

    private static void menu(File file, Scanner scanner) {
        int option;
        do {
            System.out.println("1. Mostrar ficheros\n2. Copiar contenido\n3. Borrar fichero\n4. Crear texto listado\n" +
                    "5. Mover ficheros con extension\n6. Salir");
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    System.out.print("Tamaño minimo en KB: ");
                    int minKb = scanner.nextInt();
                    mostrarFicheros(file, minKb);
                    break;
                case 2:
                    System.out.print("Ruta donde copiar contenido: ");
                    String pathCopiar = scanner.next();
                    try {
                        copiarContenido(file, new File(pathCopiar), "");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.print("Nombre fichero a borrar: ");
                    String aBorrar = scanner.next();
                    borrarFichero(file, aBorrar);
                    break;
                case 4:
                    System.out.print("Ruta donde crear el listado: ");
                    String rutaListado = scanner.next();
                    try {
                        crearText(file, new File(rutaListado + "listado.txt"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 5:
                    System.out.print("Extension de fichero: ");
                    String ext = scanner.next();
                    organizarPorExtension(file, ext);
                    break;
                case 6:
                    break;
            }
        } while (option!=6);
    }

    private static void mostrarFicheros(File file, int minKb) {
        if(file.isDirectory()) {
            File[] files = file.listFiles();

            if (files != null) { //comprueba si el directorio contiene ficheros
                for (File f : files) {
                    // .length devuelve en bytes por lo que multiplicamos por 1000
                    if (f.length() > (minKb * 1000L)) {
                        System.out.println("INFORMACIÓN SOBRE EL FICHERO:");
                        System.out.println("Nombre del fichero  : "+f.getName());
                        System.out.println("Ruta                : "+f.getPath());
                        System.out.println("Ruta absoluta       : "+f.getAbsolutePath());
                        System.out.println("Se puede leer       : "+f.canRead());
                        System.out.println("Se puede escribir   : "+f.canWrite());
                        System.out.println("Tamaño              : "+f.length());
                        System.out.println("Es un directorio    : "+f.isDirectory());
                        System.out.println("Es un fichero       : "+f.isFile());
                        System.out.println("Nombre del directorio padre: "+f.getParent());
                    }
                }
            }
        }
    }

    private static void copiarContenido(File origen, File destino, String ext) throws IOException {
        if (origen.isDirectory()) {
            if (!destino.exists()) {
                destino.mkdir();
            }
            String[] paths = origen.list();

            for (String s : paths) {
                if (s.contains(ext)) {
                    copiarContenido(new File(origen + File.separator + s),
                            new File(destino + File.separator + s), ext);
                }
            }
        }
        else if (origen.getName().contains(ext)) {
            InputStream in = new FileInputStream(origen);
            OutputStream out = new FileOutputStream(destino);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    private static void borrarFichero(File file, String aBorrar) {
        if (file.isDirectory()) {

            File[] files = file.listFiles();
            for (File f : files) {
                if (f.getName().equals(aBorrar)) {
                    if (f.isDirectory()) {
                        borrarDir(f);
                    }
                    else {
                        f.delete();
                    }
                }
            }
        }
    }

    private static void borrarDir(File dir) {
        if(dir.exists()) {
            File[] files = dir.listFiles();

            if (files != null) { //comprueba si el directorio contiene ficheros
                for (File f : files) {
                    if (f.isFile()) { // si es archivo pregunta si quiere borrar
                        f.delete();
                    }
                    //si es directorio se vuelve a llamar a la funcion para recorrer su contenido
                    else if (f.isDirectory()) {
                        borrarDir(f);
                    }
                }
            }
        }
    }

    private static void crearText(File file, File destino) throws IOException {
        FileWriter fw = new FileWriter(destino);

        fw.write(listaRutas(file));
        fw.close();
    }

    private static String listaRutas(File file) {
        File[] files = file.listFiles();
        StringBuilder rutas = new StringBuilder();

        for (File f : files) {
            String type = "";
            if (!f.isDirectory()) {
                type = "fichero";
            }
            else {
                type = "carpeta";
            }
            rutas.append(type).append(" ").append(f.getName()).append(" ").append(f.length() / 1000L).append(" ").append(f.lastModified()).append("\n");
        }
        return rutas.toString();
    }

    private static void organizarPorExtension(File file, String ext) {
        File extension = new File(file.getAbsolutePath() + File.separator + "extension");
        if (!extension.exists()) {
            extension.mkdir();
        }
        try {
            copiarContenido(file, extension, ext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
