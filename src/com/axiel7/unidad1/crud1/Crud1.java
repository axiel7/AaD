/*
 * Realizado por Axel Lopez - 2DAM
 * @author: axiel7
 */
package com.axiel7.unidad1.crud1;

import com.axiel7.unidad1.examen1.Menu;

import javax.swing.*;
import java.sql.*;

public class Crud1 extends JFrame {

	private JTextField textId;
	private JTextField textName;
	private JTextField textAge;
	private JTextField textAddress;
	private JTextField textSalary;
	private JButton buttonModify;

	public static void main(String[] args) {
		try {
			Class.forName("org.sqlite.JDBC");
			String path = Menu.class.getResource("/com/axiel7/unidad1/crud1/company.db").getPath();
			Connection connection = DriverManager.getConnection("jdbc:sqlite:"+path);
			Statement statement = connection.createStatement();

			Crud1 window = new Crud1(statement);
			window.setVisible(true);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public Crud1(Statement statement) {
		super("CRUD");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		setContents(statement, contentPane);
	}

	private void setContents(Statement statement, JPanel panel) {
		
		JLabel companyLabel = new JLabel("Compañía");
		companyLabel.setBounds(25, 0, 100, 50);
		panel.add(companyLabel);
		
		JLabel idLabel = new JLabel("ID ");
		idLabel.setBounds(10, 45, 24, 25);
		panel.add(idLabel);
		
		JLabel nameLabel = new JLabel("Nombre");
		nameLabel.setBounds(10, 70, 45, 25);
		panel.add(nameLabel);
		
		textId = new JTextField();
		textId.setBounds(80, 45, 85, 20);
		textId.setColumns(10);
		panel.add(textId);
		
		textName = new JTextField();
		textName.setBounds(80, 70, 85, 20);
		textName.setColumns(10);
		panel.add(textName);

		JLabel ageLabel = new JLabel("Edad");
		ageLabel.setBounds(10, 105, 45, 15);
		panel.add(ageLabel);

		textAge = new JTextField();
		textAge.setBounds(80, 100, 85, 20);
		textAge.setColumns(10);
		panel.add(textAge);

		JLabel addressLabel = new JLabel("Dirección");
		addressLabel.setBounds(10, 130, 65, 15);
		panel.add(addressLabel);

		textAddress = new JTextField();
		textAddress.setBounds(80, 130, 85, 20);
		textAddress.setColumns(10);
		panel.add(textAddress);

		JLabel salaryLabel = new JLabel("Salario");
		salaryLabel.setBounds(10, 160, 45, 15);
		panel.add(salaryLabel);

		textSalary = new JTextField();
		textSalary.setBounds(80, 160, 85, 20);
		textSalary.setColumns(10);
		panel.add(textSalary);
		
		JButton searchButton = new JButton("Buscar");
		searchButton.setBounds(190, 45, 90, 25);
		panel.add(searchButton);
		searchButton.addActionListener(e -> searchCompany(statement));
		
		JButton addButton = new JButton("Agregar");
		addButton.setBounds(10, 210, 90, 25);
		panel.add(addButton);
		addButton.addActionListener(e -> addEntry(statement));
		
		buttonModify = new JButton("Modificar");
		buttonModify.setEnabled(false);
		buttonModify.setBounds(110, 210, 90, 25);
		panel.add(buttonModify);
		buttonModify.addActionListener(e -> modifyEntry(statement));
		
		JButton deleteButton = new JButton("Borrar");
		deleteButton.setBounds(210, 210, 90, 25);
		panel.add(deleteButton);
		deleteButton.addActionListener(e -> deleteEntry(statement));
	}

	private void searchCompany(Statement statement) {
		try {
			ResultSet rs = statement.executeQuery ( "SELECT * FROM COMPANY WHERE ID = " + textId.getText());

			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Compañía no encontrada");
				return;
			}

			String name = rs.getString ( "name");
			int age = rs.getInt ( "age");
			String address = rs.getString ( "address");
			float salary = rs.getFloat ( "salary");

			textName.setText(name);
			textAge.setText(String.valueOf(age));
			textAddress.setText(address);
			textSalary.setText(String.valueOf(salary));
			buttonModify.setEnabled(true);

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	private void addEntry(Statement statement) {
		try {
			if (textId.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debes insertar un ID");
			}

			if (textName.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debes insertar un Nombre");
			}

			if (textAge.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debes insertar una Edad");
			}

			if (textAddress.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debes insertar una Dirección");
			}

			if (textSalary.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Debes insertar un Salario");
			}

			String sql = "INSERT INTO COMPANY (ID, NAME, AGE, ADDRESS, SALARY)" +
					" VALUES (" + textId.getText() + ",'" + textName.getText() +
					"'," + textAge.getText() + ",'" + textAddress.getText() + "',"
					+ textSalary.getText() + ");";

			int result = statement.executeUpdate(sql);

			if (result > 0) {
				JOptionPane.showMessageDialog(null, "Registro agregado correctamente");
				clearTextFields();
			} else {
				JOptionPane.showMessageDialog(null, "No se pudo agregar el registro");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void modifyEntry(Statement statement) {
		try {
			String sql = "UPDATE COMPANY set NAME = '" + textName.getText() + "'," +
					" AGE = " + textAge.getText() + ", ADDRESS = '" + textAddress.getText() + "', SALARY = " + textSalary.getText() +
					" WHERE ID = " + textId.getText();
			statement.executeUpdate (sql);
			JOptionPane.showMessageDialog(null, "Datos modificados con éxito");

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	private void deleteEntry(Statement statement) {
		try {
			String sql = "DELETE from COMPANY where ID =" + textId.getText();
			statement.executeUpdate(sql);
			JOptionPane.showMessageDialog(null, "Borrado realizado con éxito");
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	private void clearTextFields() {
		textId.setText(null);
		textName.setText(null);
		textAge.setText(null);
		textAddress.setText(null);
		textSalary.setText(null);
	}
}
