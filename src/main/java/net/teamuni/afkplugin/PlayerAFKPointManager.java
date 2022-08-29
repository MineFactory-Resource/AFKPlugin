package net.teamuni.afkplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PlayerAFKPointManager {
    private static final AFKPlugin main = AFKPlugin.getPlugin(AFKPlugin.class);
    private static File file;
    private static FileConfiguration commandsFile;

    public static void createAfkPointDataYml() {
        file = new File(main.getDataFolder(), "afkpointdata.yml");

        if (!file.exists()) {
            main.saveResource("afkpointdata.yml", false);
        }
        commandsFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return commandsFile;
    }

    public static void save() {
        try {
            commandsFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        commandsFile = YamlConfiguration.loadConfiguration(file);
    }
}
