package me.messageofdeath.lib.logging;

import me.messageofdeath.lib.LanguageSettings;
import me.messageofdeath.lib.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Messenger {
	
	public String prefix;

	public Messenger(String prefix) {
		this.prefix = prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void sendMessage(CommandSender sender, String message, boolean prefixed) {
		if (sender == null) {
			throw new NullPointerException("I tried to send a message to a null CommandSender. Oops.");
		}
		sender.sendMessage(prefixed ? Utilities.getColorized(this.prefix + message) : Utilities.getColorized(message));
	}

	public void sendMessage(CommandSender sender, String message) {
		sendMessage(sender, message, true);
	}

	public void sendMessageToConsole(String message, boolean prefixed) {
		sendMessage(Bukkit.getConsoleSender(), message, prefixed);
	}

	public void sendMessageToConsole(String message) {
		sendMessage(Bukkit.getConsoleSender(), message, true);
	}

	public void sendErrorToConsole(String message) {
		sendError(Bukkit.getConsoleSender(), message, false);
	}

	public void sendError(CommandSender sender, String message, boolean prefixed) {
		sendMessage(sender, LanguageSettings.General_ErrorTag.getSetting() + message, prefixed);
	}

	public void sendError(CommandSender sender, String message) {
		sendMessage(sender, LanguageSettings.General_ErrorTag.getSetting() + message, false);
	}

	public void sendNoPermissionError(CommandSender sender) {
		sendError(sender, LanguageSettings.Commands_NoPermission.getSetting());
	}

	public void broadcastMessage(String message, boolean prefixed) {
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			this.sendMessage(player, message, prefixed);
		}
	}

	public void broadcastMessage(String message) {
		broadcastMessage(message, true);
	}
}
