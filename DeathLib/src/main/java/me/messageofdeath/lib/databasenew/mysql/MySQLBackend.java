package me.messageofdeath.lib.databasenew.mysql;

import java.sql.*;
import java.util.logging.Logger;

public class MySQLBackend {
  protected final Logger log;
  protected final String PREFIX;
  protected final String DATABASE_PREFIX;
  protected final boolean connected;
  protected Connection connection;
  public int lastUpdate;
  private String hostname = "localhost";
  private String portnmbr = "3306";
  private String username = "minecraft";
  private String password = "";
  private String database = "minecraft";

  protected enum Statements {
    SELECT, INSERT, UPDATE, DELETE, DO, REPLACE, LOAD, HANDLER, CALL, CREATE, ALTER, DROP, TRUNCATE, RENAME, RELEASE, START, COMMIT, SAVEPOINT, ROLLBACK, LOCK, UNLOCK, PREPARE, EXECUTE, DEALLOCATE, SET, SHOW, DESCRIBE, EXPLAIN, HELP, USE, ANALYZE, ATTACH, BEGIN, DETACH, END, INDEXED, ON, PRAGMA, REINDEX, VACUUM;
  }

  public MySQLBackend(Logger log, String prefix, String hostname, String portnmbr, String database, String username, String password) {
    this.log = log;
    this.PREFIX = prefix;
    this.DATABASE_PREFIX = "[MySQLBackend] ";
    this.connected = false;
    this.connection = null;
    this.hostname = hostname;
    this.portnmbr = portnmbr;
    this.database = database;
    this.username = username;
    this.password = password;
  }

  protected String prefix(String message) {
    return this.PREFIX + this.DATABASE_PREFIX + message;
  }

  protected void writeInfo(String toWrite) {
    if (toWrite != null) {
      this.log.info(prefix(toWrite));
    }
  }

  protected void writeError(String toWrite, boolean severe) {
    if (toWrite != null) {
      if (severe) {
        this.log.severe(prefix(toWrite));
      } else {
        this.log.warning(prefix(toWrite));
      }
    }
  }

