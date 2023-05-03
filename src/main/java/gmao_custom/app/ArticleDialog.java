package gmao_custom.app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.NumberFormatter;

import gmao_custom.data.Article;
import gmao_custom.data.ArticleUnit;
import gmao_custom.data.Brand;

@SuppressWarnings("serial")
public class ArticleDialog extends JDialog {
	private JFormattedTextField field_numero, field_qte_dsm, field_qte_available, field_qte_max, field_qte_min,
			field_qte_to_com, field_quantity, field_unit_price, field_amount;

	private JTextField field_place, field_libelle, field_reference;
	private JComboBox<ArticleUnit> field_unit;
	private JComboBox<Brand> field_brand;
	private JTextArea field_note;

	public ArticleDialog(App app, ArticlePanel articlePanel, HashMap<Integer, Brand> brandsMap, Article article,
			boolean creation) {
		super(app.getJFrame(), "Article", true);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		// Number format
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Données de l'article"));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gbc.gridy = 0;
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.weightx = gbc.weighty = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel label_numero = new JLabel("Code Article");
		label_numero.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(label_numero, gbc);
		field_numero = new JFormattedTextField(formatter);
		field_numero.setPreferredSize(new Dimension(150, 25));
		field_numero.setEditable(false);
		gbc.gridy++;
		panel.add(field_numero, gbc);
		field_numero.setText(article.getNumero() + "");

		JLabel label_place = new JLabel("Emplacement");
		label_place.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridy++;
		panel.add(label_place, gbc);
		field_place = new JTextField();
		field_place.setPreferredSize(new Dimension(150, 25));
		gbc.gridy++;
		panel.add(field_place, gbc);
		field_place.setText(article.getPlace());

		format = DecimalFormat.getInstance();
		format.setRoundingMode(RoundingMode.HALF_UP);
		format.setMinimumFractionDigits(2);
		format.setMaximumFractionDigits(2);
		InternationalFormatter formatter2 = new InternationalFormatter(format);
		formatter2.setMinimum(0.0);
		formatter2.setAllowsInvalid(false);
		formatter2.setCommitsOnValidEdit(true);
		JLabel label_quantity = new JLabel("Quantité");
		label_quantity.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridy++;
		panel.add(label_quantity, gbc);
		field_quantity = new JFormattedTextField(formatter2);
		field_quantity.setPreferredSize(new Dimension(150, 25));
		gbc.gridy++;
		panel.add(field_quantity, gbc);
		field_quantity.setText(article.getQuantity() + "");
		field_quantity.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					updateAmount();
				}
			}
		});

		JLabel label_unit = new JLabel("Unité");
		label_unit.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridy++;
		panel.add(label_unit, gbc);
		field_unit = new JComboBox<>(ArticleUnit.values());
		field_unit.setPreferredSize(new Dimension(150, 25));
		gbc.gridy++;
		panel.add(field_unit, gbc);
		field_unit.setSelectedItem(article.getUnit());

		NumberFormat currFormat = NumberFormat.getCurrencyInstance();
		NumberFormatter currFormatter = new NumberFormatter(currFormat);
		currFormatter.setMinimum(0);
		JLabel label_amount = new JLabel("Montant");
		label_amount.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridy++;
		panel.add(label_amount, gbc);
		field_amount = new JFormattedTextField(currFormatter);
		field_amount.setPreferredSize(new Dimension(150, 25));
		field_amount.setEditable(false);
		gbc.gridy++;
		panel.add(field_amount, gbc);
		field_amount.setText(article.getUnit_price() * article.getQuantity() + "");

		JLabel label_libelle = new JLabel("Libéllé");
		label_libelle.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridy = 0;
		gbc.gridx = 1;
		gbc.gridwidth = 5;
		panel.add(label_libelle, gbc);
		field_libelle = new JTextField();
		field_libelle.setPreferredSize(new Dimension(150, 25));
		gbc.gridy++;
		panel.add(field_libelle, gbc);
		field_libelle.setText(article.getLibelle());

		Collection<Brand> brandsCol = brandsMap.values();
		Brand[] brands = new Brand[brandsCol.size()];
		Iterator<Brand> it = brandsCol.iterator();
		{
			int temp = 0;
			while (it.hasNext()) {
				brands[temp++] = it.next();
			}
		}

		JLabel label_brand = new JLabel("Marque");
		label_brand.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridy++;
		gbc.gridwidth = 2;
		panel.add(label_brand, gbc);
		field_brand = new JComboBox<>(brands);
		field_brand.setPreferredSize(new Dimension(150, 25));
		gbc.gridy++;
		panel.add(field_brand, gbc);
		field_brand.setSelectedItem(brandsMap.get(article.getBrand()));

		JLabel label_reference = new JLabel("Référence fabricant");
		label_reference.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridx = 3;
		gbc.gridy = 2;
		panel.add(label_reference, gbc);
		field_reference = new JTextField();
		field_reference.setPreferredSize(new Dimension(150, 25));
		gbc.gridy++;
		panel.add(field_reference, gbc);
		field_reference.setText(article.getReference());

		JLabel label_unit_price = new JLabel("Prix unitaire");
		label_unit_price.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridx = 5;
		gbc.gridwidth = 1;
		gbc.gridy = 2;
		panel.add(label_unit_price, gbc);
		field_unit_price = new JFormattedTextField(formatter2);
		field_unit_price.setPreferredSize(new Dimension(150, 25));
		gbc.gridy++;
		panel.add(field_unit_price, gbc);
		field_unit_price.setText(article.getUnit_price() + "");
		field_unit_price.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					updateAmount();
				}
			}
		});

		JLabel label_note = new JLabel("Note");
		label_note.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 5;
		panel.add(label_note, gbc);
		field_note = new JTextArea();
		field_note.setPreferredSize(new Dimension(150, 25));
		gbc.gridheight = 3;
		gbc.gridy++;
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(new JScrollPane(field_note), gbc);
		field_note.setText(article.getNote());

		JLabel label_qte_dsm = new JLabel("Qte DSM");
		label_qte_dsm.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.gridx = 1;
		gbc.gridy = 8;
		panel.add(label_qte_dsm, gbc);
		field_qte_dsm = new JFormattedTextField(formatter2);
		field_qte_dsm.setPreferredSize(new Dimension(75, 25));
		gbc.gridy++;
		panel.add(field_qte_dsm, gbc);
		field_qte_dsm.setText(article.getQuantity_dsm() + "");

		JLabel label_qte_available = new JLabel("Qte Dispo");
		label_qte_available.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridx = 2;
		gbc.gridy = 8;
		panel.add(label_qte_available, gbc);
		field_qte_available = new JFormattedTextField(formatter2);
		field_qte_available.setPreferredSize(new Dimension(75, 25));
		gbc.gridy++;
		panel.add(field_qte_available, gbc);
		field_qte_available.setText(article.getQuantity_available() + "");

		JLabel label_qte_max = new JLabel("Qte Max");
		label_qte_max.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridx = 3;
		gbc.gridy = 8;
		panel.add(label_qte_max, gbc);
		field_qte_max = new JFormattedTextField(formatter2);
		field_qte_max.setPreferredSize(new Dimension(75, 25));
		gbc.gridy++;
		panel.add(field_qte_max, gbc);
		field_qte_max.setText(article.getQuantity_max() + "");

		JLabel label_qte_min = new JLabel("Qte Min");
		label_qte_min.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridx = 4;
		gbc.gridy = 8;
		panel.add(label_qte_min, gbc);
		field_qte_min = new JFormattedTextField(formatter2);
		field_qte_min.setPreferredSize(new Dimension(75, 25));
		gbc.gridy++;
		panel.add(field_qte_min, gbc);
		field_qte_min.setText(article.getQuantity_min() + "");

		JLabel label_qte_to_com = new JLabel("Qte à commander");
		label_qte_to_com.setAlignmentX(Component.LEFT_ALIGNMENT);
		gbc.gridx = 5;
		gbc.gridy = 8;
		panel.add(label_qte_to_com, gbc);
		field_qte_to_com = new JFormattedTextField(formatter2);
		field_qte_to_com.setPreferredSize(new Dimension(150, 25));
		gbc.gridy++;
		panel.add(field_qte_to_com, gbc);
		field_qte_to_com.setText(article.getQuantity_to_command() + "");

		add(panel);

		JPanel choice = new JPanel();

		if (!creation) {
			JButton apply = new JButton("Appliquer");
			apply.addActionListener(e -> showApplyChoiceEdit(articlePanel, article));
			choice.add(apply);
			addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					showApplyChoiceEdit(articlePanel, article);
				}
			});
		} else {
			JButton save = new JButton("Enregistrer");
			save.addActionListener(e -> showApplyChoiceCreate(articlePanel, article));
			choice.add(save);
			addWindowListener(new WindowAdapter() {

				@Override
				public void windowClosing(WindowEvent e) {
					showApplyChoiceCreate(articlePanel, article);
				}
			});
		}

		add(choice);

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	}

	private void updateAmount() {
		try {
			NumberFormat format = new DecimalFormat("#,##");
			float quantity = format.parse(field_quantity.getText()).floatValue();
			float unit_price = format.parse(field_unit_price.getText()).floatValue();
			field_amount.setText(quantity * unit_price + "");
			field_amount.revalidate();
			field_amount.repaint();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void showApplyChoiceEdit(ArticlePanel panel, Article article) {
		int choice = JOptionPane.showConfirmDialog(this, "Voulez-vous enregistrer vos modifications?", "Attention",
				JOptionPane.YES_NO_CANCEL_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			try {
				NumberFormat format = new DecimalFormat("#,##");
				// Setting new values
				article.setLibelle(field_libelle.getText());
				article.setBrand(((Brand) field_brand.getSelectedItem()).getId());
				article.setReference(field_reference.getText());
				article.setNote(field_note.getText());
				article.setPlace(field_place.getText());
				article.setQuantity(format.parse(field_quantity.getText()).floatValue());
				article.setUnit((ArticleUnit) field_unit.getSelectedItem());
				article.setUnit_price(format.parse(field_unit_price.getText()).floatValue());
				article.setQuantity_dsm(format.parse(field_qte_dsm.getText()).floatValue());
				article.setQuantity_available(format.parse(field_qte_available.getText()).floatValue());
				article.setQuantity_min(format.parse(field_qte_min.getText()).floatValue());
				article.setQuantity_max(format.parse(field_qte_max.getText()).floatValue());
				article.setQuantity_to_command(format.parse(field_qte_to_com.getText()).floatValue());

				article.saveToDB(panel.getApp().getDataBase());
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(panel, "Une erreur est survenue: " + e.getMessage());
				panel.refreshData();
			} catch (ParseException test) {

			}
		}
		if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.YES_OPTION) {
			setVisible(false);
			dispose();
		}
	}

	private void showApplyChoiceCreate(ArticlePanel panel, Article article) {
		int choice = JOptionPane.showConfirmDialog(this, "Voulez-vous créer cet article?", "Attention",
				JOptionPane.YES_NO_CANCEL_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			boolean problem = false;
			if (field_brand.getSelectedIndex() == -1) {
				problem = true;
				field_brand.setBorder(BorderFactory.createLineBorder(Color.RED));
				field_brand.repaint();
			}
			if (field_libelle.getText() == null || field_libelle.getText().equals("")) {
				problem = true;
				field_libelle.setBorder(BorderFactory.createLineBorder(Color.RED));
				field_libelle.repaint();
			}

			if (problem) {
				JOptionPane.showMessageDialog(panel, "Veuillez remplir les champs obligatoires");
				return;
			}
			try {
				NumberFormat format = new DecimalFormat("#,##");
				// Setting new values
				article.setLibelle(field_libelle.getText());
				article.setBrand(((Brand) field_brand.getSelectedItem()).getId());
				article.setReference(field_reference.getText());
				article.setNote(field_note.getText());
				article.setPlace(field_place.getText());
				article.setQuantity(format.parse(field_quantity.getText()).floatValue());
				article.setUnit((ArticleUnit) field_unit.getSelectedItem());
				article.setUnit_price(format.parse(field_unit_price.getText()).floatValue());
				article.setQuantity_dsm(format.parse(field_qte_dsm.getText()).floatValue());
				article.setQuantity_available(format.parse(field_qte_available.getText()).floatValue());
				article.setQuantity_min(format.parse(field_qte_min.getText()).floatValue());
				article.setQuantity_max(format.parse(field_qte_max.getText()).floatValue());
				article.setQuantity_to_command(format.parse(field_qte_to_com.getText()).floatValue());

				article.insertIntoDB(panel.getApp().getDataBase());
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(panel, "Une erreur est survenue: " + e.getMessage());
				panel.refreshData();
			} catch (ParseException test) {

			}
		}
		if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.YES_OPTION) {
			setVisible(false);
			dispose();
		}
	}

}
