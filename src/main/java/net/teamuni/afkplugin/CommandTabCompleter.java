package net.teamuni.afkplugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandTabCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {
        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("afkplugin")) {
            List<String> tabCompleteList = new ArrayList<>();
            if (args.length == 1) {
                tabCompleteList.add("reload");
            }
            return tabCompleteList;
        }
        if (cmd.getName().equalsIgnoreCase("잠수포인트")) {
            if (player.isOp()) {
                List<String> tabCompleteList = new ArrayList<>();
                if (args.length == 1) {
                    tabCompleteList.add("확인");
                    tabCompleteList.add("설정");
                    tabCompleteList.add("지급");
                    tabCompleteList.add("차감");
                }
                return tabCompleteList;
            } else {
                List<String> tabCompleteList = new ArrayList<>();
                if (args.length == 1) {
                    tabCompleteList.add("확인");
                }
                return tabCompleteList;
            }
        }
        return null;
    }
}
