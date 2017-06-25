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
                .column(Column.dataType("Name", DataTypes.Limit.VARCHAR, 16))
                .column(Column.dataType("Job", DataTypes.VARCHAR))
                .column(Column.dataType("Money", DataTypes.DOUBLE))
                .column(Column.dataType("MoneyFormatted", DataTypes.FloatingPoint.DOUBLE, 15, 2))
                .build()).close();
    }

    public void load() {
        ResultQuery result;

        //This query would return ALL rows matching "Name = 'messageofdeath'" | Returns all columns: Name, Job, Money, MoneyFormatted
        result = query(new QueryBuilder(getTable()).select().allColumns().where("Name", "messageofdeath").build());

        //This query would return 1 row matching "Name = 'messageofdeath'" | Returns columns: Money, MoneyFormatted
        result = query(new QueryBuilder(getTable()).select().column("Money").column("MoneyFormatted").toWhere().where("Name", "messageofdeath").limit(1));

        //This statement would return every single row in the database but only the Column = Name
        result = query(new QueryBuilder(getTable()).select().column("Name").build());
        result = query(new QueryBuilder(getTable()).select().column("Name").limit(20));//With Limit of 20 rows

        //This statement would return every single row in the database with every single column.
        result = query(new QueryBuilder(getTable()).select().buildAllColumns());
        result = query(new QueryBuilder(getTable()).select().buildAllColumnsLimit(20));// With Limit of 20 rows


        //------------------- Things that produce errors! -------------------

        //This following statements would throw a IllegalStateException. As no columns were defined
        result = query(new QueryBuilder(getTable()).select().build());
        result = query(new QueryBuilder(getTable()).select().buildLimit(20));
        result = query(new QueryBuilder(getTable()).select().toWhere().build());

        //The following statements would throw a IllegalStateException. As a column was defined, but asked for all columns after.
        result = query(new QueryBuilder(getTable()).select().column("Name").buildAllColumns());
        result = query(new QueryBuilder(getTable()).select().column("Name").buildAllColumnsLimit(20));
        result = query(new QueryBuilder(getTable()).select().column("Name").allColumns().build());
    }

    public void save() {
        if(!query(new QueryBuilder(getTable()).select().allColumns().where("Name", "messageofdeath").and("Job", "Miner").build()).has()) {
            query(new QueryBuilder(getTable()).insert()
                    .insert("Name", "messageofdeath")
                    .insert("Money", 200.121232D)
                    .insert("MoneyFormatted", 200.12D)
                    .build()).close();
        }else{
            query(new QueryBuilder(getTable()).update()
                    .set("Name", "messageofdeath")
                    .set("Money", 200.121232D)
                    .set("MoneyFormatted", 200.12D).toWhere()
                    .where("Name", "messageofdeath").build()).close();
        }
    }
}
