package gmao_custom.app;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class BrandPanel extends JPanel {

	// App reference
	private App app;

	// UI
	private JTable table;
	private BrandTableModel model;

	public BrandPanel(App app) {
		super();
		this.app = app;
		this.model = new BrandTableModel(app);
		model.query();

		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gbc.gridy = 0;
		gbc.gridheight = gbc.gridwidth = 1;

		table = new JTable(model);
		table.setDefaultRenderer(String.class, new BrandCellRenderer());
		add(new JScrollPane(table), gbc);

	}

}
