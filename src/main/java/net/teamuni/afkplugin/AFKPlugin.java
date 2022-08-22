package net.teamuni.afkplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;


public final class AFKPlugin extends JavaPlugin implements Listener {

    World world;
    double x;
    double y;
    double z;
    float yaw;
    float pitch;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();
        getCommand("잠수").setExecutor(this);
        getCommand("afk").setExecutor(this);
        getCommand("잠수지역설정").setExecutor(this);
        getCommand("afkplugin").setTabCompleter(new CommandTabCompleter());
        getAfkPoint();
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        String[] afkCommands = {"잠수", "afk"};

        if (Arrays.asList(afkCommands).contains(cmd.getName()) && player.hasPermission("afk.afk")) {
            try {
                if (!player.getLocation().getWorld().getName().equalsIgnoreCase(getConfig().getString("afkpoint.world"))) {
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "이동 중...");
                    player.teleport(new Location(world, x, y, z, yaw, pitch));
                } else {
                    player.sendMessage(ChatColor.RED + "이동할 수 없습니다.");
                }
            } catch (NullPointerException | IllegalArgumentException e) {
                e.printStackTrace();
                getLogger().info("잠수 지역 정보를 불러올 수 없습니다.");
            }
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("잠수지역설정") && player.hasPermission("afk.setpoint")) {
            getConfig().set("afkpoint.world", player.getLocation().getWorld().getName());
            getConfig().set("afkpoint.x", player.getLocation().getX());
            getConfig().set("afkpoint.y", player.getLocation().getY());
            getConfig().set("afkpoint.z", player.getLocation().getZ());
            getConfig().set("afkpoint.yaw", player.getLocation().getYaw());
            getConfig().set("afkpoint.pitch", player.getLocation().getPitch());
            saveConfig();
            reloadConfig();
            getAfkPoint();
            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "AFK point has been set!");
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("afkplugin") && player.hasPermission("afk.reload")) {
            if (args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
                saveConfig();
                getAfkPoint();
                player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "AFKplugin has been reloaded!");
            }
            return false;
        }
        return false;
    }

    public void getAfkPoint() {
        try {
            world = Bukkit.getWorld(getConfig().getString("afkpoint.world"));
            x = getConfig().getDouble("afkpoint.x");
            y = getConfig().getDouble("afkpoint.y");
            z = getConfig().getDouble("afkpoint.z");
            yaw = (float) getConfig().getDouble("afkpoint.yaw");
            pitch = (float) getConfig().getDouble("afkpoint.pitch");
        } catch (NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
            getLogger().info("잠수 지역 정보를 불러올 수 없습니다.");
        }
    }
}
