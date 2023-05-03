package gmao_custom.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import gmao_custom.data.Article;
import gmao_custom.data.Brand;
import gmao_custom.data.DataBase;

@SuppressWarnings("serial")
public class ArticleTableModel extends AbstractTableModel {

	private DataBase db;

	private ArrayList<Article> articles;
	private HashMap<Integer, Brand> brands;

	public ArticleTableModel(DataBase db) {
		this.db = db;
	}

	public void query() {
		// Loading brands

		try {
			ResultSet set = db.getStatement().executeQuery("SELECT * FROM brands");
			brands = new HashMap<>();
			while (set.next()) {
				Brand brand = new Brand(set);
				brands.put(brand.getId(), brand);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Loading articles
		try {
			ResultSet set = db.getStatement().executeQuery("SELECT * FROM articles");
			articles = new ArrayList<>();
			while (set.next()) {
				articles.add(new Article(set));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int getRowCount() {
		return articles.size();
	}

	@Override
	public int getColumnCount() {
		return 14;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Article target = articles.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return target.getNumero();
		case 1:
			return target.getLibelle();
		case 2:
			return brands.get(target.getBrand());
		case 3:
			return target.getReference();
		case 4:
			return target.getNote();
		case 5:
			return target.getPlace();
		case 6:
			return target.getQuantity();
		case 7:
			return target.getUnit();
		case 8:
			return target.getUnit_price() + " \u20ac";
		case 9:
			return target.getQuantity_dsm();
		case 10:
			return target.getQuantity_available();
		case 11:
			return target.getQuantity_min();
		case 12:
			return target.getQuantity_max();
		case 13:
			return target.getQuantity_to_command();
		}
		return null;
	}

	@Override
	public String getColumnName(int col) {
		switch (col) {
		case 0:
			return "Numero";
		case 1:
			return "Libelle";
		case 2:
			return "Marque";
		case 3:
			return "Reference";
		case 4:
			return "Note";
		case 5:
			return "Emplacement";
		case 6:
			return "Quantite";
		case 7:
			return "Unite";
		case 8:
			return "PrixUnitaire";
		case 9:
			return "QteDSM";
		case 10:
			return "QteDispo";
		case 11:
			return "QteMin";
		case 12:
			return "QteMax";
		case 13:
			return "QteCommande";
		}

		return null;
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return String.class;
	}

	public ArrayList<Article> getArticles() {
		return articles;
	}

	public HashMap<Integer, Brand> getBrandsMap() {
		return brands;
	}

}
