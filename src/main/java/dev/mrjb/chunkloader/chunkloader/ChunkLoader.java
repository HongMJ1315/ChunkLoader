package dev.mrjb.chunkloader.chunkloader;

import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ChunkLoader extends JavaPlugin {

    private Set<String> loadedChunks = new HashSet<>();
    private File configFile;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        getLogger().info("ChunkLoaderPlugin has been enabled!");
        createConfigFile();
        loadChunksFromConfig();
        this.getCommand("chunk").setExecutor(new CommandEvent(this));
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this); // 註冊事件監聽器
    }

    @Override
    public void onDisable() {
        getLogger().info("ChunkLoaderPlugin has been disabled.");
        saveChunksToConfig();
    }

    public Set<String> getLoadedChunks() {
        return loadedChunks;
    }

    public void addLoadedChunk(String chunkKey) {
        loadedChunks.add(chunkKey);
        saveChunksToConfig();
    }

    public void removeLoadedChunk(String chunkKey) {
        loadedChunks.remove(chunkKey);
        saveChunksToConfig();
    }

    private void createConfigFile() {
        configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    private void loadChunksFromConfig() {
        loadedChunks = new HashSet<>(config.getStringList("loadedChunks"));
        for (String chunkKey : loadedChunks) {
            String[] parts = chunkKey.split(",");
            int x = Integer.parseInt(parts[0]);
            int z = Integer.parseInt(parts[1]);
            String worldName = parts[2];
            Chunk chunk = getServer().getWorld(worldName).getChunkAt(x, z);
            chunk.setForceLoaded(true);
        }
    }

    private void saveChunksToConfig() {
        config.set("loadedChunks", new ArrayList<>(loadedChunks));
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
