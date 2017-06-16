package me.messageofdeath.lib.logging;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.bukkit.plugin.java.JavaPlugin;

public class ExceptionPrinter {

	public void printFriendlyStackTrace(JavaPlugin plugin, Throwable throwable) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		Log log = new Log(plugin);
		log.severe("");
		log.severe("Internal error!");
		log.severe(String.format("If this bug hasn't been reported yet, please report it at %s as soon as possible.",
				new Object[] { "" }));
		log.severe("          ======= SNIP HERE =======");

		throwable.printStackTrace(printWriter);
		
		String[] x = stringWriter.toString().replace("\r", "").split("\n");
		for(String s : x) {
			log.severe(s);
		}
		printWriter.close();
		try {
			stringWriter.close();
		} catch (IOException ignored) {
		}
		log.severe("          ===== END SNIP HERE =====");
		System.out.println();
	}
}
