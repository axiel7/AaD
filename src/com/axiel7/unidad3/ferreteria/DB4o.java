package com.axiel7.unidad3.ferreteria;

import java.io.File;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;



import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

public class DB4o {

	static String DBferreteria = "DBferreteria.db4o";
	static ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);
	static Scanner teclado = new Scanner(System.in);
	
public static void main(String[] args) {
	int opcion=-1;
	do
	{
		System.out.println("Elige la opcion:");
		System.out.println("================");
		System.out.println(" 1.- Añadir Cliente");
		System.out.println(" 2.- Mostrar Clientes");
		System.out.println(" 3.- Modificar Cliente");
		System.out.println();
		System.out.println(" 4.- Añadir Artículo nuevo");
		System.out.println(" 5.- Mostrar Artículos");
		System.out.println(" 6.- Modificar Artículo");
		System.out.println(" 7.- Reponer Artículo");
		System.out.println();
		System.out.println(" 8.- Hacer Venta");
		System.out.println(" 9.- Anular Venta");
		System.out.println("10.- Mostrar todos los artículos de los que haya que pedir unidades al almacen por tener pocas unidades en la ferretería");
		System.out.println("11.- Mostrar los nombres de los artículos vendidos entre 2 fechas que se piden por teclado");
		System.out.println("12.- Localiza a todos los clientes que han hecho una compra en los tres últimos meses");
		System.out.println("13.- Muestra las localidades de los clientes y los nombres de los clientes que han realizado una factura por un importe superior a 50€");
		System.out.println();
		System.out.println("0.- Salir");
		System.out.println("Opcion= ??");
		opcion=teclado.nextInt();
		String basura = teclado.nextLine();

		switch (opcion) {
		case 0: System.out.println("HASTA LUEGO");break;
		case 1: anadirCliente(); break;
		case 2: mostrarClientes(); break;
		case 3: modificarCliente(); break;
		case 4: anadirArticulo(); break;
		case 5: mostrarArticulos(); break;
		case 6: modificarArticulo(); break;
		case 7: reponerArticulo(); break;
		case 8: hacerVenta(); break;
		case 9: anularVenta(); break;
		case 10: articulosAreponer(); break;
		case 11: ventasRealizadas(); break;
		case 12: ultimosClientes(); break;
		case 13: ventasMasde50(); break;
		
		default: ; break;

		} // fin switch
	} while (opcion!=0);// fin while

	db.close(); // cerrar base de datos


		
	
		} //final del MAIN()

private static void ventasMasde50() {
	// Mostrará localidades de los clientes y los nombres de los clientes que han realizado una factura por un importe superior a 50€.
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);

	List<Venta> ventas = db.query(new Predicate<>() {
		@Override
		public boolean match(Venta venta) {
			return (venta.getUnidades_vendidas() * venta.getPvp_unidad() >= 50);
		}
	});

	for (Venta venta: ventas) {
		Cliente clienteNull = new Cliente(null,venta.getCliente().getDni(),null);
		ObjectSet<Cliente> result = db.queryByExample(clienteNull);
		if (result.size() == 0) {
			System.out.println("No existe ese DNI");
		}
		else {
			Cliente clienteQ = result.next();
			System.out.println("Datos del cliente:\nNombre: " + clienteQ.getNombre() + "\nLocalidad: " + clienteQ.getLocalidad());
		}
	}
	db.close();
}

private static void ultimosClientes() {
	// Mostrará los nombres de clientes que han comprado en los 3 últimos meses a partir de la fecha de hoy.
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);
	Calendar calendar = new GregorianCalendar();
	int minMonth = calendar.get(Calendar.MONTH) + 1;
	int minYear = calendar.get(Calendar.YEAR);

	// Restar 3 meses
	switch (minMonth) {
		case 1 -> minMonth = 10;
		case 2 -> minMonth = 11;
		case 3 -> minMonth = 12;
		default -> minMonth -= 3;
	}

	Venta ventaNull = new Venta(null,null,0,0);
	ObjectSet<Venta> result = db.queryByExample(ventaNull);
	if (result.size() == 0)
		System.out.println("No existen registros de ventas.");
	else {
		System.out.println("Número de Ventas: " + result.size());
		while (result.hasNext()) {
			Venta venta = result.next();
			Calendar fechaVenta = venta.getFecha_venta();
			int month = fechaVenta.get(Calendar.MONTH);
			int year = fechaVenta.get(Calendar.YEAR);

			if (year == minYear && month > minMonth){
				System.out.println(venta.toString());
				System.out.println();
			}
		}
	}
	db.close();
}

