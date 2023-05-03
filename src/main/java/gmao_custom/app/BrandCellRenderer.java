package gmao_custom.app;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class BrandCellRenderer extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel res = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		res.setForeground(UIManager.getColor("Table.foreground"));
		if (value instanceof Color color) {
			res.setText("#" + Integer.toHexString(color.getRGB()));
			res.setForeground(color);
		}
		return res;
	}

}
