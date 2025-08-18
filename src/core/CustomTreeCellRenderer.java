package core;
import java.awt.Color;
import java.awt.Component;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

	private static String basePath = System.getProperty("user.dir");
	private static Path folderIconPath = Paths.get(basePath+"\\src", "images", "treeCatalog.png"); // Путь к иконке папки
	private static Path fileIconPath = Paths.get(basePath+"\\src", "images", "windowsIcon.png"); // Путь к иконке файла
    private final Icon folderIcon = new ImageIcon(folderIconPath.toString()); // Иконка файла
    private final Icon fileIcon = new ImageIcon(fileIconPath.toString()); // Иконка файла

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        
        // Настройка цвета текста
        setForeground(Color.WHITE);

        // Убираем задний фон
        setOpaque(false);
        setBackgroundNonSelectionColor(null);
        setBackgroundSelectionColor(null);
        
        if (leaf) {
            setIcon(fileIcon); // Лист (файл)
        } else {
            setIcon(folderIcon); // Родительский узел (папка)
        }
        return this;
    }

}
