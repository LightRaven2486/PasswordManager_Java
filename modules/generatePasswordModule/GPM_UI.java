package generatePasswordModule;

import core.UI;
import core.UIProvider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JSlider;
import javax.swing.JTextField;


public class GPM_UI 
{
	//
	private final UIProvider uiProvider;
	
	//Кнопка открытия диалогового окна генерации пароля
	private static JButton generateMasterPasswordButton = createHoverButton("★");
	
	//Диалоговое окно генерации пароля
	private static JDialog generatePasswordDialog;
	private static JLabel generatePasswordSettingsLabel;
	private static JButton lettersInPasswordButton = createHoverButton("Aa");
	private static JButton numbersInPasswordButton = createHoverButton("123");
	private static JButton symbolsInPasswordButton = createHoverButton("!@#");
	private static JLabel lettersInPasswordLabel;
	private static JLabel numbersInPasswordLabel;
	private static JLabel symbolsInPasswordLabel;
	private static JLabel passwordLengthLabel;
	private static JSlider passwordLengthSlider;
	private static JTextField passwordLengthField;
	private static JButton generatePasswordButton = createHoverButton("Сгенерировать пароль");
	private static JLabel generatedPasswordLabel;
	private static JTextField generatedPasswordField;
	private static JButton applyGeneratedPasswordButton = createHoverButton("Создать");
	private static JButton cancelGeneratePasswordButton = createHoverButton("Отмена");
	
	
	public GPM_UI(UIProvider uiProvider)
	{
		this.uiProvider = uiProvider;
		initializeGPM_UI();
	}
	
	private void initializeGPM_UI()
	{
		UpdateCreateDataBaseDialog();
		CreateGeneratePasswordDialog();
	}
	
	private void UpdateCreateDataBaseDialog()
	{
		JDialog createDataBaseDialog = uiProvider.getCreateDataBaseDialog();
        // Кнопка для генерации пароля
        generateMasterPasswordButton.setBackground(Color.decode("#353945"));
        generateMasterPasswordButton.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        generateMasterPasswordButton.setForeground(Color.WHITE);
        generateMasterPasswordButton.setBounds(310, 190, 55, 25);
        createDataBaseDialog.add(generateMasterPasswordButton);
	}
	
	//Метод создания диалога генератора пароля
	private static void CreateGeneratePasswordDialog()
	{
		//Создание диалогового окна
		generatePasswordDialog = new JDialog((Frame) null, "Генератор паролей", true);
		generatePasswordDialog.setSize(400, 400);
		generatePasswordDialog.getContentPane().setBackground(Color.decode("#202634"));
		generatePasswordDialog.setLayout(null);
		generatePasswordDialog.setResizable(false);
		
		//Метка "Настройка генератора паролей"
		generatePasswordSettingsLabel = new JLabel("Настройка генератора паролей");
		generatePasswordSettingsLabel.setForeground(Color.WHITE);
		generatePasswordSettingsLabel.setBounds(20, 20, 200, 25);
		generatePasswordDialog.add(generatePasswordSettingsLabel);
		
		//Кнопка выбора символов в пароле
		lettersInPasswordButton.setBounds(20, 55, 40, 25);
        generatePasswordDialog.add(lettersInPasswordButton);
        
        //Метка "Включить буквы в пароль"
        lettersInPasswordLabel = new JLabel("Буквы в пароле");
        lettersInPasswordLabel.setForeground(Color.WHITE);
        lettersInPasswordLabel.setBounds(70, 55, 200, 25);
      	generatePasswordDialog.add(lettersInPasswordLabel);
      	
      	//Кнопка выбора символов в пароле
      	numbersInPasswordButton.setBounds(20, 90, 40, 25);
        generatePasswordDialog.add(numbersInPasswordButton);
              
        //Метка "Включить цифры в пароль"
        numbersInPasswordLabel = new JLabel("Цифры в пароле");
        numbersInPasswordLabel.setForeground(Color.WHITE);
        numbersInPasswordLabel.setBounds(70, 90, 200, 25);
        generatePasswordDialog.add(numbersInPasswordLabel);
        
        //Кнопка выбора символов в пароле
        symbolsInPasswordButton.setBounds(20, 125, 40, 25);
        generatePasswordDialog.add(symbolsInPasswordButton);
              
        //Метка "Включить символы в пароль"
        symbolsInPasswordLabel = new JLabel("Символы в пароле");
        symbolsInPasswordLabel.setForeground(Color.WHITE);
        symbolsInPasswordLabel.setBounds(70, 125, 200, 25);
        generatePasswordDialog.add(symbolsInPasswordLabel);
        
        //Метка "Настройка генератора паролей"
        passwordLengthLabel = new JLabel("Размер пароля");
        passwordLengthLabel.setForeground(Color.WHITE);
        passwordLengthLabel.setBounds(20, 160, 200, 25);
      	generatePasswordDialog.add(passwordLengthLabel);
      	
      	//Слайдер размера пароля
      	passwordLengthSlider = new JSlider(8, 64, 24);
      	passwordLengthSlider.setForeground(Color.WHITE);
      	passwordLengthSlider.setBounds(20, 195, 300, 25);
      	passwordLengthSlider.setBackground(null);
      	generatePasswordDialog.add(passwordLengthSlider);
      	
      	// Поле для отображения размера пароля
      	passwordLengthField = new JTextField();
      	passwordLengthField.setEditable(false);
      	passwordLengthField.setBackground(Color.decode("#353945"));
      	passwordLengthField.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        passwordLengthField.setForeground(Color.WHITE);
        passwordLengthField.setBounds(330, 195, 40, 25);
        generatePasswordDialog.add(passwordLengthField);
        
        // Кнопка "Сгенерировать пароль"
        generatePasswordButton.setForeground(Color.GREEN);
        generatePasswordButton.setBounds(20, 235, 350, 25);
        generatePasswordDialog.add(generatePasswordButton);
        
        //Метка "Сгенерированный пароль"
        generatedPasswordLabel = new JLabel("Сгенерированный пароль");
        generatedPasswordLabel.setForeground(Color.WHITE);
        generatedPasswordLabel.setBounds(20, 260, 200, 25);
      	generatePasswordDialog.add(generatedPasswordLabel);
      	
      	// Поле для отображения сгенерированного пароля
      	generatedPasswordField = new JTextField();
      	generatedPasswordField.setBackground(Color.decode("#353945"));
      	generatedPasswordField.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
      	generatedPasswordField.setForeground(Color.WHITE);
      	generatedPasswordField.setBounds(20, 285, 350, 25);
        generatePasswordDialog.add(generatedPasswordField);
        
        // Кнопка "Создать"
        applyGeneratedPasswordButton.setForeground(Color.GREEN);
        applyGeneratedPasswordButton.setBounds(205, 325, 165, 25);
        generatePasswordDialog.add(applyGeneratedPasswordButton);
        
        // Кнопка "Отмена"
        cancelGeneratePasswordButton.setForeground(Color.decode("#F25056"));
        cancelGeneratePasswordButton.setBounds(20, 325, 165, 25);
        generatePasswordDialog.add(cancelGeneratePasswordButton);
	}
	
