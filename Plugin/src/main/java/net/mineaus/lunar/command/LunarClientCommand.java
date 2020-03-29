package net.mineaus.lunar.command;

import net.mineaus.lunar.LunarClientPlugin;
import net.mineaus.lunar.api.user.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LunarClientCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);

            if (target != null) {
                User targetData = LunarClientPlugin.getApi().getUserManager().getPlayerData(target);
                if (targetData.isLunarClient()) {
                    sender.sendMessage(LunarClientPlugin.getMessage("msg.lunarclient.player-true", target.getName()));
                    return true;
                } else {
                    sender.sendMessage(LunarClientPlugin.getMessage("msg.lunarclient.player-false", target.getName()));
                    return true;
                }
            } else {
                sender.sendMessage(LunarClientPlugin.getMessage("msg.lunarclient.player-offline", args[0]));
                return false;
            }
        } else {
            StringBuilder sb = new StringBuilder(LunarClientPlugin.getMessage("msg.lunarclient.list-header"));

            int amount = LunarClientPlugin.getApi().getUserManager().getPlayerDataMap().size();

            if (amount > 0) {
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    User data = LunarClientPlugin.getApi().getUserManager().getPlayerData(player);

                    if (data != null && data.isLunarClient()) {
                        sb.append(ChatColor.WHITE).append(data.getName()).append("\n");
                    }
                }
            } else {
                sb.append(LunarClientPlugin.getMessage("msg.lunarclient.list-no-one")).append("\n");
            }

            sb.append(LunarClientPlugin.getMessage("msg.lunarclient.list-footer"));
            sender.sendMessage(sb.toString());
            return true;
        }
    }
}