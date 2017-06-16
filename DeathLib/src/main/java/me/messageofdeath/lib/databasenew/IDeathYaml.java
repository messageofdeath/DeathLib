package me.messageofdeath.lib.databasenew;

import java.util.ArrayList;

public interface IDeathYaml {

    void changeFile(String fileName);

    void changeFile(String fileName, String fileLocation);

    void onStartup();

    void onShutDown();

    void reload();

    void save();

    void set(String where, Object set);

    ArrayList<String> getSection(String where, ArrayList<String> fallback);
}
