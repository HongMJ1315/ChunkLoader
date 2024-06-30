package org.mrjb.chunkloader.chunkloader;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoinListener implements Listener {

    private JavaPlugin plugin;

    public PlayerJoinListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
//        event.getPlayer().sendMessage("Welcome! Type /chunk help to see how to use the Chunk Loader Plugin.");
        TextComponent message = new TextComponent("Welcome! Type ");
        TextComponent command = new TextComponent("/chunk help");
        command.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        command.setClickEvent(new net.md_5.bungee.api.chat.ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/chunk help"));
        message.addExtra(command);
        message.addExtra(" to see how to use the Chunk Loader Plugin.");
        event.getPlayer().spigot().sendMessage(message);
    }
}
