package me.messageofdeath.lib.compat;

import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class DeathNMS_v1_11_R1 implements DeathNMS {

    @Override
    public void sendActionBar(Player player, String text) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(IChatBaseComponent.
                ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2));
    }

    @Override
    public void removeActionBar(Player player) {
        sendActionBar(player, "");
    }
}
