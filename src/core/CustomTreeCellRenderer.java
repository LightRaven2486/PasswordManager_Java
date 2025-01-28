package core;
import java.awt.Color;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

	private final Icon folderIcon = new ImageIcon("C:\\Users\\kolan\\eclipse-workspace\\PasswordManager\\src\\images\\treeCatalog.png"); // Иконка папки
    private final Icon fileIcon = new ImageIcon("C:\\Users\\kolan\\eclipse-workspace\\PasswordManager\\src\\images\\windowsIcon.png"); // Иконка файла

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
