package com.axiel7.unidad2.examen2;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
public class Ejercicio2 {
  public static void main(String[] args) throws IOException {      
   File ficheroIN = new File("empleados.dat");
   File ficheroOUT = new File("empleados.txt");
   
   Scanner teclado=new Scanner(System.in);
   int opcion=-1;
   
   do
   {
   System.out.println("Elige la opcion:");
   System.out.println("================");
   System.out.println("1.- Crear Fichero con datos");
   System.out.println("2.- Mostrar todos los datos");
   System.out.println("3.- Exportar todos los datos a FILE");
   System.out.println("4.- Localizar Empleado");
   System.out.println("4.- Actualizar Empleado");
   System.out.println("0.- Salir");
   System.out.println("Opcion= ??");
   opcion=teclado.nextInt();
   switch (opcion) {
   case 0: System.out.println("HASTA LUEGO");break;
   case 1: CrearFichero(ficheroIN); break;
   case 2: MostrarFile(ficheroIN); break;
   case 3: ExportarFile(ficheroIN,ficheroOUT); break;
   case 4: long posicion = LocalizarEmpleado(ficheroIN, "Genaro"); break;
   case 5: ActualizarEmpleado(ficheroIN, "Genaro", "Arturo",10,2000,"Jefecillo"); break;
   default: System.out.println("Opción Incorrecta"); break;
   
   } // fin switch
  } while (opcion!=0);// fin while
   
  }// fin MAIN()
   
   
  //##########################################################

private static void ExportarFile(File ficheroIN, File ficheroOUT) {
      try {
          FileWriter fw = new FileWriter(ficheroOUT);
          fw.write(MostrarFile(ficheroIN));
          fw.close();
      } catch (IOException e) {
          e.printStackTrace();
      }
}
//-----------------------------------------
private static long LocalizarEmpleado(File ficheroIN, String nombreaBuscar) {
    try {
        DataInputStream in = new DataInputStream(new FileInputStream(ficheroIN));
        String nombre, cargo;
        int dpto;
        double salario;

        long pos = 0;
        try {
            while (true) {
                nombre = in.readUTF();
                pos += 12;
                cargo = in.readUTF();
                pos += 12;

                dpto = in.readInt();
                pos += 4;
                salario = in.readDouble();
                pos += 8;

                if (nombreaBuscar.equalsIgnoreCase(nombre.trim())) {
                    return pos;
                }
            }
        } catch (EOFException eo) {}
    } catch (IOException e) {
        e.printStackTrace();
    }
    return -1;
}
//-----------------------------------------
private static String MostrarFile(File ficheroIN) throws IOException {
    DataInputStream in = new DataInputStream(new FileInputStream(ficheroIN));
    StringBuilder contenido = new StringBuilder();
    contenido.append("Nombre | Cargo | Dpto | Salario \n");
    System.out.println("Nombre | Cargo | Dpto | Salario");

    String nombre, cargo;
    int dpto;
    double salario;

    try {
        while (true) {
            nombre = in.readUTF();
            cargo = in.readUTF();

            dpto = in.readInt();
            salario = in.readDouble();

            String datos = nombre + " | " + cargo + " | " + dpto + " | " + salario;
            System.out.println(datos);
            contenido.append(datos).append("\n");
        }
    } catch (EOFException eo) {}
	in.close();
    return contenido.toString();
}
//-----------------------------------------
private static void ActualizarEmpleado(File fileIN, String nombreBuscado, String nuevoNombre, int nuevoDep, double nuevoSalario, String nuevoCargo) throws IOException {
      long pos = LocalizarEmpleado(fileIN, nombreBuscado);

      if (pos!=-1) {
          RandomAccessFile raf = new RandomAccessFile(fileIN, "rw");

          raf.seek(pos);

          raf.writeUTF(nuevoNombre);
          raf.writeUTF(nuevoCargo);
          raf.writeInt(nuevoDep);
          raf.writeDouble(nuevoSalario);
      }
      else {
          System.out.println("Empleado no encontrado.");
      }
}
//-----------------------------------------
private static void CrearFichero(File ficheroIN) throws FileNotFoundException {
	// TODO Auto-generated method stub
	
	//declara el fichero de acceso aleatorio
	   RandomAccessFile file = new RandomAccessFile(ficheroIN, "rw");
	   //arrays con los datos
	   String nombre[] = {"Juan Diego","Genaro","Lucia","Sergio",
	                        "Alejandro Manuel","Pedro Luis", "Antonio"};//nombre 
	   int dep[] = {1, 2, 4, 3, 2, 1, 4};       //departamentos
	   double salario[]={1000.45, 2400.60, 3000.0, 1500.56, 
	                     2200.0, 1435.87, 2000.0};//salarios
	   String cargo[]={"Empleado","Coordinador","Auxiliar","Auxiliar","Jefe","Empleado","Auxiliar"};
	   
	   int n=nombre.length;//numero de elementos del array
	   
	   for (int i=0;i<n; i++){ //recorro los arrays          	  
		 
		 try {
			 String NombreString = String.format("%1$-10s",nombre[i]);
			 NombreString=NombreString.substring(0, 10);
			 file.writeUTF(NombreString);//insertar apellido
			 
			 String cargoString = String.format("%1$-10s",cargo[i]);
			 cargoString=cargoString.substring(0, 10);
			 file.writeUTF(cargoString);//insertar cargo
			 
			 file.writeInt(dep[i]);       //insertar departamento
			 file.writeDouble(salario[i]);//insertar salario
			 
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//insertar nombre
		
		 
	   }     
	   try {
		file.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  //cerrar fichero 

	
}

//######################################
}// FIN clase .JAVA