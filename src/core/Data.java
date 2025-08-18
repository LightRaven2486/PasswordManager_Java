package core;

import java.sql.*;

public class Data {

	//Данные
	public final String defaultTable = 
	"CREATE TABLE IF NOT EXISTS passwords "
	+ "(id INTEGER PRIMARY KEY NOT NULL, "
	+ "Название VARCHAR(55),"
	+ "Пользователь VARCHAR(55),"
	+ "Пароль VARCHAR(255),"
	+ "Сайт VARCHAR(2083))";
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
