package me.messageofdeath.lib.database;

import java.sql.Array;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import me.messageofdeath.lib.Main;
import org.bukkit.plugin.java.JavaPlugin;

public class MySQLDatabase {

	private MySQL mysql = null;

	public MySQLDatabase(JavaPlugin plugin, String host, String port, String db, String user, String pass) {
		this.mysql = new MySQL(plugin.getLogger(), "[" + plugin.getDescription().getName() + "]", host, port, db, user, pass);
	}

	public void open() {
		try {
			this.mysql.open();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		this.mysql.close();
	}


	/**
	 *
	 * This is used to create a table
	 *
	 * @param tableName - table name
	 */
	public void createTable(String tableName, String columns) {
		try {
			this.mysql.query("CREATE TABLE " + tableName + " (" + columns + ")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * This is used to add a column to an existing table
	 *
	 * @param tableName - table name
	 * @param column - Should be in format "column_name datatype"
	 */
	public void addColumn(String tableName, String column) {
		try {
			this.mysql.query("ALTER TABLE " + tableName + " ADD " + column);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * This is used to delete/drop an existing column
	 *
	 * @param tableName - table name+
	 * @param column - Should be in format "column_name"
	 */
	public void dropColumn(String tableName, String column) {
		try {
			this.mysql.query("ALTER TABLE " + tableName + " DROP COLUMN " + column);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 *
	 * This is used to modify the datatype of an existing column
	 *
	 * @param tableName - table name
	 * @param column - Should be in format "column_name datatype"
	 */
	public void modifyColumnDatatype(String tableName, String column) {
		try {
			this.mysql.query("ALTER TABLE " + tableName + " MODIFY COLUMN " + column);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkConnection() {
		if (this.mysql.checkConnection()) {
			return true;
		}
		Main.log("[MySQLBackend] The mysql connection was never established. Check your mysql server or your credentials.", true);
		return false;
	}

	public boolean doesTableExist(String table) {
		return checkConnection() && this.mysql.checkTable(table);
	}

	public String getString(String table, String where, String columnNeeded, String fallback) {
		if (checkConnection()) {
			try {
				ResultSet rs = this.mysql.query("SELECT * FROM " + table + " WHERE " + where);
				if ((rs != null) && (rs.first())) {
					return rs.getString(columnNeeded);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fallback;
	}

	public int getInteger(String table, String where, String columnNeeded, int fallback) {
		if (checkConnection()) {
			try {
				ResultSet rs = this.mysql.query("SELECT * FROM " + table + " WHERE " + where);
				if ((rs != null) && (rs.first())) {
					return rs.getInt(columnNeeded);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fallback;
	}

	public double getDouble(String table, String where, String columnNeeded, double fallback) {
		if (checkConnection()) {
			try {
				ResultSet rs = this.mysql.query("SELECT * FROM " + table + " WHERE " + where);
				if ((rs != null) && (rs.first())) {
					return rs.getDouble(columnNeeded);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fallback;
	}

	public boolean getBoolean(String table, String where, String columnNeeded, boolean fallback) {
		if (checkConnection()) {
			try {
				ResultSet rs = this.mysql.query("SELECT * FROM " + table + " WHERE " + where);
				if ((rs != null) && (rs.first())) {
					return rs.getBoolean(columnNeeded);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fallback;
	}

	public Object getObject(String table, String where, String columnNeeded, Object fallback) {
		if (checkConnection()) {
			try {
				ResultSet rs = this.mysql.query("SELECT * FROM " + table + " WHERE " + where);
				if ((rs != null) && (rs.first())) {
					return rs.getObject(columnNeeded);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fallback;
	}

	public float getFloat(String table, String where, String columnNeeded, float fallback) {
		if (checkConnection()) {
			try {
				ResultSet rs = this.mysql.query("SELECT * FROM " + table + " WHERE " + where);
				if ((rs != null) && (rs.first())) {
					return rs.getFloat(columnNeeded);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fallback;
	}

	public Time getTime(String table, String where, String columnNeeded, Time fallback) {
		if (checkConnection()) {
			try {
				ResultSet rs = this.mysql.query("SELECT * FROM " + table + " WHERE " + where);
				if ((rs != null) && (rs.first())) {
					return rs.getTime(columnNeeded);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fallback;
	}

	public byte getByte(String table, String where, String columnNeeded, byte fallback) {
		if (checkConnection()) {
			try {
				ResultSet rs = this.mysql.query("SELECT * FROM " + table + " WHERE " + where);
				if ((rs != null) && (rs.first())) {
					return rs.getByte(columnNeeded);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fallback;
	}

	public Date getDate(String table, String where, String columnNeeded, Date fallback) {
		if (checkConnection()) {
			try {
				ResultSet rs = this.mysql.query("SELECT * FROM " + table + " WHERE " + where);
				if ((rs != null) && (rs.first())) {
					return rs.getDate(columnNeeded);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fallback;
	}

	public ArrayList<Object> getArray(String table, String where, String columnNeeded, ArrayList<Object> fallback) {
		if (checkConnection()) {
			try {
				ResultSet rs = this.mysql.query("SELECT * FROM " + table + " WHERE " + where);
				if ((rs != null) && (rs.first())) {
					Array temp = rs.getArray(columnNeeded);
					ArrayList<Object> temp2 = new ArrayList<>();
					while (temp.getResultSet().next()) {
						temp2.add(temp.getResultSet().getObject(columnNeeded));
					}
					return temp2;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fallback;
	}

	public ArrayList<String> getStringArray(String table, String columnNeeded, ArrayList<String> fallback) {
		ArrayList<String> all = new ArrayList<>();
		try {
			ResultSet rs = this.mysql.query("Select * FROM " + table);
			while (rs.next()) {
				all.add(rs.getString(columnNeeded));
			}
			return all;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fallback;
	}

	public ArrayList<String> getStringArray(String table, String where, String columnNeeded,
											ArrayList<String> fallback) {
		ArrayList<String> all = new ArrayList<>();
		try {
			ResultSet rs = this.mysql.query("Select * FROM " + table + " WHERE " + where);
			while (rs.next()) {
				all.add(rs.getString(columnNeeded));
			}
			return all;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fallback;
	}

	public int getIntegerAmount(String table, String where, String filter, int fallback) {
		int all = 0;
		try {
			ResultSet rs = this.mysql.query("Select * FROM " + table + " WHERE " + filter + " = " + where);
			while (rs.next()) {
				all++;
			}
			return all;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fallback;
	}

	public void insert(String table, String columns, String values) {
		if (checkConnection()) {
			try {
				this.mysql.query("INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ")");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void delete(String table, String where) {
		if (checkConnection()) {
			try {
				this.mysql.query("DELETE FROM " + table + " WHERE " + where);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void update(String table, String column, String filter) {
		if (checkConnection()) {
			try {
				this.mysql.query("UPDATE " + table + " SET " + column + " WHERE " + filter);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
