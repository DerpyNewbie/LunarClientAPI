package net.mineaus.lunar.command;

import net.mineaus.lunar.LunarClientPlugin;
import net.mineaus.lunar.api.type.Emote;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EmoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command doesn't support execution from console.");
            return true;
        }

        Player player = (Player) sender;
        Emote emote;

        if (!LunarClientPlugin.getApi().isAuthenticated(player)) {
            player.sendMessage(ChatColor.RED + "You must be on Lunar Client to perform emotes.");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "Please specify an emote.");
            return false;
        }

        // Get Emote type by argument

        if (isInteger(args[0])) {
            int emoteId = Integer.parseInt(args[0]);

            if (emoteId == -1) {
                try {
                    LunarClientPlugin.getApi().performEmote(player, -1, true);
                    return true;
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Error occurred while halting emote.");
                    return false;
                }
            }

            emote = Emote.getById(emoteId);
        } else {
            if (args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("cancel")) {
                try {
                    LunarClientPlugin.getApi().performEmote(player, -1, true);
                    return true;
                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "Error occurred while halting emote.");
                    return false;
                }
            }

            emote = Emote.getByName(args[0]);
        }

        // Perform emote

        if (emote == null) {
            player.sendMessage(ChatColor.RED + "That is not valid emote!");
            for (Emote emotes : Emote.values()) {
                player.sendMessage(ChatColor.RED + " - " + emotes.name());
            }
            return false;
        }

        if (!player.hasPermission("lunar.command.emote." + emote.name())) {
            player.sendMessage(ChatColor.RED + "You do not have enough permission to perform emote " + emote.name() + ".");
            return true;
        }

        try {
            LunarClientPlugin.getApi().performEmote(player, emote.getEmoteId(), true);
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
