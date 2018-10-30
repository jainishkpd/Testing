package com.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import org.testng.annotations.Test;

public class MySqlConnection {

	@Test
	public void dbConnection() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Driver");
		Logger.getLogger("Driver loaded");

		try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdummy","root","root");
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("select e.Id, e.name, e.email, e.city, s.gender, s.Ammount from \r\n" + 
						"employee e\r\n" + 
						"inner join salary s on e.name = s.name\r\n" + 
						"WHERE s.gender = 'female' AND s.Ammount > 65000");) {
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5));
			}	
		} catch (SQLException e) {
			throw e;
		}
	}
}
