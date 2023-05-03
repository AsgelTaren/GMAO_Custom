package gmao_custom.data;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import gmao_custom.app.Utils;

public class DataBase {

	private File file;

	private Connection conn;
	private Statement st;

	public DataBase(File file) throws SQLException {
		this.file = file;

		ensureFileExistence();

		conn = DriverManager.getConnection("jdbc:sqlite:" + file);
		st = conn.createStatement();
		st.setQueryTimeout(30);
		createTables();
	}

	private void ensureFileExistence() {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void createTables() {
		// Loading index.json
		String data = null;
		try {
			data = Utils.readAllData(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("gmao_custom/tables/index.json"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		// Converting data to JSon Object
		JsonObject obj = JsonParser.parseString(data).getAsJsonObject();
		JsonArray tablesArray = obj.get("tables").getAsJsonArray();
		for (int i = 0; i < tablesArray.size(); i++) {
			String tableName = tablesArray.get(i).getAsString();
			String tableCreator = null;
			try {
				tableCreator = Utils.readAllData(Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("gmao_custom/tables/" + tableName + ".sqltable"));
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Error while loading table creator for table " + tableName + "! Creation aborted");
				break;
			}
			System.out.println("Loaded table creator for table " + tableName + "!");

			try {
				st.executeUpdate(tableCreator);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error while trying to create table " + tableName);
			}
			System.out.println("Created table " + tableName + " if necessary!");
		}
	}

	public Statement getStatement() {
		return st;
	}

	public Connection getConnection() {
		return conn;
	}
}