package me.messageofdeath.lib.pagelists;

import org.bukkit.command.CommandSender;

public class Option {

	private final String text;
	private final String perm;

	public Option(String text, String perm) {
		this.text = text;
		this.perm = perm;
	}

	public String getText() {
		return this.text;
	}

	public boolean hasPermission(CommandSender sender) {
		return (sender.hasPermission(this.perm)) || (this.perm.equalsIgnoreCase("noPerm"))
				|| (this.perm.equalsIgnoreCase("")) || (this.perm.equalsIgnoreCase("noPermission"));
	}
}
