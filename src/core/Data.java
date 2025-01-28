package core;

public class Data {

	//Данные
	private static String[] columnData = {};
    private static Object[][] cellData = {};
	
	public Data()
	{
		//
	}
	
	private static void loadData()
	{
		
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
