package net.teamuni.afkplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;


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
        PlayerAFKPointManager.createCommandsYml();
        getCommand("잠수").setExecutor(this);
        getCommand("afk").setExecutor(this);
        getCommand("잠수지역설정").setExecutor(this);
        getCommand("afkplugin").setTabCompleter(new CommandTabCompleter());
        getCommand("잠수포인트").setTabCompleter(new CommandTabCompleter());
        getAfkPoint();
    }

    @Override
    public void onDisable() {
        saveConfig();
        PlayerAFKPointManager.save();
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
            getAfkPoint();
            player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "AFK point has been set!");
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("afkplugin") && player.hasPermission("afk.reload")) {
            if (args[0].equalsIgnoreCase("reload")) {
                saveConfig();
                getAfkPoint();
                player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "AFKplugin has been reloaded!");
            }
            return false;
        }
        if (cmd.getName().equalsIgnoreCase("잠수포인트") && player.hasPermission("afk.afk")) {
            if (args[0].equalsIgnoreCase("확인")) {
                player.sendMessage("");
                player.sendMessage(ChatColor.AQUA + "[잠수] " + ChatColor.WHITE + "현재 " + ChatColor.YELLOW + player.getName() + ChatColor.WHITE + "님의 잠수포인트는 " + ChatColor.GOLD +
                        PlayerAFKPointManager.get().getLong("player.point." + player.getName()) + ChatColor.WHITE + "포인트입니다.");
                player.sendMessage("");
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

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Set<String> playerSet = PlayerAFKPointManager.get().getConfigurationSection("player.point").getKeys(false);

        if (!playerSet.contains(player.getName())) {
            playerSet.add(player.getName());
            PlayerAFKPointManager.get().set("player.point", playerSet);
            getLogger().info(player.getName() + "님의 잠수포인트 정보를 생성하였습니다.");
        }
    }

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        long delay = getConfig().getLong("delay");
        long period = getConfig().getLong("period");

        try {
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        if (player.getLocation().getWorld().getName().equalsIgnoreCase(getConfig().getString("afkpoint.world"))) {
                            long updatedPlayerAfkPoint = PlayerAFKPointManager.get().getLong("player.point." + player.getName()) + getConfig().getLong("value");
                            PlayerAFKPointManager.get().set("player.point." + player.getName(), updatedPlayerAfkPoint);
                            player.sendMessage("");
                            player.sendMessage(ChatColor.AQUA + "[잠수] " + ChatColor.GOLD + getConfig().getLong("value") + ChatColor.WHITE + "만큼의 잠수포인트가 지급되었습니다.");
                            player.sendMessage("");
                            cancel();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        getLogger().info("잠수포인트 정보를 불러오는데 문제가 발생하였습니다.");
                    }
                }
            };
            runnable.runTaskTimer(this, delay, period);
        } catch (NullPointerException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
