package com.desktop.MoSchoolApp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * This class is used for all the database activities using MySQL DB server and
 * mysql-connector-j lib
 * 
 * Connection information any database
 *  [protocol]//[hosts][/database][?properties]
 *  protocol is the ordinary JDBC failover connection
 *  hosts include both host and port in the format host:port b
 *  databse is the name of database to which you want to connect
 *  properties A succession of global properties applying to all hosts, preceded by ? and written as key=value pairs separated by the symbol “&.”
 *  key and value are just strings. Proper type conversion and validation are performed internally in Connector/J.
 *  key is case-sensitive. Two keys differing in case only are considered conflicting, and it is uncertain which one will be used.
 *  Example jdbc:mysql://(host=myhost1,port=1111),(host=myhost2,port=2222)/db?key1=value1&key2=value2&key3=value3	 *  
 * 
 * ###MySQL related Java things###
 * connection info
 *  jdbc:mysql://localhost:3306/dbName?user=demo&password=demo1
 *  3306 is the default port for the ordinary MySQl database
 */
public class DBConnection {
	
	
	
	private static Connection connection;
	
	
	
	/* Steps for connecting to MySQL database using java
	 * Basics- DriverManager class manages the establishment of connections.
	 *         Specify to the DriverManager which JDBC drivers to try to make Connections with. 
	 *         The easiest way to do this is to use Class.forName() on the class that implements the java.sql.Driver interface. 
	 *         With MySQL Connector/J, the name of this class is com.mysql.cj.jdbc.Driver.
	 *         
	 *         After the driver has been registered with the DriverManager, 
	 *         you can obtain a Connection instance that is connected to a particular database by calling DriverManager.getConnection():
	 *        
	 *        Once a Connection is established, it can be used to create Statement 
	 *        and PreparedStatement objects, as well as retrieve metadata about the database. 
	 *        
	 *        Steps for getting the MySQL Connection
	 *        1. Register driver with DriverManager interface
	 *           Class.forName(com.mysql.cj.jdbc.Driver)
	 *        2. Get Connection from Driver manger
	 *           DriverManger.getConnection(URL url)
	 *            getConnection has 3 overloaded method as belows
	 *           
	 *            getConnection(String url)

                  getConnection(String url, Properties prop)

                  getConnection(String url, String user, String password)
                  URL is jdbc:mysql://localhost:3306/dtabaseName
                  user is username
                  password is any password for the mysql db    
                   
	 *  */
	
	public static Connection getDBConnection() {		
		
		String url="jdbc:mysql://localhost:3306/mo_school";
		String user="root";
		String password="root";
		
		try {
			
			//Register Driver with DriverManger interface not needed now a days
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Get connection from Driver Manger using URl ,user and password			
			connection=DriverManager.getConnection(url, user, password);
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;		
		
	}
	
	public static boolean checkAuthentication(String username,String password) {
		
		//Using PreparedStatement to make application more secure to SQL injection attack
		PreparedStatement ps=null;
		ResultSet rs=null;
		boolean result=false;
		
		String query="SELECT * from mo_school.admin where username=? and password=?";
		
		if(connection==null) {
			 connection = getDBConnection();
		}
		
		try {
			ps=connection.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
		    result= rs.next();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
