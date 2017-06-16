package me.messageofdeath.lib.commands;

import me.messageofdeath.lib.LanguageSettings;
import me.messageofdeath.lib.Main;
import me.messageofdeath.lib.Utilities;
import me.messageofdeath.lib.logging.Messenger;
import me.messageofdeath.lib.pagelists.Option;
import me.messageofdeath.lib.pagelists.PageList;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class CommandManagerBackend {
	
	private static Messenger messenger = null;
	private static HashMap<String, Method> commands = null;
	private static HashMap<Method, Object> instances = null;
	private static HashMap<String, PageList> help = null;
	private static HashMap<String, JavaPlugin> plugins = null;
	private static HashMap<JavaPlugin, String> prefixes = null;
	private static CommandListener listener = null;

	public static void registerPrefix(JavaPlugin plugin, String prefix) {
		if(prefixes == null) {
			prefixes = new HashMap<>();
		}
		if(prefixes.containsKey(plugin)) {
			prefixes.remove(plugin);
		}
		prefixes.put(plugin, prefix);
	}

	public static void register(JavaPlugin plugin, Class<?> classx) {
		if(commands == null) {
			commands = new HashMap<>();
		}
		if(plugins == null) {
			plugins = new HashMap<>();
		}
		if(messenger == null) {
			messenger = new Messenger(Main.PREFIX);
		}
		if(instances == null) {
			instances = new HashMap<>();
		}
		if(help == null) {
			help = new HashMap<>();
		}
		if(listener == null) {
			listener = new CommandListener();
		}
		ArrayList<String> commandsExisting = new ArrayList<>();
		Method[] methods = classx.getMethods();
		ArrayUtils.reverse(methods);
		for(Method method : methods) {
			if(method.isAnnotationPresent(Command.class)) {
				if(CommandManagerBackend.isCompatible(method)) {
					Command cmd = method.getAnnotation(Command.class);
					for(String command : cmd.aliases()) {
						for(String modifier : cmd.modifiers()) {
							if(!modifier.isEmpty()) {
								commands.put(command.toLowerCase() + " " + modifier.toLowerCase(), method);
							}else{
								commands.put(command.toLowerCase(), method);
							}
						}
						if(!commandsExisting.contains(command.toLowerCase())) {
							commandsExisting.add(command.toLowerCase());
							plugin.getCommand(command.toLowerCase()).setExecutor(listener);
							plugins.put(command.toLowerCase(), plugin);
						}
					}
					try {
						instances.put(method, classx.newInstance());
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}else{
					Main.logError("Incompatible Method", "CommandManagerBackend", "register(JavaPlugin, Class<?>)", "Developer setup the "
							+ "commands wrong for method '"+method.getName()+"("+getParameters(method)+")' Needs to have parameters (IssuedCommand, CommandSender)");
				}
			}
		}
		for(String cmd : commandsExisting) {
			setupHelp(cmd);
		}
	}

	public static void executeMethod(CommandSender cmdSender, String cmdLabel, String[] args) {
		String command;
		if(args.length > 0) {
			command = CommandManagerBackend.getCommand(cmdLabel.toLowerCase() + " " + arrayToString(args, -1));
		}else{
			command = CommandManagerBackend.getCommand(cmdLabel.toLowerCase());
		}
		if(commands.containsKey(command)) {
			Method method = commands.get(command);
			Command cmd = method.getAnnotation(Command.class);
			IssuedCommand sender = new IssuedCommand(cmdSender, cmdLabel.toLowerCase(), args, cmd.flags());
			String usage = "  &8- &c/" + cmd.aliases()[0] + " " + cmd.usage();
			if(plugins.containsKey(cmdLabel.toLowerCase())) {
				messenger.setPrefix(prefixes.get(plugins.get(cmdLabel.toLowerCase())));
			}else{
				messenger.setPrefix(Main.PREFIX);
			}
			if(!(cmd.permission().equalsIgnoreCase("noPerm") || sender.getSender().hasPermission(cmd.permission()) || sender.getSender().isOp())) {
				messenger.sendNoPermissionError(cmdSender);
				return;
			}
			if(!(cmdSender instanceof Player) && cmd.ingame()) {
				messenger.sendError(sender.getSender(), LanguageSettings.Commands_MustBeIngame.getSetting());
				return;
			}
			if(!sender.setupArgs()) {
				messenger.sendError(sender.getSender(), LanguageSettings.Commands_InvalidModifier.getSetting());
				messenger.sendMessage(cmdSender, usage, false);
				return;
			}
			if(sender.getLength() < cmd.min()) {
				messenger.sendError(sender.getSender(), LanguageSettings.Commands_NotEnoughArgs.getSetting());
				messenger.sendMessage(cmdSender, usage, false);
				return;
			}
			if(sender.getLength() > cmd.max() && cmd.max() != -1) {
				messenger.sendError(sender.getSender(), LanguageSettings.Commands_TooManyArgs.getSetting());
				messenger.sendMessage(cmdSender, usage, false);
				return;
			}
			try {
				method.invoke(instances.get(method), sender, cmdSender);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}else if(args.length > 1 && args[0].equalsIgnoreCase("help")){
			executeHelp(cmdSender, cmdLabel, args);
		}else{
			help(cmdSender, cmdLabel, 0);
		}
	}
	
	private static String getCommand(String command) {
		String[] args = command.split(" ");
		for(int i = args.length; i > -1; i--) {
			String cmd = arrayToString(args, i);
			if(commands.containsKey(cmd.toLowerCase())) {
				return cmd;
			}
		}
		return command;
	}
	
	private static String arrayToString(String[] args, int maxArgs) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < args.length && (i < maxArgs || maxArgs == -1); i++) {
			if(builder.length() != 0) {
				builder.append(" ");
			}
			builder.append(args[i]);
		}
		return builder.toString();
	}
	
	private static boolean isCompatible(Method method) {
		return method.getParameterTypes().length > 1 && (method.getParameterTypes()[0] == IssuedCommand.class && method.getParameterTypes()[1] == CommandSender.class);
	}
	
	private static String getParameters(Method method) {
		StringBuilder x = new StringBuilder();
		for(Class<?> classx : method.getParameterTypes()) {
			if(x.length() > 0) {
				x.append(", ");
			}
			x.append(classx.getSimpleName());
		}
		return x.toString();
	}
	
	public static void setupHelp(String command) {
		PageList list = new PageList(3);
		list.addOption(new Option("/"+command+" &b&l-&e Displays help&a.", "ur.cmd."+command+".help"));
		list.addOption(new Option("/"+command+" &ahelp [page] &b&l-&e Goes to a help page&a.", "ur.cmd."+command+".help"));
		for(String key : commands.keySet()) {
			if(key.toLowerCase().startsWith(command.toLowerCase())) {
				Command cmd = commands.get(key).getAnnotation(Command.class);
				list.addOption(new Option("/"+command+" " + cmd.usage() + " &b&l-&e " + cmd.desc() + "&a.", cmd.permission()));
			}
		}
		int options = list.getAmountOfOptions();
		int perPage = 5;
		for(int i = 6; i > 2; i--) {
			if(options % i == 0) {
				perPage = i;
				break;
			}
		}
		list.setAmountPerPage(perPage);
		if(help.containsKey(command.toLowerCase())) {
			help.remove(command.toLowerCase());
		}
		help.put(command.toLowerCase(), list);
	}
	
	private static void executeHelp(CommandSender sender, String command, String[] args) {
		if(Utilities.isInteger(args[1])) {
			help(sender, command, Integer.parseInt(args[1]));
		}else{
			help(sender, command, 0);
		}
	}

	private static void help(CommandSender sender, String command, int page) {
		if (sender.hasPermission("ur.cmd."+command+".help")) {
			if(help.containsKey(command.toLowerCase())) {
				PageList list = help.get(command.toLowerCase());
				page = list.checkPage(sender, page);
				if(plugins.containsKey(command.toLowerCase())) {
					messenger.setPrefix(prefixes.get(plugins.get(command.toLowerCase())));
				}else{
					messenger.setPrefix(Main.PREFIX);
				}
				messenger.sendMessage(sender, "Available Commands (" + (page + 1) + "/" + list.getTotalPages(sender) + "):");
				messenger.sendMessage(sender, "<> are required | [] are not required", false);
				String dud = "    &8- &2";
				for (String m : list.getOptions(sender, page)) {
					int index = m.indexOf(' ');
					if (index < m.length()) {
						String prefix = m.substring(0, index) + "&a";
						messenger.sendMessage(sender, dud + prefix + m.substring(index), false);
					} else {
						messenger.sendMessage(sender, dud + m, false);
					}
				}	
			}
		}else{
			messenger.sendError(sender, LanguageSettings.Commands_NoPermission.getSetting());
		}
	}
}
