package me.messageofdeath.lib.databasenew.yaml;

import me.messageofdeath.lib.Main;
import me.messageofdeath.lib.Utilities;
import me.messageofdeath.lib.databasenew.IDeathDB;
import me.messageofdeath.lib.databasenew.IDeathYaml;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public abstract class DeathYamlDB implements IDeathDB, IDeathYaml {

    private JavaPlugin plugin;
    private String fileName;
    private String fileLocation;
    private File file;
    private FileConfiguration config;
    public boolean saveOnSet = true;

    public DeathYamlDB(JavaPlugin plugin) {
        this.plugin = plugin;
        this.config = new YamlConfiguration();
    }

    public DeathYamlDB(JavaPlugin plugin, String fileName) {
        this(plugin);
        changeFile(fileName);
    }

    public DeathYamlDB(JavaPlugin plugin, String fileName, String fileLocation) {
        this(plugin, fileName);
        changeFile(fileName, fileLocation);
    }

    @Override
    public void changeFile(String fileName) {
        changeFile(fileName, plugin.getDataFolder().getPath());
    }

    @Override
    public void changeFile(String fileName, String fileLocation) {
        this.fileName = fileName + ".yml";
        this.fileLocation = fileLocation;
        onStartup();
    }

    @Override
    public void onStartup() {
        if (this.fileLocation == null) {
            this.file = new File(this.plugin.getDataFolder(), this.fileName);
        } else {
            this.file = new File(this.fileLocation, this.fileName);
        }
        try {
            this.config = YamlConfiguration.loadConfiguration(this.file);
            if (!this.file.exists()) {
                this.file.getParentFile().mkdirs();
                this.file.createNewFile();
                if (this.plugin.getResource(this.fileName) != null) {
                    copy(this.plugin.getResource(this.fileName), this.file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onShutDown() {
        this.save();
    }

    @Override
    public void reload() {
        try {
            config.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        try {
            this.config.save(this.file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void set(String where, Object set) {
        this.config.set(where, set);
        if (this.saveOnSet) {
            save();
        }
    }

    @Override
    public boolean contains(String where) {
        return config.contains(where);
    }

    @Override
    public String getString(String where, String fallback) {
        return contains(where) ? config.getString(where) : fallback;
    }

    @Override
    public byte getByte(String where, byte fallback) {
        if(contains(where)) {
            String trans = config.getString(where);
            if(Utilities.isByte(trans)) {
                return Byte.valueOf(trans);
            }
        }
        return fallback;
    }

    @Override
    public short getShort(String where, short fallback) {
        if(contains(where)) {
            String trans = config.getString(where);
            if(Utilities.isShort(trans)) {
                return Short.valueOf(trans);
            }
        }
        return fallback;
    }

    @Override
    public int getInteger(String where, int fallback) {
        return contains(where) ? config.getInt(where) : fallback;
    }

    @Override
    public long getLong(String where, long fallback) {
        return contains(where) ? config.getLong(where) : fallback;
    }

    @Override
    public float getFloat(String where, float fallback) {
        if(contains(where)) {
            String trans = config.getString(where);
            if(Utilities.isFloat(trans)) {
                return Float.valueOf(trans);
            }
        }
        return fallback;
    }

    @Override
    public double getDouble(String where, double fallback) {
        return contains(where) ? config.getDouble(where) : fallback;
    }

    @Override
    public boolean getBoolean(String where, boolean fallback) {
        return contains(where) ? config.getBoolean(where) : fallback;
    }

    @Override
    public Object getObject(String where, Object fallback) {
        return contains(where) ? config.get(where) : fallback;
    }

    @Override
    public Time getTime(String where, Time fallback) {
        //Not supported
        return null;
    }

    @Override
    public Date getDate(String where, Date fallback) {
        //Not supported
        return null;
    }

    @Override
    public ArrayList<Object> getObjectArray(String where, ArrayList<Object> fallback) {
        return contains(where) ? (ArrayList<Object>)config.getList(where) : fallback;
    }

    @Override
    public ArrayList<String> getStringArray(String where, ArrayList<String> fallback) {
        return contains(where) ? (ArrayList<String>)config.getStringList(where) : fallback;
    }

    @Override
    public ArrayList<String> getSection(String where, ArrayList<String> fallback) {
        if(contains(where)) {
            ArrayList<String> list = new ArrayList<>();
            for(Object obj : config.getConfigurationSection(where).getKeys(false).toArray()) {
                list.add((String)obj);
            }
            return list;
        }
        return fallback;
    }

    @Override
    public int getOccurrences(String where) {
        return getSection(where, new ArrayList<>()).size();
    }

    private void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buf)) > 0) {
                out.write(buf, 0, bytesRead);
            }
            out.close();
        }catch(Exception e) {
            Main.logError("File", "DeathYamlDB", "copy(InputStream, File)", "Failed to write to file");
        }
    }
}
