package gmao_custom.data;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Brand {

	private int id;
	private String name;
	private Color color;

	public Brand(int id, String name, String color) {
		this.id = id;
		this.name = name;
		this.color = Color.decode(color);
	}

	public Brand(ResultSet set) throws SQLException {
		this.id = set.getInt("id");
		this.name = set.getString("name");
		this.color = Color.decode(set.getString("color"));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return name;
	}

}
