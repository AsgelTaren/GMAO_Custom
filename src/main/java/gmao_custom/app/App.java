package gmao_custom.app;

import java.io.File;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import gmao_custom.data.DataBase;
import gmao_custom.gfx.IconAtlas;

public class App {

	// UI
	private JFrame frame;
	private JTabbedPane tabs;

	// DataBase
	private File file;
	private DataBase db;

	public App(File file) {
		this.file = file;
	}

	public void create() throws SQLException {
		// Loading icons
		IconAtlas.registerAllIcons();

		// DataBase connection
		db = new DataBase(file);

		// GUI setup
		frame = new JFrame("GMAO_Custom");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tabs = new JTabbedPane();
		tabs.addTab("Articles", new ArticlePanel(this));
		tabs.addTab("Marques", new BrandPanel(this));

		frame.add(tabs);

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public DataBase getDataBase() {
		return db;
	}

	public JFrame getJFrame() {
		return frame;
	}

}
