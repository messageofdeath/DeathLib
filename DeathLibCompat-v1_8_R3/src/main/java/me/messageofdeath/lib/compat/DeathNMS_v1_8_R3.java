package me.messageofdeath.lib.compat;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class DeathNMS_v1_8_R3 implements DeathNMS {

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
