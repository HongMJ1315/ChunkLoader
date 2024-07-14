package dev.mrjb.chunkloader.chunkloader;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;

public class CommandEvent implements CommandExecutor {

    private ChunkLoader plugin;

    public CommandEvent(ChunkLoader plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("chunk")) {
            if (args.length == 0) {
                sendClickableMessage((Player) sender);
                return true;
            }
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("help")) {
                    sendHelpMessage(sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("reset")) {
                    resetChunks(sender);
                    return true;
                } else if (args[0].equalsIgnoreCase("load") || args[0].equalsIgnoreCase("unload")) {
                    if (args.length == 3) {
                        int x = Integer.parseInt(args[1]);
                        int z = Integer.parseInt(args[2]);
                        String worldName = (sender instanceof Player) ? ((Player) sender).getWorld().getName() : "world";
                        handleChunkLoadUnload(sender, args[0], x, z, worldName);
                    } else if (sender instanceof Player) {
                        Player player = (Player) sender;
                        Chunk chunk = player.getLocation().getChunk();
                        handleChunkLoadUnload(sender, args[0], chunk.getX(), chunk.getZ(), player.getWorld().getName());
                    } else {
                        sender.sendMessage("Usage: /chunk " + args[0] + " [x] [z]");
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void sendClickableMessage(Player player) {
        TextComponent loadMessage = new TextComponent("[load]");
        loadMessage.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        loadMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/chunk load"));
        loadMessage.setBold(true);

        TextComponent unloadMessage = new TextComponent("[unload]");
        unloadMessage.setColor(net.md_5.bungee.api.ChatColor.RED);
        unloadMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/chunk unload"));
        unloadMessage.setBold(true);

        TextComponent space = new TextComponent("                ");

        TextComponent message = new TextComponent();
        message.addExtra(loadMessage);
        message.addExtra(space);
        message.addExtra(unloadMessage);

        player.spigot().sendMessage(message);
    }

    private void sendHelpMessage(CommandSender sender) {
        TextComponent githubLink = new TextComponent("[Github]");
        githubLink.setColor(net.md_5.bungee.api.ChatColor.BLUE);
        githubLink.setUnderlined(true);
        githubLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/HongMJ1315/ChunkLoader"));

        TextComponent message = new TextComponent("By Mr_JB ");
        message.addExtra(githubLink);

        if (sender instanceof Player) {
            sender.sendMessage("Chunk Loader Plugin Commands:");
            sender.sendMessage("/chunk - Show clickable options to load or unload the current chunk.");
            sender.sendMessage("/chunk load - Load the chunk you are currently in.");
            sender.sendMessage("/chunk load [x] [z] - Load the specified chunk.");
            sender.sendMessage("/chunk unload - Unload the chunk you are currently in.");
            sender.sendMessage("/chunk unload [x] [z] - Unload the specified chunk.");
            sender.sendMessage("/chunk reset - Reset all chunks to normal loading mode.");
            sender.sendMessage("/chunk help - Show this help message.");
            ((Player) sender).spigot().sendMessage(message);
        } else {
            sender.sendMessage("Chunk Loader Plugin Commands:");
            sender.sendMessage("/chunk load [x] [z] - Load the specified chunk.");
            sender.sendMessage("/chunk unload [x] [z] - Unload the specified chunk.");
            sender.sendMessage("/chunk reset - Reset all chunks to normal loading mode.");
            sender.sendMessage("/chunk help - Show this help message.");
            sender.sendMessage("By Mr_JB");
            sender.spigot().sendMessage(message);
        }
    }

    private void resetChunks(CommandSender sender) {
        for (String chunkKey : plugin.getLoadedChunks()) {
            String[] parts = chunkKey.split(",");
            int x = Integer.parseInt(parts[0]);
            int z = Integer.parseInt(parts[1]);
            String worldName = parts[2];
            Chunk chunk = Bukkit.getWorld(worldName).getChunkAt(x, z);
            chunk.setForceLoaded(false);
        }
        plugin.getLoadedChunks().clear();
        plugin.saveConfig();
        sender.sendMessage("All chunks have been reset to normal loading mode.");
    }

    private void handleChunkLoadUnload(CommandSender sender, String action, int x, int z, String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            sender.sendMessage("World not found: " + worldName);
            return;
        }
        Chunk chunk = world.getChunkAt(x, z);
        String chunkKey = x + "," + z + "," + worldName;
        if (action.equalsIgnoreCase("load")) {
            chunk.setForceLoaded(true);
            plugin.addLoadedChunk(chunkKey);
            sender.sendMessage("Chunk at " + x + ", " + z + " in world " + worldName + " is now force loaded.");
            getLogger().info("Chunk at " + x + ", " + z + " in world " + worldName + " is now force loaded.");
        } else if (action.equalsIgnoreCase("unload")) {
            chunk.setForceLoaded(false);
            plugin.removeLoadedChunk(chunkKey);
            sender.sendMessage("Chunk at " + x + ", " + z + " in world " + worldName + " is now unloaded.");
            getLogger().info("Chunk at " + x + ", " + z + " in world " + worldName + " is now unloaded.");
        }
    }
}
