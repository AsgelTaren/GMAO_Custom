package gmao_custom.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import gmao_custom.data.Brand;

@SuppressWarnings("serial")
public class BrandTableModel extends AbstractTableModel {

	private App app;
	private ArrayList<Brand> brands;

	public BrandTableModel(App app) {
		super();
		this.app = app;
		query();
	}

	@Override
	public int getRowCount() {
		return brands.size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Brand target = brands.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return target.getId();
		case 1:
			return target.getName();
		case 2:
			return target.getColor();
		}
		return null;
	}

	@Override
	public String getColumnName(int col) {
		switch (col) {
		case 0:
			return "Numéro";
		case 1:
			return "Nom";
		case 2:
			return "Couleur";
		}
		return null;
	}

	@Override
	public Class<?> getColumnClass(int col) {
		return String.class;
	}

	public void query() {
		try {
			ResultSet set = app.getDataBase().getStatement().executeQuery("SELECT * FROM brands;");
			brands = new ArrayList<>();
			while (set.next()) {
				brands.add(new Brand(set));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
