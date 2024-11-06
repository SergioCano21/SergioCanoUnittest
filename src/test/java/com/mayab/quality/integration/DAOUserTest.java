package com.mayab.quality.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.junit.jupiter.api.Test;

import com.mayab.quality.unittest.dao.DAOUser;

class DAOUserTest extends DBTestCase {

	public DAOUserTest() {
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://localhost:3307/calidad2024");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "123456");
	}
	
	/*@BeforeEach
	void setup() {
		DAOUser dao = new DAOUser();
		IDatabaseConnection connection = getConnection();
	}
	@Test
	void test() {
		fail("Not yet implemented");
	}
	*/

}
