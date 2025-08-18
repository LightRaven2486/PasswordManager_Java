package core;

import java.sql.*;

public class Data {

	//Данные
	private static String[] columnData = {};
    private static Object[][] cellData = {};
	
	public Data()
	{
		//
	}
	
	public Connection CreateNewDatabase(String dbPath) throws SQLException 
	{
		String url = "jdbc:sqlite:" + dbPath;
		return DriverManager.getConnection(url);
	}
	
	public void updateСolumnNames(String[] columns, Object[][] cells)
	{
		columnData = columns;
		cellData = cells;
	}
	
	public String[] getColumnData()
	{
		return columnData;
	}
	
	public Object[][] getCellData()
	{
		return cellData;
	}
	
}
