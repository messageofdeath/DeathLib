package me.messageofdeath.lib.databasenew.mysql;

public class Column {

    private transient String column;

    Column(String column) {
        this.column = column;
    }

    public String getColumn() {
        return column;
    }

    public static Column dataType(String name, DataTypes type) {
        return new Column(name + " " + type.name());
    }

    public static Column dataType(String name, DataTypes.Limit type, int limiter) {
        return new Column(name + " " + type.name() + "("+limiter+")");
    }

    public static Column dataType(String name, DataTypes.FloatingPoint type, int length, int decimals) {
        return new Column(name + " " + type.name() + "("+length+", "+decimals+")");
    }
}