private static void ventasRealizadas() {
	// Mostrará las ventas realizadas
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);

	System.out.println("Introduzca fecha min (dd/mm/yyyy): ");
	String minDate = teclado.nextLine();
	String[] minDateSplit = minDate.split("/");
	int minDay = Integer.parseInt(minDateSplit[0]);
	int minMonth = Integer.parseInt(minDateSplit[1]);
	int minYear = Integer.parseInt(minDateSplit[2]);

	System.out.println("Introduzca fecha max (dd/mm/yyyy): ");
	String maxDate = teclado.nextLine();
	String[] maxDateSplit = maxDate.split("/");
	int maxDay = Integer.parseInt(maxDateSplit[0]);
	int maxMonth = Integer.parseInt(maxDateSplit[1]);
	int maxYear = Integer.parseInt(maxDateSplit[2]);

	Venta ventaNull = new Venta(null,null,0,0);
	ObjectSet<Venta> result = db.queryByExample(ventaNull);
	if (result.size() == 0)
		System.out.println("No existen registros de ventas.");
	else {
		System.out.println("Número de Ventas: " + result.size());
		while (result.hasNext()) {
			Venta venta = result.next();
			Calendar fechaVenta = venta.getFecha_venta();
			int day = fechaVenta.get(Calendar.DAY_OF_MONTH);
			int month = fechaVenta.get(Calendar.MONTH);
			int year = fechaVenta.get(Calendar.YEAR);

			// Mostrar fecha si entra en el intervalo indicado
			if (year >= minYear && year <= maxYear) {
				if(month >= minMonth && month <= maxMonth) {
					if(day >= minDay && day <= maxDay){
						System.out.println(venta.toString());
						System.out.println();
					}
				}
			}
		}
	}
	db.close();
}

private static void articulosAreponer() {
	// Mostrará todos los artículos cuyo stock_actual es inferior al stock_minmo
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);
	List<Articulo> articulos = db.query(new Predicate<>() {
		@Override
		public boolean match(Articulo articulo) {
			return (articulo.getStock_actual() < 3);
		}
	});

	for (Articulo articulo : articulos){
		System.out.println("Descripción: " + articulo.getDescripcion()
				+ "\nCódigo: " + articulo.getCod_articulo()
				+ "\nStock: " + articulo.getStock_actual());
	}
	db.close();
}

private static void anularVenta() {
	// Pedirá un dni de cliente y una fecha_venta, mostrará esa venta si la hay y procederá a borrarla. Habrá que volver a sumar las unidades devueltas en el stock_actual
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);

	System.out.println("Introduzca DNI: ");
	String dni = teclado.nextLine();

	Cliente cliente = buscarCliente(dni, db);
	if (cliente != null) {
		System.out.println("Introduce código del artículo: ");
		String cod = teclado.nextLine();

		Articulo articulo = buscarArticulo(cod, db);
		if (articulo != null) {
			System.out.println("Unidades disponibles: " + articulo.getStock_actual());
			System.out.println("Introduzca unidades a comprar: ");
			int unidades = teclado.nextInt();
			System.out.println("Introduzca precio por unidad: ");
			int precio = teclado.nextInt();

			// Eliminar venta
			Venta venta = new Venta(cliente, articulo, unidades, precio);
			db.delete(venta);

			// Actualizar stock
			articulo.setStock_actual(articulo.getStock_actual() + venta.getUnidades_vendidas());
			db.store(articulo);
		}
	}
	db.close();
}

private static void hacerVenta() {
	// A partir de la fecha de hoy, pide dni de cliente, pide artículo y nº unidades quese venden. Hay que descontar el numero de unidades del stock.
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);
	System.out.println("Introduzca DNI: ");
	String dni = teclado.nextLine();
	Cliente cliente = buscarCliente(dni,db);

	//Comprobar que Artículo y Cliente existen
	if (cliente != null) {
		System.out.println("Introduzca código del artículo: ");
		String cod = teclado.nextLine();

		Articulo articulo = buscarArticulo(cod,db);
		if (articulo != null) {
			System.out.println("Unidades disponibles: " + articulo.getStock_actual());
			System.out.println("Introduzca unidades a comprar: ");
			int unidades = teclado.nextInt();
			System.out.println("Introduzca precio por unidad: ");
			int precio = teclado.nextInt();

			//Actualizar stock
			articulo.setStock_actual(articulo.getStock_actual() - unidades);
			db.store(articulo);

			//Almacenar venta
			Venta ven = new Venta(cliente, articulo, unidades, precio);
			db.store(ven);
		}
	}
	db.close();
}

