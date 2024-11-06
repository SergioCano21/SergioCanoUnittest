package com.mayab.quality.unittest.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.mayab.quality.unittest.model.User;

public class DAOUser implements IDAOUser {

	public Connection getConnectionMySQL() {

		Connection con = null;
		try {
			// Establish the driver connector
			Class.forName("com.mysql.cj.jdbc.Driver");
			// Set the URI for connecting the MySql database
			String dbURL = "jdbc:mysql://localhost:3307/calidad2024";
			String strUserID = "root";
            String strPassword = "123456";
            con = DriverManager.getConnection(dbURL,strUserID,strPassword);
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}

	@Override
	public int save(User user) {
		Connection connection = getConnectionMySQL();
		int result = -1;
		try {
			// Declare statement query to run
			PreparedStatement preparedStatement;
			preparedStatement = connection
					.prepareStatement("INSERT INTO usuarios(name, email, password, isLogged) values(?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
			// Set the values to match in the ? on query
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setBoolean(4, user.getIsLogged());

			// Return the result of connection add statement
			if (preparedStatement.executeUpdate() >= 1) {
				try (ResultSet rs = preparedStatement.getGeneratedKeys()){
					if(rs.next()) {
						result = rs.getInt(1);
					}
				}
			}
			System.out.println("\n");
			System.out.println("Usuario añadido con exito");
			System.out.println(">> Return: " + result + "\n");
			// Close connection with the database
			connection.close();
			preparedStatement.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		// Return statement
		return result;
	}

	@Override
	public boolean deleteById(int id) {
		Connection connection = getConnectionMySQL();
		boolean result = false;

		try {
			// Declare statement query to run
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement("Delete from usuarios WHERE id = ?");
			// Set the values to match in the ? on query
			preparedStatement.setInt(1, id);

			// Return the result of connection and statement
			if (preparedStatement.executeUpdate() >= 1) {
				result = true;
			}
			System.out.println("\n");
			System.out.println("Alumno eliminado con exito");
			System.out.println(">> Return: " + result + "\n");
			// Close connection with the database
			connection.close();
			preparedStatement.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		// Return statement
		return result;
	}

	@Override
	public User updateUser(User a) {
		Connection connection = getConnectionMySQL();
		boolean result = false;

		try {
			// Declare statement query to run
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement("UPDATE alumnos_tbl SET email = ? WHERE id = ?");
			// Set the values to match in the ? on query
			preparedStatement.setString(1, a.getEmail());
			//preparedStatement.setString(2, a.getId());

			// Return the result of connection and statement
			if (preparedStatement.executeUpdate() >= 1) {
				result = true;
			}
			System.out.println("\n");
			System.out.println("Correo de alumno con ID: " + a.getId() + " actualizado");
			System.out.println(">> Return: " + result + "\n");
			// Close connection with the database
			connection.close();
			preparedStatement.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		// Return statement
		return null;
	}

	public User findbyUserName(String name) {
		Connection connection = getConnectionMySQL();
		PreparedStatement preparedStatement;
		ResultSet rs;
		boolean result = false;

		User retrieved = null;

		try {
			// Declare statement query to run
			preparedStatement = connection.prepareStatement("SELECT * from usuarios WHERE name = ?");
			// Set the values to match in the ? on query
			preparedStatement.setString(1, name);
			rs = preparedStatement.executeQuery();

			// Obtain the pointer to the data in generated table
			rs.next();

			String retrivedId = rs.getString(1);
			String retrivedName = rs.getString(2);
			String retrivedEmail = rs.getString(3);
			int retrivedAge = rs.getInt(4);

			//retrieved = new User(retrivedId, retrivedName, retrivedEmail, retrivedAge);

			// Return the values of the search
			System.out.println("\n");
			System.out.println("---Alumno---");
			System.out.println("ID: " + retrieved.getId());
			System.out.println("Nombre: " + retrieved.getName());
			System.out.println("Email: " + retrieved.getEmail());
			System.out.println("Email: " + retrieved.getEmail() + "\n");
			// Close connection with the database
			connection.close();
			rs.close();
			preparedStatement.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		// Return statement
		return retrieved;
	}

	@Override
	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
