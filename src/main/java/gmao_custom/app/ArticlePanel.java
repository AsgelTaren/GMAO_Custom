package gmao_custom.app;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import gmao_custom.data.Article;
import gmao_custom.gfx.IconAtlas;

@SuppressWarnings("serial")
public class ArticlePanel extends JPanel {

	private App app;
	private JTable table;
	private ArticleTableModel model;
	private JToolBar toolbar;

	public ArticlePanel(App app) {
		super();
		this.app = app;
		this.model = new ArticleTableModel(app.getDataBase());
		model.query();

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = 1;

		table = new JTable(model);
		table.setDefaultRenderer(String.class, new ArticleCellRenderer());
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent event) {
				if (SwingUtilities.isRightMouseButton(event)) {
					int choice = table.getSelectedRow();
					if (choice > -1) {
						Article target = model.getArticles().get(choice);
						menuForArticleTable(target).show(event.getComponent(), event.getX(), event.getY());
					}
				}
			}
		});
		table.getSelectionModel().addListSelectionListener(e -> {
			toolbar.getComponent(1).setEnabled(table.getSelectedRow() != -1);
		});

		add(new JScrollPane(table), gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weighty = 0;
		gbc.gridy = 0;
		add(toolbar = createToolBar(), gbc);
	}

	public JPopupMenu menuForArticleTable(Article article) {
		JPopupMenu res = new JPopupMenu();

		JMenuItem edit = new JMenuItem("Modifier");
		edit.addActionListener(e -> {
			ArticleDialog dialog = new ArticleDialog(app, this, model.getBrandsMap(), article, false);
			dialog.setVisible(true);
		});
		edit.setIcon(IconAtlas.getImageIcon("edit", 16));
		res.add(edit);
		return res;
	}

	public JToolBar createToolBar() {
		JToolBar result = new JToolBar();

		JButton add = new JButton("Ajouter");
		add.setToolTipText("Ajouter un article");
		add.setIcon(IconAtlas.getImageIcon("add", 32));
		add.addActionListener(action -> {
			ArticleDialog dialog = new ArticleDialog(app, this, model.getBrandsMap(),
					Article.createArticle(app.getDataBase()), true);
			dialog.setVisible(true);
		});
		result.add(add);

		JButton edit = new JButton("Modifier");
		edit.setToolTipText("Modifier cet article");
		edit.setIcon(IconAtlas.getImageIcon("edit", 32));
		edit.addActionListener(e -> {
			ArticleDialog dialog = new ArticleDialog(app, this, model.getBrandsMap(),
					model.getArticles().get(table.getSelectedRow()), false);
			dialog.setVisible(true);
		});
		edit.setEnabled(false);
		result.add(edit);

		return result;
	}

	public void refreshData() {
		model.query();
		table.revalidate();
		table.repaint();
	}

	public App getApp() {
		return app;
	}

}
