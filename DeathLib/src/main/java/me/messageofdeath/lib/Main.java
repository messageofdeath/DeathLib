package me.messageofdeath.lib;

import org.bstats.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class Main extends JavaPlugin {

    public static final String PREFIX = "&8[&2DeathLib&8] &6";
    private LanguageConfiguration langConfig;
    private Metrics metrics;

    @Override
    public void onEnable() {
        log("Loading DeathLib...", true);
        langConfig = new LanguageConfiguration(this);
        metrics = new Metrics(this);
        langConfig.initConfiguration();
        langConfig.loadConfiguration();
        log("DeathLib has loaded!", true);
    }

    public static void log(String log, boolean prefix) {
        Bukkit.getServer().getConsoleSender().sendMessage(Utilities.getColorized((prefix ? PREFIX : "") + log));
    }

    public static void logError(String topic, String classx, String method, String error) {
        String space = "                                                              ";
        String text = "&cTopic:";
        topic = "&c" + topic;
        log("&4------------------------&b{&2DeathLib &cError&b}&4------------------------", false);
        log("", false);
        log(getCentered(space, text), false);
        log(getCentered(space, topic), false);
        log("", false);
        for (String s : getLines(error, space)) {
            log("&b" + getCentered(space, s), false);
        }
        log("", false);
        String cl = "&8Class: &c" + classx + "   &8Method: &c" + method;
        for (String s : getLines(cl, space)) {
            log("&c" + getCentered(space, s), false);
        }
        log("", false);
        log("&4------------------------&b{&2DeathLib &cError&b}&4------------------------", false);
    }

    private static String getCentered(String space, String text) {
        return space.substring((space.length() + text.length()) / 2, space.length()) + text;
    }

    private static ArrayList<String> getLines(String parse, String space) {
        ArrayList<String> lines = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        String[] split = parse.split(" ");
        int length = split.length;
        for (int i = 0; i < length; i++) {
            if ((s.length() + split[i].length() + ((s.length() > 0) ? 1 : 0)) <= space.length()) {
                if(s.length() > 0) {
                    s.append(" ");
                }
                s.append(split[i]);
            } else {
                lines.add(s.toString());
                s = new StringBuilder();
                String text = split[i];
                if(text.length() > space.length()) {
                    int lastIndex = 0;
                    int amount = Math.floorDiv(text.length(), space.length());
                    for(int x = 0; x < amount; x++) {
                        lines.add(text.substring(lastIndex, lastIndex + space.length() - 1) + "-");
                        lastIndex += space.length() - 1;
                    }
                    if(lastIndex < text.length()) {
                        lines.add(text.substring(lastIndex, text.length()));
                    }
                }else{
                    if(s.length() > 0) {
                        s.append(" ");
                    }
                    s.append(split[i]);
                }
            }
            if (i + 1 == length) {
                lines.add(s.toString());
                s = new StringBuilder();
            }
        }
        return lines;
    }
}
