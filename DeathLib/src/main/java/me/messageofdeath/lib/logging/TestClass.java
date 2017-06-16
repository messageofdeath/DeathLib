package me.messageofdeath.lib.logging;

import me.messageofdeath.lib.databasenew.mysql.*;
import org.bukkit.plugin.java.JavaPlugin;

public class TestClass extends DeathMySQLDB {

    public TestClass(JavaPlugin plugin, String prefix, String table, String host, String port, String db, String user, String pass) {
        super(plugin, prefix, table, host, port, db, user, pass);
    }

    @Override
    public void createTable() {
        query(new QueryBuilder(getTable()).createTable()
                .column(Column.dataType("Name", DataTypes.VARCHAR))
                .column(Column.dataType("Name", DataTypes.Limit.VARCHAR, 16))
                .column(Column.dataType("Money", DataTypes.DOUBLE))
                .column(Column.dataType("MoneyFormatted", DataTypes.FloatingPoint.DOUBLE, 15, 2))
                .build());
    }

    public void load() {
        ResultQuery result = query(new QueryBuilder(getTable()).selectWhere().where("Name", "messageofdeath").build());
        result.getString("Name", "DEFAULT_NAME");
        result.getDouble("Money", 0);
        result.getDouble("MoneyFormatted", 0);
    }

    public void save() {
        if(!query(new QueryBuilder(getTable()).selectWhere().where("Name", "messageofdeath").build()).contains(null)) {
            query(new QueryBuilder(getTable()).insert()
                    .insert("Name", "messageofdeath")
                    .insert("Money", 200.121232D)
                    .insert("MoneyFormatted", 200.12D)
                    .build());
        }else{
            query(new QueryBuilder(getTable()).update()
                    .set("Name", "messageofdeath")
                    .set("Money", 200.121232D)
                    .set("MoneyFormatted", 200.12D).toWhere()
                    .where("Name", "messageofdeath").build());
        }
    }
}
