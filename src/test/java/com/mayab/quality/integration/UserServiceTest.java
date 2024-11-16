package com.mayab.quality.integration;

import java.io.FileInputStream;
import java.io.File;

import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mayab.quality.unittest.dao.DAOUser;
import com.mayab.quality.unittest.model.User;
import com.mayab.quality.unittest.service.UserService;

class UserServiceTest extends DBTestCase{
	
	private DAOUser dao;
	private UserService service;

	public UserServiceTest(){
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3307/calidad2024");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "123456");
	}

	@BeforeEach
	protected void setUp() throws Exception {
		dao = new DAOUser(); 
		service = new UserService(dao);
		IDatabaseConnection connection = getConnection(); 
		try {
			DatabaseOperation.TRUNCATE_TABLE.execute(connection,getDataSet());
			DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
			
		}catch(Exception e) {
			fail("Error in setup: "+ e.getMessage());
		}finally {
			connection.close();
		}
	}
	
	protected IDataSet getDataSet() throws Exception
    {
        return new FlatXmlDataSetBuilder().build(new FileInputStream("src/resources/initDB.xml"));
    }
	
	@Test
	public void create_user_happy_path() {
		User usuario = new User("username2", "correo2@correo.com", "123456789");
		
		service.createUser(usuario.getName(), usuario.getEmail(), usuario.getPassword());
		
		try {
			IDatabaseConnection conn = getConnection();
			conn.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);
			IDataSet databaseDataSet = conn.createDataSet(); 
			String[] tablas = databaseDataSet.getTableNames();
			
			ITable actualTable = databaseDataSet.getTable("usuarios");
			
			// Read XML with the expected result
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/create.xml"));
			ITable expectedTable = expectedDataSet.getTable("usuarios");
			
			Assertion.assertEquals(expectedTable, actualTable);
			
		} catch (Exception e) {
			fail("Error in insert test: " + e.getMessage());
		}	
	}

	@Test
	public void create_user_when_email_exists() {
		User usuario = new User("username2", "correo3@correo.com", "123456");
		
		//Se crea el primer usuario con el correo
		service.createUser(usuario.getName(), usuario.getEmail(), usuario.getPassword());
		
		try {
			IDatabaseConnection conn = getConnection();
			conn.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);
			IDataSet databaseDataSet = conn.createDataSet(); 
			String[] tablas = databaseDataSet.getTableNames();
			
			ITable actualTableBefore = databaseDataSet.getTable("usuarios");
			int rowsBefore = actualTableBefore.getRowCount();
			
			//Se intenta volver a crear un usuario con el mismo correo
			User newUser = service.createUser(usuario.getName(), usuario.getEmail(), usuario.getPassword());
			assertNull(newUser);
			
			//Se vuelve a obtener el numero de filas de la BD
			ITable actualTableAfter = databaseDataSet.getTable("usuarios");
			int rowsAfter = actualTableAfter.getRowCount();
			assertEquals(rowsBefore, rowsAfter);
			
		} catch (Exception e) {
			fail("Error in insert test: " + e.getMessage());
		}	
	}
	
	@Test
	public void create_user_when_password_long() {
		User usuario = new User("username2", "correo@correo.com", "1234567890987654321");
		
		try {
			IDatabaseConnection conn = getConnection();
			conn.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);
			IDataSet databaseDataSet = conn.createDataSet(); 
			String[] tablas = databaseDataSet.getTableNames();
			
			ITable actualTableBefore = databaseDataSet.getTable("usuarios");
			int rowsBefore = actualTableBefore.getRowCount();
			
			User newUser = service.createUser(usuario.getName(), usuario.getEmail(), usuario.getPassword());
			assertNull(newUser);
			
			//Se vuelve a obtener el numero de filas de la BD
			ITable actualTableAfter = databaseDataSet.getTable("usuarios");
			int rowsAfter = actualTableAfter.getRowCount();
			assertEquals(rowsBefore, rowsAfter);
			
		} catch (Exception e) {
			fail("Error in insert test: " + e.getMessage());
		}	
	}
	
	@Test
	public void create_user_when_password_short() {
		User usuario = new User("username2", "correo@correo.com", "123");
		
		try {
			IDatabaseConnection conn = getConnection();
			conn.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);
			IDataSet databaseDataSet = conn.createDataSet(); 
			String[] tablas = databaseDataSet.getTableNames();
			
			ITable actualTableBefore = databaseDataSet.getTable("usuarios");
			int rowsBefore = actualTableBefore.getRowCount();
			
			User newUser = service.createUser(usuario.getName(), usuario.getEmail(), usuario.getPassword());
			assertNull(newUser);
			
			//Se vuelve a obtener el numero de filas de la BD
			ITable actualTableAfter = databaseDataSet.getTable("usuarios");
			int rowsAfter = actualTableAfter.getRowCount();
			assertEquals(rowsBefore, rowsAfter);
			
		} catch (Exception e) {
			fail("Error in insert test: " + e.getMessage());
		}	
	}
	
	@Test
	public void find_user_by_email() {
		
		User user = service.findUserByEmail("correo3@correo.com");
		
		try {
			IDatabaseConnection conn = getConnection();
			conn.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, true);
			IDataSet databaseDataSet = conn.createDataSet(); 
			String[] tablas = databaseDataSet.getTableNames();
			
			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/initDB.xml"));
			
		} catch (Exception e) {
			fail("Error in insert test: " + e.getMessage());
		}	
	}
}