	//Создание поведения, когда на кнопку навелись
	private static JButton createHoverButton(String text) {
	    JButton button = new JButton(text);
	    button.setPreferredSize(new Dimension(66, 39));
	    button.setFocusable(false); 
	    button.setContentAreaFilled(false);
	    button.setOpaque(true);
	    button.setForeground(Color.WHITE);
	    button.setBackground(Color.decode("#2F343F"));
        button.setBorder(BorderFactory.createLineBorder(Color.decode("#7288B8")));
	    button.setBorderPainted(false);
	    button.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseEntered(MouseEvent e) {
	            button.setBackground(Color.decode("#45526e"));
	        }
	
	        @Override
	        public void mouseExited(MouseEvent e) {
	            button.setBackground(Color.decode("#2F343F"));
	        }
	    });
	    
	    
	    // Создание контекстного меню для кнопки "Файл"
	    if (button.getText() == "Файл") {
	        JPopupMenu popupMenu = new JPopupMenu();
	
	        JMenuItem createNew = new JMenuItem("Создать новую базу паролей");
	        JMenuItem openExisting = new JMenuItem("Открыть существующую");
	        JMenuItem quickSave = new JMenuItem("Быстрое сохранение (CTRL + S)");
	        JMenuItem saveAs = new JMenuItem("Сохранить как...");
	
	        // Добавляем обработчики событий для пунктов меню
	        createNew.addActionListener(e -> JOptionPane.showMessageDialog(null, "Создать новую базу паролей"));
	        openExisting.addActionListener(e -> JOptionPane.showMessageDialog(null, "Открыть существующую"));
	        quickSave.addActionListener(e -> JOptionPane.showMessageDialog(null, "Быстрое сохранение"));
	        saveAs.addActionListener(e -> JOptionPane.showMessageDialog(null, "Сохранить как..."));
	
	        popupMenu.add(createNew);
	        popupMenu.add(openExisting);
	        popupMenu.add(quickSave);
	        popupMenu.add(saveAs);
	
	        // Добавляем обработчик для показа меню
	        button.addActionListener(e -> popupMenu.show(button, 0, button.getHeight()));
	    }
	
	    return button;
	}
	
	public void changeSelectedButtonColor(JButton button)
	{
		if (button.isBorderPainted())
		{
			button.setBorderPainted(false);
		}
		else
		{
			button.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			button.setBorderPainted(true);
		}
	}
	
	public JButton getGenerateMasterPasswordButton()
	{
		return generateMasterPasswordButton;
	}
	
	public JDialog getGeneratePasswordDialog() {
		return generatePasswordDialog;
	}
	
	public JButton getLettersInPasswordButton()
	{
		return lettersInPasswordButton;
	}
	
	public JSlider getPasswordLengthSlider()
	{
		return passwordLengthSlider;
	}
	
	public JButton getNumbersInPasswordButton()
	{
		return numbersInPasswordButton;
	}
	
	public JButton getSymbolsInPasswordButton()
	{
		return symbolsInPasswordButton;
	}
	
	public JTextField getPasswordLengthField()
	{
		return passwordLengthField;
	}
	
	public JButton getGeneratePasswordButton()
	{
		return generatePasswordButton;
	}
	
	public JTextField getGeneratedPasswordField()
	{
		return generatedPasswordField;
	}
	
	public JButton getApplyGeneratedPasswordButton()
	{
		return applyGeneratedPasswordButton;
	}
	
	public JButton getCancelGeneratePasswordButton()
	{
		return cancelGeneratePasswordButton;
	}
}
