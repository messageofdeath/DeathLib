package me.messageofdeath.lib.databasenew;

import me.messageofdeath.lib.databasenew.mysql.ResultQuery;

public interface IDeathMySQL {

    void open();

    void close();

    void createTable();

    ResultQuery query(String query);

    boolean checkConnection();

    boolean doesTableExist();
}
