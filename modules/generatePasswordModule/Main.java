package generatePasswordModule;

import core.UI;
import core.UIProvider;

public class Main {
	public void Execute() 
	{
		UI ui = UI.getInstance();
		GPM_UI gpm_ui = new GPM_UI(ui);
		GPM_Data gpm_data = new GPM_Data();
		new GPM_Logic(gpm_ui, gpm_data, ui);
		
        System.out.println("Выполнение метода execute() в классе Logic");
    }
}
