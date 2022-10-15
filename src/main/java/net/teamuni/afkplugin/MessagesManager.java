package net.teamuni.afkplugin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessagesManager {
    private static final AFKPlugin main = AFKPlugin.getPlugin(AFKPlugin.class);
    private static File file;
    private static FileConfiguration messagesFile;

    public static void createMessagesYml() {
        file = new File(main.getDataFolder(), "messages.yml");

        if (!file.exists()) {
            main.saveResource("messages.yml", false);
        }
        messagesFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return messagesFile;
    }

    public static void save() {
        try {
            messagesFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload() {
        messagesFile = YamlConfiguration.loadConfiguration(file);
    }
}
