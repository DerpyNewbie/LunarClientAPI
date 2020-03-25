package net.mineaus.lunar.command;

import net.mineaus.lunar.LunarClientPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EmoteByIdCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command doesn't support execution from console.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            player.sendMessage(LunarClientPlugin.getMessage("msg.emote.not-specified"));
            return false;
        }

        if (!isInteger(args[0])) {
            player.sendMessage(LunarClientPlugin.getMessage("msg.emote.not-valid", args[0]));
            return false;
        }

        int emoteId = Integer.parseInt(args[0]);

        try {
            LunarClientPlugin.getApi().performEmote(player, emoteId, true);
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "Error occurred while performing emote.");
            return false;
        }

        return true;
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
