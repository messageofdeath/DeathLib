package me.messageofdeath.lib.logging;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Log {
	private Logger log = Bukkit.getLogger();
	private String pluginName;

	public Log(JavaPlugin plugin) {
		this.pluginName = plugin.getDescription().getName();
	}

	public void severe(String message) {
		this.log.severe("[" + this.pluginName + "] " + message);
	}

	public void warning(String message) {
		this.log.warning("[" + this.pluginName + "] " + message);
	}

	public void info(String message) {
		this.log.info("[" + this.pluginName + "] " + message);
	}

	public void config(String message) {
		this.log.config("[" + this.pluginName + "] " + message);
	}

	public void fine(String message) {
		this.log.fine("[" + this.pluginName + "] " + message);
	}

	public void finer(String message) {
		this.log.finer("[" + this.pluginName + "] " + message);
	}

	public void finest(String message) {
		this.log.finest("[" + this.pluginName + "] " + message);
	}
}