  protected boolean initialize() {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      return true;
    } catch (ClassNotFoundException e) {
      writeError("MySQLBackend driver class missing: " + e.getMessage() + ".", true);
    }
    return false;
  }

  public Connection open()
          throws SQLException {
    if (initialize()) {
      String url;
      url = "jdbc:mysql://" + this.hostname + ":" + this.portnmbr + "/" + this.database;
      this.connection = DriverManager.getConnection(url, this.username, this.password);
      return this.connection;
    }
    throw new SQLException("Cannot open a MySQLBackend connection. The driver class is missing.");
  }

  public boolean close() {
    if (this.connection != null) {
      try {
        this.connection.close();
        return true;
      } catch (SQLException e) {
        writeError("Could not close connection, SQLException: " + e.getMessage(), true);
        return false;
      }
    }
    writeError("Could not close connection, it is null.", true);
    return false;
  }

  public Connection getConnection() {
    return this.connection;
  }

  public boolean checkConnection() {
    return this.connection != null;
  }

  public ResultSet query(String query)
          throws SQLException {
    Statement statement;
    ResultSet result;

    statement = this.connection.createStatement();
    result = statement.executeQuery("SELECT CURTIME()");
    switch (getStatement(query)) {
      case ALTER:
      case CALL:
      case DEALLOCATE:
      case RELEASE:
      case RENAME:
      case REPLACE:
        result = statement.executeQuery(query);
        break;
      case ANALYZE:
      case ATTACH:
      case BEGIN:
      case COMMIT:
      case CREATE:
      case DELETE:
      case DESCRIBE:
      case DETACH:
      case DO:
      case DROP:
      case END:
      case EXECUTE:
      case EXPLAIN:
      case HANDLER:
      case HELP:
      case INDEXED:
      case INSERT:
      case LOAD:
      case LOCK:
      case ON:
      case PRAGMA:
      case PREPARE:
      case REINDEX:
        this.lastUpdate = statement.executeUpdate(query);
        break;
      case ROLLBACK:
        writeError("Please create a new connection to use a different database.", false);
        break;
      default:
        result = statement.executeQuery(query);
    }
    return result;
  }

  public PreparedStatement prepare(String query)
          throws SQLException {
    return this.connection.prepareStatement(query);
  }

  protected Statements getStatement(String query)
          throws SQLException {
    if ((query.length() > 5) && (query.substring(0, 6).equalsIgnoreCase("SELECT"))) {
      return Statements.SELECT;
    }
    if ((query.length() > 5) && (query.substring(0, 6).equalsIgnoreCase("INSERT"))) {
      return Statements.INSERT;
    }
    if ((query.length() > 5) && (query.substring(0, 6).equalsIgnoreCase("UPDATE"))) {
      return Statements.UPDATE;
    }
    if ((query.length() > 5) && (query.substring(0, 6).equalsIgnoreCase("DELETE"))) {
      return Statements.DELETE;
    }
    if ((query.length() > 1) && (query.substring(0, 2).equalsIgnoreCase("DO"))) {
      return Statements.DO;
    }
    if ((query.length() > 6) && (query.substring(0, 7).equalsIgnoreCase("REPLACE"))) {
      return Statements.REPLACE;
    }
    if ((query.length() > 3) && (query.substring(0, 4).equalsIgnoreCase("LOAD"))) {
      return Statements.LOAD;
    }
    if ((query.length() > 6) && (query.substring(0, 7).equalsIgnoreCase("HANDLER"))) {
      return Statements.HANDLER;
    }
    if ((query.length() > 3) && (query.substring(0, 4).equalsIgnoreCase("CALL"))) {
      return Statements.CALL;
    }
    if ((query.length() > 5) && (query.substring(0, 6).equalsIgnoreCase("CREATE"))) {
      return Statements.CREATE;
    }
    if ((query.length() > 4) && (query.substring(0, 5).equalsIgnoreCase("ALTER"))) {
      return Statements.ALTER;
    }
    if ((query.length() > 3) && (query.substring(0, 4).equalsIgnoreCase("DROP"))) {
      return Statements.DROP;
    }
    if ((query.length() > 7) && (query.substring(0, 8).equalsIgnoreCase("TRUNCATE"))) {
      return Statements.TRUNCATE;
    }
    if ((query.length() > 5) && (query.substring(0, 6).equalsIgnoreCase("RENAME"))) {
      return Statements.RENAME;
    }
    if ((query.length() > 4) && (query.substring(0, 5).equalsIgnoreCase("START"))) {
      return Statements.START;
    }
    if ((query.length() > 5) && (query.substring(0, 6).equalsIgnoreCase("COMMIT"))) {
      return Statements.COMMIT;
    }
    if ((query.length() > 7) && (query.substring(0, 8).equalsIgnoreCase("ROLLBACK"))) {
      return Statements.ROLLBACK;
    }
    if ((query.length() > 8) && (query.substring(0, 9).equalsIgnoreCase("SAVEPOINT"))) {
      return Statements.SAVEPOINT;
    }
    if ((query.length() > 3) && (query.substring(0, 4).equalsIgnoreCase("LOCK"))) {
      return Statements.LOCK;
    }
    if ((query.length() > 5) && (query.substring(0, 6).equalsIgnoreCase("UNLOCK"))) {
      return Statements.UNLOCK;
    }
    if ((query.length() > 6) && (query.substring(0, 7).equalsIgnoreCase("PREPARE"))) {
      return Statements.PREPARE;
    }
    if ((query.length() > 6) && (query.substring(0, 7).equalsIgnoreCase("EXECUTE"))) {
      return Statements.EXECUTE;
    }
    if ((query.length() > 9) && (query.substring(0, 10).equalsIgnoreCase("DEALLOCATE"))) {
      return Statements.DEALLOCATE;
    }
    if ((query.length() > 2) && (query.substring(0, 3).equalsIgnoreCase("SET"))) {
      return Statements.SET;
    }
    if ((query.length() > 3) && (query.substring(0, 4).equalsIgnoreCase("SHOW"))) {
      return Statements.SHOW;
    }
    if ((query.length() > 7) && (query.substring(0, 8).equalsIgnoreCase("DESCRIBE"))) {
      return Statements.DESCRIBE;
    }
    if ((query.length() > 6) && (query.substring(0, 7).equalsIgnoreCase("EXPLAIN"))) {
      return Statements.EXPLAIN;
    }
    if ((query.length() > 3) && (query.substring(0, 4).equalsIgnoreCase("HELP"))) {
      return Statements.HELP;
    }
    if ((query.length() > 2) && (query.substring(0, 3).equalsIgnoreCase("USE"))) {
      return Statements.USE;
    }
    if ((query.length() > 6) && (query.substring(0, 7).equalsIgnoreCase("ANALYSE"))) {
      return Statements.ANALYZE;
    }
    if ((query.length() > 5) && (query.substring(0, 6).equalsIgnoreCase("ATTACH"))) {
      return Statements.ATTACH;
    }
    if ((query.length() > 4) && (query.substring(0, 5).equalsIgnoreCase("BEGIN"))) {
      return Statements.BEGIN;
    }
    if ((query.length() > 5) && (query.substring(0, 6).equalsIgnoreCase("DETACH"))) {
      return Statements.DETACH;
    }
    if ((query.length() > 2) && (query.substring(0, 3).equalsIgnoreCase("END"))) {
      return Statements.END;
    }
    if ((query.length() > 6) && (query.substring(0, 7).equalsIgnoreCase("INDEXED"))) {
      return Statements.INDEXED;
    }
    if ((query.length() > 1) && (query.substring(0, 2).equalsIgnoreCase("ON"))) {
      return Statements.ON;
    }
    if ((query.length() > 5) && (query.substring(0, 6).equalsIgnoreCase("PRAGMA"))) {
      return Statements.PRAGMA;
    }
    if ((query.length() > 6) && (query.substring(0, 7).equalsIgnoreCase("REINDEX"))) {
      return Statements.REINDEX;
    }
    if ((query.length() > 6) && (query.substring(0, 7).equalsIgnoreCase("RELEASE"))) {
      return Statements.RELEASE;
    }
    if ((query.length() > 5) && (query.substring(0, 6).equalsIgnoreCase("VACUUM"))) {
      return Statements.VACUUM;
    }
    throw new SQLException("Unknown statement \"" + query + "\".");
  }

  public boolean createTable(String query) {
    Statement statement;
    if ((query.equals("")) || (query == null)) {
      writeError("Could not create table: query is empty or null.", true);
      return false;
    }
    try {
      statement = this.connection.createStatement();
      statement.execute(query);
    } catch (SQLException e) {
      writeError("Could not create table, SQLException: " + e.getMessage(), true);
      return false;
    }
    return true;
  }

  public boolean checkTable(String table) {
    try {
      Statement statement = this.connection.createStatement();
      ResultSet result = statement.executeQuery("SELECT * FROM " + table);
      return result != null;
    } catch (SQLException e) {
      writeError("Could not check if table \"" + table + "\" exists, SQLException: " + e.getMessage(), true);
    }
    return false;
  }

  public boolean wipeTable(String table) {
    Statement statement;
    String query;
    try {
      if (!checkTable(table)) {
        writeError("Table \"" + table + "\" does not exist.", true);
        return false;
      }
      statement = this.connection.createStatement();
      query = "DELETE FROM " + table + ";";
      statement.executeUpdate(query);

      return true;
    } catch (SQLException e) {
      writeError("Could not wipe table, SQLException: " + e.getMessage(), true);
    }
    return false;
  }
}
