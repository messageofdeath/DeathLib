package me.messageofdeath.lib.database;

import me.messageofdeath.lib.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlDatabase {
	
	private JavaPlugin plugin;
	private String fileName;
	private String fileExtension;
	private String fileLocation;
	private File file;
	private FileConfiguration fileConfig;
	public boolean saveOnSet = true;

	public YamlDatabase(JavaPlugin plugin) {
		this.plugin = plugin;
		this.fileExtension = ".yml";
		this.fileConfig = new YamlConfiguration();
	}

	public YamlDatabase(JavaPlugin plugin, String fileName) {
		this.plugin = plugin;
		this.fileName = fileName;
		this.fileExtension = ".yml";
		this.fileConfig = new YamlConfiguration();
	}

	public YamlDatabase(JavaPlugin plugin, String fileName, String fileLocation) {
		this.plugin = plugin;
		this.fileName = fileName;
		this.fileExtension = ".yml";
		this.fileConfig = new YamlConfiguration();
		this.fileLocation = fileLocation;
	}

	public void changeFile(String fileName) {
		changeFile(fileName, this.plugin.getDataFolder().getPath());
	}

	public void changeFile(String fileName, String fileLocation) {
		this.fileName = fileName;
		this.fileLocation = fileLocation;
		onStartUp();
	}

	public void onStartUp() {
		if (this.fileLocation == null) {
			this.file = new File(this.plugin.getDataFolder(), this.fileName + this.fileExtension);
		} else {
			this.file = new File(this.fileLocation, this.fileName + this.fileExtension);
		}
		try {
			this.fileConfig = YamlConfiguration.loadConfiguration(this.file);
			if (!this.file.exists()) {
				this.file.getParentFile().mkdirs();
				this.file.createNewFile();
				if (this.plugin.getResource(this.fileName + this.fileExtension) != null) {
					copy(this.plugin.getResource(this.fileName + this.fileExtension), this.file);
				}
			}
		} catch (Exception e) {
			e.getCause();
		}
	}

	public void onShutDown() {
		this.save();
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
			Main.logError("File", "YamlDatabase", "copy(InputStream, File)", "Failed to write to file");
		}
	}

	public void reload() {
		try {
			this.fileConfig.load(this.file);
		} catch (Exception localException) {
		}
	}

	public void save() {
		try {
			this.fileConfig.save(this.file);
		} catch (Exception localException) {
		}
	}

	public ConfigurationSection getConfigurationSection(String key, ConfigurationSection fallback) {
		if (this.fileConfig.contains(key)) {
			return this.fileConfig.getConfigurationSection(key);
		}
		return fallback;
	}

	public ArrayList<String> getSection(String key, ArrayList<String> fallback) {
		if (this.fileConfig.contains(key)) {
			ArrayList<String> section = new ArrayList<String>();
			Object[] arrayOfObject;
			int j = (arrayOfObject = getConfigurationSection(key, null).getKeys(false).toArray()).length;
			for (int i = 0; i < j; i++) {
				Object str = arrayOfObject[i];
				section.add(String.valueOf(str));
			}
			return section;
		}
		return fallback;
	}

	public int getInteger(String key, int fallback) {
		if (this.fileConfig.contains(key)) {
			return this.fileConfig.getInt(key);
		}
		return fallback;
	}

	public String getString(String key, String fallback) {
		if (this.fileConfig.contains(key)) {
			return this.fileConfig.getString(key);
		}
		return fallback;
	}

	public boolean contains(String key) {
		return this.fileConfig.contains(key);
	}

	public boolean getBoolean(String key, boolean fallback) {
		if (this.fileConfig.contains(key)) {
			return this.fileConfig.getBoolean(key);
		}
		return fallback;
	}

	public ArrayList<String> getStringArray(String key, ArrayList<String> fallback) {
		if (this.fileConfig.contains(key)) {
			return (ArrayList<String>) this.fileConfig.getStringList(key);
		}
		return fallback;
	}

	public double getDouble(String key, double fallback) {
		if (this.fileConfig.contains(key)) {
			return this.fileConfig.getDouble(key);
		}
		return fallback;
	}

	public Object getObject(String key, Object fallback) {
		if (this.fileConfig.contains(key)) {
			return this.fileConfig.get(key);
		}
		return fallback;
	}

	public float getFloat(String key, float fallback) {
		if (this.fileConfig.contains(key)) {
			return (float) this.fileConfig.getDouble(key);
		}
		return fallback;
	}

	public Material getMaterial(String key, Material fallback) {
		if (this.fileConfig.contains(key)) {
			return this.fileConfig.getItemStack(key).getType();
		}
		return fallback;
	}
	
	public ItemStack getItemStack(String key, ItemStack fallback) {
		if (this.fileConfig.contains(key)) {
			return this.fileConfig.getItemStack(key);
		}
		return fallback;
	}
	
	@SuppressWarnings("unchecked")
	public ItemStack[] getItemStacks(String key, ItemStack[] fallback) {
		if (this.fileConfig.contains(key)) {
			return ((List<ItemStack>)this.fileConfig.get(key)).toArray(new ItemStack[0]);
		}
		return fallback;
	}
	
	public List<Map<?, ?>> getMapList(String key, List<Map<?, ?>> fallback) {
		if (this.fileConfig.contains(key)) {
			return this.fileConfig.getMapList(key);
		}
		return fallback;
	}
 
	public void set(String key, Object set) {
		this.fileConfig.set(key, set);
		if (this.saveOnSet) {
			save();
		}
	}
}
