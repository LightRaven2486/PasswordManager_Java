//Импорт библиотек java и javax.swing
package core;

import javax.swing.*; 
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UI implements UIProvider {
	//
	private static String basePath = System.getProperty("user.dir");
	//
	private static Path createDBImagePath = Paths.get(basePath+"\\src", "images", "createDB.png");
	//
	private static Path openExistDBImagePath = Paths.get(basePath+"\\src", "images", "openDB.png");
	//
	private static Path saveCurrentDBImagePath = Paths.get(basePath+"\\src", "images", "saveDB.png");
	
	//Инстанс UI
	private static UI instance;
	
	//Фрейм
	private static JFrame mainFrame; //Основной фрейм (Окно)
	
	//Шапка
	private static JPanel headerPanel; //Шапка окна
	private static JButton file = createHoverButton("Файл"); // Кнопка "Файл"
	private static JButton edit = createHoverButton("Правка"); // Кнопка "Правка"
	private static JButton view = createHoverButton("Вид"); // Кнопка "Вид"
	private static JButton help = createHoverButton("Помощь"); // Кнопка "Помощь"
	
	private static JPanel topPanel; // Родитель шапки окна и тулбара
	
	private static JToolBar toolBar; // Тулбар (Идёт под шапкой)
	//Кнопка тулбара "Создание новой базы паролей"
	private static JButton createNewDatabase = createToolbarButton(createDBImagePath.toString(), "Создать новую базу паролей");
	//Кнопка тулбара "Открытие базы паролей"
	private static JButton openExistDatabase = createToolbarButton(openExistDBImagePath.toString(), "Открыть базу паролей");
	//Кнопка тулбара "Сохранение базы паролей"
	private static JButton saveCurrentDatabase = createToolbarButton(saveCurrentDBImagePath.toString(), "Сохранить базу паролей");
	
	//Левая панель
	private static JPanel leftPanel; // Левая панель (Древо каталогов)
	private static JScrollPane treeScrollPane; //Прокрутка древа каталогов 
	private static DefaultMutableTreeNode root;
	private static JTree tree;
	
	//Центральный список
	private static JTable centerTable; //Центральный список
	private static JScrollPane centerScrollPane; //Прокрутка центрального списка
	
	//Всплываюшее окно ячейки
	private static JPopupMenu cellPopupMenu; //Всплывающее окно ячейки
	private static JMenuItem editItem = createStyledMenuItem("Изменить"); //Кнопка всплывающего окна "Изменить"
	private static JMenuItem deleteItem = createStyledMenuItem("Удалить"); //Кнопка всплывающего окна "Удалить"
	
	//Подвал
	private static JPanel bottomPanel; //Подвал (Нижняя панель)
	
	//Диалоговое окно создания базы данных
	private static JDialog createDataBaseDialog;
	private static JLabel dataBaseNameLabel;
	private static JTextField dataBaseNameField;
	private static JLabel dataBasePathLabel; 
	private static JTextField dataBasePathField;
	private static JButton browseDataBasePathButton = createHoverButton("...");
	private static JLabel masterPasswordDataBaseLabel;
	private static JPasswordField masterPasswordDataBaseField;
	private static JButton createDataBaseButton = createHoverButton("Создать");
	private static JButton cancelCreateDataBaseButton = createHoverButton("Отмена");

	//Запуск инициализатора
	public UI()
	{
		initializeUI();
	}
	
	public static synchronized UI getInstance() {
        if (instance == null) {
            instance = new UI();
        }
        return instance;
    }
	
	//Инициализирует все визуальные компоненты 
	private static void initializeUI()
	{
		System.out.println(createDBImagePath);
		createHeaderPanel();
		createToolBar();
		createTopPanel();
		createLeftPanel();
		createCenterPanel();
		createBottomPanel();
		createCellPopupMenu();
		createMainFrame();
		createCreateDataBaseDialog();
	}
	
	//Метод создания главного окна
	private static void createMainFrame()
	{
		// Создаем главное окно
        mainFrame = new JFrame("Password Manager");
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.setSize(800, 600);

        // Устанавливаем менеджер компоновки
        mainFrame.setLayout(new BorderLayout());

        // Устанавливаем цвет фона окна
        mainFrame.getContentPane().setBackground(Color.decode("#2F343F"));
  
        
        // Добавляем общую панель в верхнюю часть окна
        mainFrame.add(topPanel, BorderLayout.NORTH);
        //Добавляем левую панель в левую часть окна
        mainFrame.add(leftPanel, BorderLayout.WEST);
        //Добавляем центральную таблицу в центре окна 
        mainFrame.add(centerScrollPane, BorderLayout.CENTER);
        // Добавляем нижнюю панель в нижнюю часть окна
        mainFrame.add(bottomPanel, BorderLayout.PAGE_END);
        // Делаем окно видимым
        mainFrame.setVisible(true);
	}
	
	//Метод создания шапки
	private static void createHeaderPanel()
	{
		// Создаем панель для шапки с кнопками
        headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.decode("#2F343F"));
        headerPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        headerPanel.add(file);
        headerPanel.add(edit);
        headerPanel.add(view);
        headerPanel.add(help);
	}
	
	//Метод создания родителя шапки и тулбара
	private static void createTopPanel()
	{
		// Создаем панель для шапки и тулбара
		topPanel = new JPanel(new BorderLayout());
		// Добавляем шапку и тулбар в общую панель
	    topPanel.add(headerPanel, BorderLayout.NORTH);
	    topPanel.add(toolBar, BorderLayout.CENTER);
	}
	
	//Метод создания тулбара
	private static void createToolBar()
	{
		// Создаем тулбар
	    toolBar = new JToolBar();
	    toolBar.setFloatable(false); // Запрещаем перемещение тулбара
	    toolBar.setBackground(Color.decode("#2F343F"));
	    toolBar.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));

	    // Добавляем кнопки с иконками
	    toolBar.add(createNewDatabase);
	    toolBar.add(openExistDatabase);
	    toolBar.add(saveCurrentDatabase);
	}
	
	//Метод создания левой панели (Древо каталогов)
	private static void createLeftPanel()
	{
		 // Создаем левую панель с деревом
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.decode("#2F343F"));
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
	}
	
	public void UpdateRootTreeNode(String rootName)
	{
		if (leftPanel.getComponentCount() >= 1)
		{
	        leftPanel.remove(treeScrollPane);
		}
		root = new DefaultMutableTreeNode(rootName);
        //DefaultMutableTreeNode windowsNode = new DefaultMutableTreeNode("Виндоус");
        //root.add(windowsNode);
        tree = new JTree(root);
        tree.setBackground(Color.decode("#2F343F"));
        tree.setForeground(Color.WHITE);
        tree.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        tree.setCellRenderer(new CustomTreeCellRenderer());
        
        treeScrollPane = new JScrollPane(tree);
        treeScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        treeScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        treeScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        leftPanel.add(treeScrollPane, BorderLayout.CENTER);
	}

	//Метод создания центрального списка
	private static void createCenterPanel()
	{   
		centerTable = new JTable(new DefaultTableModel());
		
        centerTable.setBackground(Color.decode("#2F343F"));
        centerTable.setForeground(Color.WHITE);
        centerTable.setRowHeight(30);
        centerTable.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        centerTable.setShowGrid(false); // Отключение отображения сетки
        
        centerTable.getTableHeader().setBackground(Color.decode("#2F343F"));
        centerTable.getTableHeader().setForeground(Color.WHITE);
        centerTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        
        centerScrollPane = new JScrollPane(centerTable);
        centerScrollPane.getViewport().setBackground(Color.decode("#2F343F"));
        centerScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        centerScrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        centerScrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        
	}
	
	//Метод создания всплывающего окна
	private static void createCellPopupMenu()
	{
		// Создаем контекстное меню
		cellPopupMenu = new JPopupMenu();
		cellPopupMenu.setBackground(Color.decode("#2F343F")); // Устанавливаем цвет фона
		cellPopupMenu.setBorder(BorderFactory.createLineBorder(Color.decode("#202634"))); // Устанавливаем границу

        // Добавляем элементы в контекстное меню
        cellPopupMenu.add(editItem);
        cellPopupMenu.add(deleteItem);
	}
	
	//Метод создания подвала (Нижней панели)
	private static void createBottomPanel()
	{
		bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBackground(Color.decode("#202634"));
        bottomPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));

        // Левый текст "Создайте или откройте базу данных."
        JLabel statusLabel = new JLabel("Создайте или откройте базу данных.");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bottomPanel.add(statusLabel, BorderLayout.WEST);
	}
	
	//Метод создания диалога создания базы данных
	private static void createCreateDataBaseDialog()
	{
		// Создание диалогового окна
        createDataBaseDialog = new JDialog((Frame) null, "Создание новой базы паролей", true);
        createDataBaseDialog.setSize(400, 310);
        createDataBaseDialog.getContentPane().setBackground(Color.decode("#202634"));
        createDataBaseDialog.setLayout(null);
        createDataBaseDialog.setResizable(false);
        
        // Метка "Название базы паролей"
        dataBaseNameLabel = new JLabel("Название базы паролей");
        dataBaseNameLabel.setForeground(Color.WHITE);
        dataBaseNameLabel.setBounds(20, 20, 200, 25);
        createDataBaseDialog.add(dataBaseNameLabel);

        // Поле для ввода названия базы паролей
        dataBaseNameField = new JTextField();
        dataBaseNameField.setBackground(Color.decode("#353945"));
        dataBaseNameField.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        dataBaseNameField.setForeground(Color.WHITE);
        dataBaseNameField.setBounds(20, 50, 350, 25);
        createDataBaseDialog.add(dataBaseNameField);

        // Метка "Путь базы паролей"
        dataBasePathLabel = new JLabel("Путь базы паролей");
        dataBasePathLabel.setForeground(Color.WHITE);
        dataBasePathLabel.setBounds(20, 90, 200, 25);
        createDataBaseDialog.add(dataBasePathLabel);

        // Поле для отображения пути
        dataBasePathField = new JTextField();
        dataBasePathField.setBackground(Color.decode("#353945"));
        dataBasePathField.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        dataBasePathField.setForeground(Color.WHITE);
        dataBasePathField.setBounds(20, 120, 280, 25);
        createDataBaseDialog.add(dataBasePathField);
        
        // Кнопка просмотра каталогов
        browseDataBasePathButton.setBackground(Color.decode("#353945"));
        browseDataBasePathButton.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        browseDataBasePathButton.setForeground(Color.WHITE);
        browseDataBasePathButton.setBounds(310, 120, 55, 25);
        createDataBaseDialog.add(browseDataBasePathButton);
        
        // Метка "Придумайте пароль"
        masterPasswordDataBaseLabel = new JLabel("Придумайте пароль");
        masterPasswordDataBaseLabel.setForeground(Color.WHITE);
        masterPasswordDataBaseLabel.setBounds(20, 160, 200, 25);
        createDataBaseDialog.add(masterPasswordDataBaseLabel);
        
        // Поле для ввода пароля
        masterPasswordDataBaseField = new JPasswordField();
        masterPasswordDataBaseField.setBackground(Color.decode("#353945"));
        masterPasswordDataBaseField.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        masterPasswordDataBaseField.setForeground(Color.WHITE);
        masterPasswordDataBaseField.setBounds(20, 190, 280, 25);
        createDataBaseDialog.add(masterPasswordDataBaseField);
        
        // Кнопка "Создать"
        createDataBaseButton.setBackground(Color.decode("#353945"));
        createDataBaseButton.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        createDataBaseButton.setForeground(Color.GREEN);
        createDataBaseButton.setBounds(200, 230, 165, 25);
        createDataBaseDialog.add(createDataBaseButton);
        
        // Кнопка "Отмена"
        cancelCreateDataBaseButton.setBackground(Color.decode("#353945"));
        cancelCreateDataBaseButton.setBorder(BorderFactory.createLineBorder(Color.decode("#202634")));
        cancelCreateDataBaseButton.setForeground(Color.decode("#F25056"));
        cancelCreateDataBaseButton.setBounds(20, 230, 165, 25);
        createDataBaseDialog.add(cancelCreateDataBaseButton);
        
	}
	
	//Создание кастомных пунктов меню
    private static JMenuItem createStyledMenuItem(String text) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setBackground(Color.decode("#2F343F")); // Фон
        menuItem.setForeground(Color.WHITE); // Цвет текста
        menuItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Отступы текста
        menuItem.setOpaque(true); // Прозрачность
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setBackground(Color.LIGHT_GRAY); // Цвет при наведении
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(Color.decode("#2F343F")); // Возврат цвета
            }
        });
        return menuItem;
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
	
	//Создание кастомных кнопок для тулбара
	private static JButton createToolbarButton(String iconPath, String tooltip) {
    ImageIcon icon = new ImageIcon(iconPath);
    JButton button = new JButton(icon);
    button.setToolTipText(tooltip); // Всплывающая подсказка
    button.setFocusable(false); // Отключаем фокусировку
    button.setContentAreaFilled(false);
    button.setOpaque(true);
    button.setBackground(Color.decode("#2F343F"));
    button.setBorder(BorderFactory.createLineBorder(Color.decode("#7288B8")));
    button.setBorderPainted(false); // Убираем рамку

    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(Color.decode("#45526e"));
            button.setBorderPainted(true); // Делаем рамку видимой
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(Color.decode("#2F343F"));
            button.setBorderPainted(false); // Убираем рамку
        }
    });

    return button;
	}
	
	public JPanel getHeaderPanel()
	{
		return headerPanel;
	}
	
	public JTable getCenterTable()
	{
		return centerTable;
	}
	
	public JFrame getMainFrame()
	{
		return mainFrame;
	}
	
	public JMenuItem getEditItem()
	{
		return editItem;
	}
	
	public JMenuItem getDeleteItem()
	{
		return deleteItem;
	}
	
	public JPopupMenu getCellPopupMenu()
	{
		return cellPopupMenu;
	}
	
	public JButton getCreateDataBaseButtonIcon()
	{
		return createNewDatabase;
	}
	
	@Override
	public JDialog getCreateDataBaseDialog()
	{
		return createDataBaseDialog;
	}
	
	public JButton getBrowseDataBasePathButton()
	{
		return browseDataBasePathButton;
	}
	
	public JTextField getDataBasePathField()
	{
		return dataBasePathField;
	}
	
	public JTextField getDataBaseNameField()
	{
		return dataBaseNameField;
	}
	
	public JPasswordField getMasterPasswordDataBaseField()
	{
		return masterPasswordDataBaseField;
	}
	
	public JButton getCreateDataBaseButton()
	{
		return createDataBaseButton;
	}
	
	public JButton getCancelCreateDataBaseButton() {
		return cancelCreateDataBaseButton;
	}
	
}