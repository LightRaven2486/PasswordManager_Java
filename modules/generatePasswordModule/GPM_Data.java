package generatePasswordModule;

public class GPM_Data {

	final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    final String numbers = "0123456789";
    final String symbols = "!@#$%^&*()-_+=";
    
    private static Boolean[] passwordGroups = new Boolean[] {true, false, false};
    
	public String getLetters()
	{
		return letters;
	}
	
	public String getNumbers()
	{
		return numbers;
	}
	
	public String getSymbols()
	{
		return symbols;
	}
	
	public Boolean[] getPasswordGroups()
	{
		return passwordGroups;
	}
}
