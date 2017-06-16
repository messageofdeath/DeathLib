package me.messageofdeath.lib.commands;

import me.messageofdeath.lib.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class IssuedCommand {
	
	private String[] args;
	private final String commandLabel;
	private final String validFlags;
	private final CommandSender sender;
	private ArrayList<String> flags;
	private HashMap<String, String> valueFlags;

	public IssuedCommand(CommandSender sender, String commandLabel, String[] args, String validFlags) {
		this.args = args;
		this.commandLabel = commandLabel;
		this.validFlags = validFlags;
		this.sender = sender;
	}

	public boolean isPlayer() {
		return this.sender instanceof Player;
	}

	public boolean isConsole() {
		return this.sender instanceof ConsoleCommandSender;
	}

	public CommandSender getSender() {
		return this.sender;
	}

	public Player getPlayer() {
		return (Player) this.sender;
	}

	public ConsoleCommandSender getConsole() {
		return (ConsoleCommandSender) this.sender;
	}

	public String[] getArgs() {
		return this.args;
	}

	public int getLength() {
		return this.args.length;
	}

	public boolean argExist(int arg) {
		return arg < this.args.length;
	}

	public String getString(int arg) {
		return this.args[arg];
	}

	public boolean isOnline(int arg) {
		return this.getOfflinePlayer(arg).isOnline();
	}

	public Player getPlayer(int arg) {
		return Bukkit.getPlayer(this.args[arg]);
	}

	@SuppressWarnings("deprecation")
	public OfflinePlayer getOfflinePlayer(int arg) {
		return Bukkit.getOfflinePlayer(this.args[arg]);
	}

	public int getInteger(int arg) {
		return Integer.parseInt(this.args[arg]);
	}

	public double getDouble(int arg) {
		return Double.parseDouble(this.args[arg]);
	}

	public boolean getBoolean(int arg) {
		return this.args[arg].equalsIgnoreCase("true");
	}

	public boolean isAlphanumeric(int arg) {
		return Utilities.isAlphanumeric(this.args[arg]);
	}

	public boolean isAlpha(int arg) {
		return Utilities.isAlpha(this.args[arg]);
	}

	public boolean isNumeric(int arg) {
		return Utilities.isNumeric(this.args[arg]);
	}
	
	public boolean isInteger(int arg) {
		return Utilities.isInteger(this.args[arg]);
	}
	
	public boolean isFloat(int arg) {
		return Utilities.isFloat(this.args[arg]);
	}
	
	public boolean isDouble(int arg) {
		return Utilities.isDouble(this.args[arg]);
	}

	public boolean isBoolean(int arg) {
		return (this.args[arg].equalsIgnoreCase("true")) || (this.args[arg].equalsIgnoreCase("false"));
	}

	public boolean isCommand(String name) {
		return this.commandLabel.equalsIgnoreCase(name);
	}

	public String getCommand() {
		return this.commandLabel;
	}
	
	//**************** Flags ****************
	
	public boolean hasFlag(char c) {
		return this.flags.contains(c + "");
	}
	
	public boolean hasValueFlag(String c) {
		return this.valueFlags.containsKey(c);
	}
	
	public boolean isFlagNumeric(String c) {
		return Utilities.isNumeric(this.valueFlags.get(c));
	}
	
	public boolean isFlagInteger(String c) {
		return Utilities.isInteger(this.valueFlags.get(c));
	}
	
	public boolean isFlagDouble(String c) {
		return Utilities.isDouble(this.valueFlags.get(c));
	}
	
	public boolean isFlagFloat(String c) {
		return Utilities.isFloat(this.valueFlags.get(c));
	}
	
	public String getFlag(String c) {
		return this.valueFlags.get(c);
	}
	
	public Integer getFlagInteger(String c) {
		return Integer.parseInt(this.valueFlags.get(c));
	}
	
	public Double getFlagDouble(String c) {
		return Double.parseDouble(this.valueFlags.get(c));
	}
	
	public Float getFlagFloat(String c) {
		return Float.parseFloat(this.valueFlags.get(c));
	}
	
	public boolean setupArgs() {
		if(this.flags == null) {
			this.flags = new ArrayList<>();
		}
		if(this.valueFlags == null) {
			this.valueFlags = new HashMap<>();
		}
		int remove = 0;
		for(int i = 0; i < this.args.length; i++) {
			//Value Flags
			if(this.args[i].startsWith("--") && (i+1) < this.args.length) {
				String valueFlag = this.args[i].toLowerCase().replace("--", "");
				this.valueFlags.put(valueFlag, this.args[i+1]);
				this.args[i] = "";
				this.args[i+1] = "";
				remove += 2;
				continue;
			}else if(this.args[i].startsWith("--")) {
				return false;
			}
			//Flags
			if(this.args[i].startsWith("-") && this.args[i].length() == 2) {
				if(!this.validFlags.contains(this.args[i].toLowerCase().charAt(1) + "")) {
					return false;
				}
				this.flags.add("" + this.args[i].charAt(1));
				this.args[i] = "";
				remove += 1;
			}else if(this.args[i].startsWith("-")) {
				return false;
			}
		}
		String[] newArgs = new String[args.length-remove];
		int i = 0;
		for(String arg : args) {
			if(arg != null && !arg.isEmpty()) {
				newArgs[i] = arg;
				i++;
			}
		}
		this.args = newArgs;
		return true;
	}
}