private static void reponerArticulo() {
	// Pedirá cod_articulo a reponer y unidades adquiridas. Hay que actualizar stock_actual
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);

	System.out.println("Introduzca código del artículo a modificar: ");
	String cod = teclado.nextLine();

	Articulo articulo = buscarArticulo(cod,db);
	if (articulo != null) {
		System.out.println("Introduzca número de unidades a añadir: ");
		int unidades = teclado.nextInt();
		articulo.setStock_actual(articulo.getStock_actual() + unidades);
		db.store(articulo);
	}
	db.close();
}

private static void modificarArticulo() {
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);
	System.out.println("Introduzca código del artículo a modificar: ");
	String cod = teclado.nextLine();

	Articulo articulo = buscarArticulo(cod,db);
	if (articulo != null) {
		System.out.println("Introduzca nueva descripción: ");
		String description = teclado.nextLine();
		System.out.println("Introduzca nuevas unidades: ");
		int unidades = teclado.nextInt();
		articulo.setDescripcion(description);
		articulo.setStock_actual(unidades);
		db.store(articulo);
	}
	db.close();
}

private static void mostrarArticulos() {
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);
	Articulo articuloNull = new Articulo(null, null, 0);
	ObjectSet<Articulo> result = db.queryByExample(articuloNull);
	if (result.size() == 0)
		System.out.println("No hay artículos");
	else {
		System.out.println("Número de artículos: " + result.size());
		while (result.hasNext()) {
			Articulo articulo = result.next();
			articulo.mostrarDatos();
			System.out.println();
		}
	}
	db.close();
}

private static void anadirArticulo() {
	// No se podrá añadir a ningún articulo si el cod_articulo está repetido.
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);
	System.out.println("Introduzca código del nuevo artículo: ");
	String cod = teclado.nextLine();

	// Comprueba que el articulo no existe
	if (buscarArticulo(cod, db) == null) {
		System.out.println("Descripción: ");
		String description = teclado.nextLine();
		System.out.println("Unidades de stock: ");
		int unidades = teclado.nextInt();
		Articulo articulo = new Articulo(description, cod, unidades);
		db.store(articulo);
		System.out.println("ARTÍCULO AÑADIDO");
	} else {
		System.out.println("Ya existe un artículo con ese código");
	}
	db.close();
}

private static void modificarCliente() {
	// Pedirá un dni y pedirá nuevos datos para ese cliente.
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);
	System.out.println("Introduzca DNI del cliente a modificar: ");
	String dni = teclado.nextLine();

	Cliente cliente = buscarCliente(dni, db);
	if (cliente != null) {
		System.out.println("Nombre: ");
		String nom = teclado.nextLine();
		System.out.println("Localidad: ");
		String loc = teclado.nextLine();
		cliente.setNombre(nom);
		cliente.setLocalidad(loc);
		db.store(cliente);
	}
	db.close();
}

private static void mostrarClientes() {
	// Mostrará todos los clientes almacenados con todos sus datos.
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);
	Cliente clienteNull = new Cliente(null, null, null);
	ObjectSet<Cliente> result = db.queryByExample(clienteNull);
	if (result.size() == 0)
		System.out.println("No hay clientes");
	else {
		System.out.println("Número de Clientes: " + result.size());
		while (result.hasNext()) {
			Cliente cliente = result.next();
			cliente.mostrarDatos();
			System.out.println();
		}
	}
	db.close();
}

private static void anadirCliente() {
	// No se podrá añadir a ningún cliente si el dni está repetido.
	ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), DBferreteria);
	System.out.println("Introduzca DNI del nuevo cliente: ");
	String dni = teclado.nextLine();

	// Comprueba que el cliente no existe
	if (buscarCliente(dni, db) == null) {
		System.out.println("Nombre: ");
		String nombre = teclado.nextLine();
		System.out.println("Localidad: ");
		String localidad = teclado.nextLine();
		Cliente cliente = new Cliente(nombre, dni, localidad);
		db.store(cliente);
		System.out.println("CLIENTE AÑADIDO");
	}
	else {
		System.out.println("Ya existe un cliente con ese DNI");
	}
	db.close();
}

	public static Cliente buscarCliente(String dni, ObjectContainer db) {
		Cliente cliente = new Cliente(null, dni, null);
		ObjectSet<Cliente> result = db.queryByExample(cliente);
		if (result.size() == 0) {
			System.out.println("No existe cliente con DNI " + dni);
			return null;
		}
		else {
			return result.next();
		}
	}

	public static Articulo buscarArticulo(String cod, ObjectContainer db) {
		Articulo articulo = new Articulo(null, cod, 0);
		ObjectSet<Articulo> result = db.queryByExample(articulo);
		if (result.size() == 0) {
			System.out.println("No existe artículo " + cod);
			return null;
		}
		else {
			return result.next();
		}
	}

} // final de la clase
