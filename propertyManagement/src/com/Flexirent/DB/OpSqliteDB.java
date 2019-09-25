package com.Flexirent.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class OpSqliteDB {
    
    private static final String Class_Name = "org.sqlite.JDBC";
    private static final String DB_URL = "jdbc:sqlite:C:\\Users\\wujj\\Desktop\\RMIT\\S2\\AP\\ass2\\FlexiRent\\shujuku";
//C:\Users\wujj\Desktop\RMIT\S2\AP\ass2\FlexiRent
    public  Connection getCon()
	{
		Connection connection=null;
		try {
			Class.forName(Class_Name);
           connection = DriverManager.getConnection(DB_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return connection;
	}
	
	
	public  void close(Statement stmt,Connection con)throws Exception{
		if(stmt!=null){
			stmt.close();
			if(con!=null){
				con.close();
			}
		}
		}
	public static void main(String[] args) {
		System.out.println(new OpSqliteDB().getCon());
	}
}
