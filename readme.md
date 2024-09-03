# ChunkLoader Plugin

## Overview
[中文]

The **ChunkLoader** plugin is designed for Minecraft servers to allow players to manually force-load or unload specific chunks. This can be useful for ensuring certain areas of your server remain active and loaded, even if no players are nearby.

## Features

- **Manual Chunk Loading/Unloading:** Players can load or unload chunks on demand.
- **Persistent Chunk Data:** Loaded chunks are saved in a configuration file and are reloaded when the server restarts.
- **Reset Functionality:** Easily reset all chunks to their normal loading mode.
- **Interactive Commands:** Utilize clickable commands for easy chunk management.
- **Player Join Message:** New players receive a welcome message with guidance on how to use the plugin.

## Installation

1. Download the latest release of the **ChunkLoader** plugin from the [GitHub repository](https://github.com/HongMJ1315/ChunkLoader).
2. Place the `ChunkLoader.jar` file in the `plugins` directory of your Minecraft server.
3. Restart the server to enable the plugin.

## Commands

- `/chunk`  
  Displays clickable options to either load or unload the current chunk.

- `/chunk load`  
  Forces the current chunk the player is standing in to be loaded.

- `/chunk load [x] [z]`  
  Forces the specified chunk at coordinates (x, z) to be loaded.

- `/chunk unload`  
  Unloads the current chunk the player is standing in.

- `/chunk unload [x] [z]`  
  Unloads the specified chunk at coordinates (x, z).

- `/chunk reset`  
  Resets all chunks to their normal loading state, removing all forced loading.

- `/chunk help`  
  Shows the help message with all commands and usage instructions.

## Usage

1. **Loading a Chunk:**
    - Use `/chunk load` to force-load the chunk you're currently in.
    - Alternatively, use `/chunk load [x] [z]` to load a specific chunk by its coordinates.

2. **Unloading a Chunk:**
    - Use `/chunk unload` to unload the chunk you're currently in.
    - Use `/chunk unload [x] [z]` to unload a specific chunk by its coordinates.

3. **Resetting All Chunks:**
    - Use `/chunk reset` to revert all chunks back to their normal loading behavior.

4. **Getting Help:**
    - Type `/chunk help` to display a help message with all commands and their explanations.

## Configuration

The plugin creates a `config.yml` file in the `plugins/ChunkLoader` directory. This file stores information about which chunks are force-loaded. You don't need to manually edit this file; all changes should be made via the in-game commands.

---

By Mr_JB  
[GitHub Repository](https://github.com/HongMJ1315/ChunkLoader)
