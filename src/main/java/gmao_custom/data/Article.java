package gmao_custom.data;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Article {

	private int numero;
	private String libelle;
	private int brand;
	private String reference;
	private String note;
	private String place;
	private float quantity;
	private ArticleUnit unit;
	private float unit_price;
	private float quantity_dsm;
	private float quantity_available;
	private float quantity_min;
	private float quantity_max;
	private float quantity_to_command;

	public Article(int numero, String libelle, int brand, String reference, String note, String place, float quantity,
			int unit, float unit_price, float quantity_dsm, float quantity_available, float quantity_min,
			float quantity_max, float quantity_to_command) {
		this.numero = numero;
		this.libelle = libelle;
		this.brand = brand;
		this.reference = reference;
		this.note = note;
		this.place = place;
		this.quantity = quantity;
		this.unit = ArticleUnit.values()[unit];
		this.unit_price = unit_price;
		this.quantity_dsm = quantity_dsm;
		this.quantity_available = quantity_available;
		this.quantity_min = quantity_min;
		this.quantity_max = quantity_max;
		this.quantity_to_command = quantity_to_command;
	}

	public Article(ResultSet set) throws SQLException {
		this.numero = set.getInt("id");
		this.libelle = set.getString("libelle");
		this.brand = set.getInt("brand");
		this.reference = set.getString("reference");
		this.note = set.getString("note");
		this.place = set.getString("place");
		this.quantity = set.getFloat("quantity");
		this.unit = (set.getInt("brand") >= 0 && set.getInt("brand") < ArticleUnit.values().length)
				? ArticleUnit.values()[set.getInt("brand")]
				: ArticleUnit.values()[0];
		this.unit_price = set.getFloat("unit_price");
		this.quantity_dsm = set.getFloat("quantity_dsm");
		this.quantity_min = set.getFloat("quantity_min");
		this.quantity_max = set.getFloat("quantity_max");
		this.quantity_to_command = set.getFloat("quantity_to_command");
	}

	public void saveToDB(DataBase db) throws SQLException {
		db.getStatement()
				.executeUpdate("update articles set libelle=\"" + libelle + "\",brand=" + brand + ",reference=\""
						+ reference + "\",note=\"" + note + "\",place=\"" + place + "\",quantity=" + quantity
						+ ",unit_price=" + unit_price + ",quantity_dsm=" + quantity_dsm + ",quantity_min="
						+ quantity_min + ",quantity_max=" + quantity_max + ",quantity_to_command=" + quantity_to_command
						+ " where id=" + numero + ";");
	}

	public void insertIntoDB(DataBase db) throws SQLException {
		db.getStatement().executeUpdate(
				"insert into articles (libelle,brand,reference,note,place,quantity,unit_price,quantity_dsm,quantity_min,quantity_max,quantity_to_command) "
						+ "values (\"" + libelle + "\"," + brand + ",\"" + reference + "\",\"" + note + "\",\"" + place
						+ "\"," + quantity + "," + unit_price + "," + quantity_dsm + "," + quantity_min + ","
						+ quantity_max + "," + quantity_to_command + ");");
	}

	public static final Article createArticle(DataBase db) {
		try {
			ResultSet set = db.getStatement().executeQuery("select seq from sqlite_sequence where name=\"articles\";");
			int seq = -1;
			if (set.next()) {
				seq = set.getInt("seq") + 1;
			}
			if (seq == -1) {
				throw new SQLException();
			}
			return new Article(seq, "", 0, "", "", "", 0, 0, 0, 0, 0, 0, 0, 0);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getBrand() {
		return brand;
	}

	public void setBrand(int brand) {
		this.brand = brand;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public ArticleUnit getUnit() {
		return unit;
	}

	public void setUnit(ArticleUnit unit) {
		this.unit = unit;
	}

	public float getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(float unit_price) {
		this.unit_price = unit_price;
	}

	public float getQuantity_dsm() {
		return quantity_dsm;
	}

	public void setQuantity_dsm(float quantity_dsm) {
		this.quantity_dsm = quantity_dsm;
	}

	public float getQuantity_available() {
		return quantity_available;
	}

	public void setQuantity_available(float quantity_available) {
		this.quantity_available = quantity_available;
	}

	public float getQuantity_min() {
		return quantity_min;
	}

	public void setQuantity_min(float quantity_min) {
		this.quantity_min = quantity_min;
	}

	public float getQuantity_max() {
		return quantity_max;
	}

	public void setQuantity_max(float quantity_max) {
		this.quantity_max = quantity_max;
	}

	public float getQuantity_to_command() {
		return quantity_to_command;
	}

	public void setQuantity_to_command(float quantity_to_command) {
		this.quantity_to_command = quantity_to_command;
	}

}