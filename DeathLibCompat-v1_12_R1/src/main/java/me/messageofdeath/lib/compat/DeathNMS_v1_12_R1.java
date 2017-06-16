package me.messageofdeath.lib.compat;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.entity.Player;

public class DeathNMS_v1_12_R1 implements DeathNMS {

    @Override
    public void sendActionBar(Player player, String text) {
        ((CraftServer) Bukkit.getServer()).getHandle().a(player.getUniqueId()).playerConnection.sendPacket(new PacketPlayOutChat(IChatBaseComponent.
                ChatSerializer.a("{\"text\":\"" + text + "\"}"), ChatMessageType.GAME_INFO));
    }

    @Override
    public void removeActionBar(Player player) {
        sendActionBar(player, "");
    }
}