package generatePasswordModule;

//Импорты библиотек
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import core.UI;

public class GPM_Logic {
	private UI ui;
	private GPM_UI gpm_ui;
	private GPM_Data gpm_data;
	
	//
	public GPM_Logic(GPM_UI gpm_ui, GPM_Data gpm_data, UI ui)
	{
		this.ui = ui;
		this.gpm_ui = gpm_ui;
		this.gpm_data = gpm_data;
		setupGPM_LogicEventListeners();
	}
	
	private static int passwordSliderValue = 24;
	
	//
	private void setupGPM_LogicEventListeners()
	{
		JButton generateMasterPasswordButton = gpm_ui.getGenerateMasterPasswordButton();
		
		JDialog generatePasswordDialog = gpm_ui.getGeneratePasswordDialog();
		JButton lettersInPasswordButton = gpm_ui.getLettersInPasswordButton();
		JButton numbersInPasswordButton = gpm_ui.getNumbersInPasswordButton();
		JButton symbolsInPasswordButton = gpm_ui.getSymbolsInPasswordButton();
		JSlider passwordLengthSlider = gpm_ui.getPasswordLengthSlider();
		JTextField passwordLengthField = gpm_ui.getPasswordLengthField();
		JButton generatePasswordButton = gpm_ui.getGeneratePasswordButton();
		JTextField generatedPasswordField = gpm_ui.getGeneratedPasswordField();
		JButton applyGeneratedPasswordButton = gpm_ui.getApplyGeneratedPasswordButton();
		JButton cancelGeneratePasswordButton = gpm_ui.getCancelGeneratePasswordButton();
		
		Boolean[] passwordGroups = gpm_data.getPasswordGroups();
		
		gpm_ui.changeSelectedButtonColor(lettersInPasswordButton);
		passwordLengthField.setText(String.valueOf(passwordLengthSlider.getValue()));
		
		generateMasterPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	generatePasswordDialog.setLocationRelativeTo(null);
            	generatePasswordDialog.setVisible(true);
            }
        });
		
		lettersInPasswordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (passwordGroups[0].booleanValue() == true)
				{
					passwordGroups[0] = false;
				}
				else
				{
					passwordGroups[0] = true;
				}
				gpm_ui.changeSelectedButtonColor(lettersInPasswordButton);
			}
		});
		
		numbersInPasswordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (passwordGroups[1].booleanValue() == true)
				{
					passwordGroups[1] = false;
				}
				else
				{
					passwordGroups[1] = true;
				}
				gpm_ui.changeSelectedButtonColor(numbersInPasswordButton);
			}
		});
		
		symbolsInPasswordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (passwordGroups[2].booleanValue() == true)
				{
					passwordGroups[2] = false;
				}
				else
				{
					passwordGroups[2] = true;
				}
				gpm_ui.changeSelectedButtonColor(symbolsInPasswordButton);
			}
		});
		
		generatePasswordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				generatedPasswordField.setText(generatePassword());
			}

		    private String generatePassword() {
		    	SecureRandom random = new SecureRandom();
		        // Простая логика генерации пароля
		        StringBuilder password = new StringBuilder();
		        for (int i = 0; i < passwordSliderValue; i++) {
		        	
		        	if (passwordGroups[0] == true)
		        	{
		                int randomIndex = (int) (random.nextDouble() * gpm_data.getLetters().length());
		                password.append(gpm_data.getLetters().charAt(randomIndex));
		        	}
		        	
		        	if (passwordGroups[1] == true)
		        	{
		        		int randomIndex = (int) (random.nextDouble() * gpm_data.getNumbers().length());
		                password.append(gpm_data.getNumbers().charAt(randomIndex));
		        	}
		        	
		        	if (passwordGroups[2] == true)
		        	{
		        		int randomIndex = (int) (random.nextDouble() * gpm_data.getSymbols().length());
		                password.append(gpm_data.getSymbols().charAt(randomIndex));
		        	}

		        }
		        password.delete(passwordSliderValue, password.length());
		        
		     // Преобразуем StringBuilder в массив символов
		        char[] chars = password.toString().toCharArray();

		        // Перемешиваем массив символов
		        for (int i = chars.length - 1; i > 0; i--) {
		            int j = random.nextInt(i + 1); // Генерируем случайный индекс от 0 до i
		            // Меняем местами символы chars[i] и chars[j]
		            char temp = chars[i];
		            chars[i] = chars[j];
		            chars[j] = temp;
		        }

		        // Собираем перемешанный массив обратно в StringBuilder
		        password.setLength(0); // Очищаем StringBuilder
		        password.append(chars);
		        
		        return password.toString();
		    }
			
		});
		
        // Добавляем слушатель изменений значения слайдера
		passwordLengthSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
            	passwordSliderValue = passwordLengthSlider.getValue();
                passwordLengthField.setText(String.valueOf(passwordSliderValue));
            }
        });
		
		//
		applyGeneratedPasswordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				generatePasswordDialog.dispose();
				ui.getMasterPasswordDataBaseField().setText(generatedPasswordField.getText());
			}
		});
		
		//
		cancelGeneratePasswordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				generatePasswordDialog.dispose();
			}
		});
	}
	
	
}
