package core;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.File;
import java.security.SecureRandom;
import java.util.Random;


public class Logic {
	private UI ui;
	private Data data;
	
	public Logic(UI ui, Data data)
	{
		this.ui = ui;
		this.data = data;
		setupEventListeners();
	}
	
	private void setupEventListeners()
	{	
		
		JTable centerTable = ui.getCenterTable(); //Получаем центральный список
		JFrame mainFrame = ui.getMainFrame(); //Получаем главное окно
		JMenuItem editItem = ui.getEditItem(); //Получаем кнопку контесткного меню ячейки "Изменить"
		JMenuItem deleteItem = ui.getDeleteItem(); //Получаем кнопку контесткного меню ячейки "Удалить"
		JPopupMenu cellPopupMenu = ui.getCellPopupMenu(); //Получаем контесткное меню ячейки
		JButton createNewDatabase = ui.getCreateDataBaseButtonIcon(); //Получаем кнопку с иконкой создания базы данных
		JDialog createDataBaseDialog = ui.getCreateDataBaseDialog();
		JButton browseDataBasePathButton = ui.getBrowseDataBasePathButton();
		JTextField dataBasePathField = ui.getDataBasePathField();
		JTextField dataBaseNameField = ui.getDataBaseNameField();
		JPasswordField masterPasswordDataBaseField = ui.getMasterPasswordDataBaseField();
		//JButton generateMasterPasswordButton = ui.getGenerateMasterPasswordButton();
		JButton createDataBaseButton = ui.getCreateDataBaseButton();
		JButton cancelCreateDataBaseButton = ui.getCancelCreateDataBaseButton();
		
		String[] columnData = data.getColumnData(); //Получаем список названий колонок
		Object[][] cellData = data.getCellData(); //Получаем данные в центральном списке

		
		// Добавляем слушатель окна
		mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Показываем диалог подтверждения
                int result = JOptionPane.showConfirmDialog(
                		mainFrame,
                        "Вы уверены, что хотите закрыть окно?",
                        "Подтверждение",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                // Если пользователь выбрал "Да", закрываем окно
                if (result == JOptionPane.YES_OPTION) {
                	mainFrame.dispose();
                }
                // Если "Нет", ничего не делаем
            }
        });
		
		// Делаем все ячейки нередактируемыми
        DefaultTableModel model = new DefaultTableModel(cellData, columnData) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Отключаем редактирование всех ячеек
            }
        };
        centerTable.setModel(model); //
		
        //Добавляем обработчик для двойного клика
		centerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Проверяем двойной клик
                    int row = centerTable.getSelectedRow();
                    int column = centerTable.getSelectedColumn();
                    if (row != -1 && column != -1) {
                        Object cellValue = centerTable.getValueAt(row, column);
                        if (cellValue != null) {
                            String value = cellValue.toString();
                            Toolkit.getDefaultToolkit()
                            .getSystemClipboard()
                            .setContents(new StringSelection(value), null); // Копируем в буфер обмена
                            JOptionPane.showMessageDialog(mainFrame, "Данные скопированы: " + value, "Копирование", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
		
		
		// Добавляем обработчики событий для элементов
        editItem.addActionListener(e -> {
            int row = centerTable.getSelectedRow();
            int column = centerTable.getSelectedColumn();
            if (row != -1 && column != -1) {
                Object value = centerTable.getValueAt(row, column);
                String newValue = JOptionPane.showInputDialog(
                		centerTable,
                    "Изменить значение:",
                    value != null ? value.toString() : ""
                );
                if (newValue != null) {
                	centerTable.setValueAt(newValue, row, column); // Изменяем значение
                }
            }
        });

        deleteItem.addActionListener(e -> {
            int row = centerTable.getSelectedRow();
            if (row != -1) {
                ((DefaultTableModel) centerTable.getModel()).removeRow(row); // Удаляем строку
            }
        });
		
		// Добавляем обработчик для отображения контекстного меню
		centerTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) { // Проверяем, вызвано ли правой кнопкой мыши
                    int row = centerTable.rowAtPoint(e.getPoint());
                    int column = centerTable.columnAtPoint(e.getPoint());
                    if (row != -1 && column != -1) {
                    	centerTable.setRowSelectionInterval(row, row); // Выделяем строку
                        centerTable.setColumnSelectionInterval(column, column); // Выделяем ячейку
                        cellPopupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });
		
		//Добавляем обработчик для вызова метода создания локальной базы данных
		createNewDatabase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        createDataBaseDialog.setLocationRelativeTo(null);
		        createDataBaseDialog.setVisible(true);
            }
		});
		
        // Кнопка для выбора пути
		browseDataBasePathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = fileChooser.showOpenDialog(createDataBaseDialog);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    dataBasePathField.setText(selectedDirectory.getAbsolutePath());
                }
            }
        });
		
		createDataBaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = dataBaseNameField.getText();
                String path = dataBasePathField.getText();
                String password = new String(masterPasswordDataBaseField.getPassword());
         
                File directory = new File(path);             

                if (name.isEmpty()) 
                {
                    JOptionPane.showMessageDialog(createDataBaseDialog, "Поле 'Название базы паролей' не должно быть пустым!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } 
                else if (path.isEmpty())
                {
                	JOptionPane.showMessageDialog(createDataBaseDialog, "Путь для базы паролей не должен быть пустым!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                else if (!directory.exists() && !directory.isDirectory())
                {
                	JOptionPane.showMessageDialog(createDataBaseDialog, "Данный путь не существует!", "Ошибка", JOptionPane.ERROR_MESSAGE);	
                }
                else if (password.isEmpty())
                {
                	JOptionPane.showMessageDialog(createDataBaseDialog, "Мастер пароль не должен быть пустым!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
                else 
                {
                	ui.UpdateRootTreeNode(name);
                    createDataBaseDialog.dispose();
                }
            }
        });
		
		cancelCreateDataBaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	createDataBaseDialog.dispose();
            }
        });
	}
}
