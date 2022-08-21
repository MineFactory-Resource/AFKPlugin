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


public final class AFKPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();
        getCommand("잠수").setExecutor(this);
        getCommand("잠수지역설정").setExecutor(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("잠수") && player.hasPermission("afk.afk")) {
            if (!player.getLocation().getWorld().getName().equalsIgnoreCase(getConfig().getString("afkpoint.world"))) {
                try {
                    World world = Bukkit.getWorld(getConfig().getString("afkpoint.world"));
                    double x = getConfig().getDouble("afkpoint.x");
                    double y = getConfig().getDouble("afkpoint.y");
                    double z = getConfig().getDouble("afkpoint.z");
                    float yaw = (float) getConfig().getDouble("afkpoint.yaw");
                    float pitch = (float) getConfig().getDouble("afkpoint.pitch");
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "이동 중...");
                    player.teleport(new Location(world, x, y, z, yaw, pitch));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    getLogger().info("/잠수지역설정 명령어를 통해 잠수 지역을 먼저 설정해 주세요.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "이동할 수 없습니다.");
            }
        }
        if (cmd.getName().equalsIgnoreCase("잠수지역설정") && player.hasPermission("afk.setpoint")) {
            getConfig().set("afkpoint.world", player.getLocation().getWorld().getName());
            getConfig().set("afkpoint.x", player.getLocation().getX());
            getConfig().set("afkpoint.y", player.getLocation().getY());
            getConfig().set("afkpoint.z", player.getLocation().getZ());
            getConfig().set("afkpoint.yaw", player.getLocation().getYaw());
            getConfig().set("afkpoint.pitch", player.getLocation().getPitch());
            saveConfig();
            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "AFK point has been set!");
        }
        return false;
    }
}
