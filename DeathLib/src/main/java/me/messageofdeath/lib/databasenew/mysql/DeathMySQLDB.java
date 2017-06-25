package me.messageofdeath.lib.databasenew.mysql;

import me.messageofdeath.lib.databasenew.IDeathMySQL;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public abstract class DeathMySQLDB implements IDeathMySQL {

    private JavaPlugin plugin;
    private String table;
    private transient MySQLBackend mysql;
    private transient final String prefix;

    public DeathMySQLDB(JavaPlugin plugin, String prefix, String table, String host, String port, String db, String user, String pass) {
        this.plugin = plugin;
        this.prefix = prefix;
        this.table = table;
        this.mysql = new MySQLBackend(plugin.getLogger(), prefix, host, port, db, user, pass);
        open();
        if(!doesTableExist()) {
            createTable();
        }
        close();
    }

    public String getTable() {
        return table;
    }

    @Override
    public void open() {
        try {
            mysql.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if(checkConnection()) {
            mysql.close();
        }
    }

    @Override
    public ResultQuery query(String query) {
        ResultQuery set = null;
        open();
        if(checkConnection()) {
            try {
               set = new ResultQuery(mysql.query(query), this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return set;
    }

    @Override
    public boolean checkConnection() {
        return mysql.checkConnection();
    }

    @Override
    public boolean doesTableExist() {
        return checkConnection() && mysql.checkTable(table);
    }
}
