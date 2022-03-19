package com.chillaxmc.keepinventorypve.commands;

import com.chillaxmc.keepinventorypve.utils.Cooldown;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class keepInvToggle implements CommandExecutor {

    LuckPerms luckAPI = null;
    private String DISABLE_KEEPINV_PERMISSION = "keepInvOptions.disableForPlayer";
    private static final Cooldown COOLDOWN = new Cooldown(86400);


    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckAPI = provider.getProvider();
        }

        Node node = Node.builder(DISABLE_KEEPINV_PERMISSION).build();

        if(sender instanceof Player){
            if(!COOLDOWN.isValid((Player) sender)){
                throw new CommandException(ChatColor.RED + "You cannot change your keep inventory status yet! Please wait " + COOLDOWN.getPlayerDelayFormatted((Player) sender));
            }
            COOLDOWN.addPlayer((Player) sender);
            // Load, modify & save the user in LuckPerms.
            luckAPI.getUserManager().modifyUser(((Player) sender).getUniqueId(), (User user) -> {
                // Try to add the node to the user.
                if(sender.hasPermission(DISABLE_KEEPINV_PERMISSION)) {
                    DataMutateResult result = user.data().remove(node);
                    sender.sendMessage(ChatColor.AQUA + "Keep Inventory has been re-enabled for PvE");
                } else {
                    DataMutateResult result = user.data().add(node);
                    sender.sendMessage(ChatColor.AQUA + "Keep Inventory has been fully disabled.");
                }
            });
        }
        return true;
    }
}
