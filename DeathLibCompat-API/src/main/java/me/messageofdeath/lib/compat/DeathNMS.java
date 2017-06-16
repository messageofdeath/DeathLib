package me.messageofdeath.lib.compat;

import me.messageofdeath.lib.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public interface DeathNMS {

    void sendActionBar(Player player , String text);

    void removeActionBar(Player player);

    static DeathNMS getNMS() {
        String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        try {
            return (DeathNMS)Class.forName("me.messageofdeath.lib.compat.DeathNMS_" + version).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
           Main.logError("NMS Hook", "DeathNMS", "getNMS()", "The server version '"+version+"' is not compatible with DeathLib! " +
                    "This may affect plug-ins that use this a dependency!");
        }
        return null;
    }
}
