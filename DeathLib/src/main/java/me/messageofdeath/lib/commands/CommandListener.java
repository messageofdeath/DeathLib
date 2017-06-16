package me.messageofdeath.lib.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandListener implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
		CommandManagerBackend.executeMethod(sender, commandLabel, args);
		return false;
	}
}
