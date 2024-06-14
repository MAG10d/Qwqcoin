package xyz.lgls.qwqgive;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Qwqgive extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("qwq").setExecutor(new GiveCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

class GiveCommand implements CommandExecutor {
    private final HashMap<String, String> itemMap;

    public GiveCommand() {
        itemMap = new HashMap<>();
        itemMap.put(ChatColor.translateAlternateColorCodes('&', "&b無盡&8儲存單元"), "NTW_QUANTUM_STORAGE_8");
        itemMap.put(ChatColor.translateAlternateColorCodes('&', "&9基礎&8儲存單元"), "NTW_QUANTUM_STORAGE_1");
        itemMap.put(ChatColor.translateAlternateColorCodes('&', "&c高級&8儲存單元"), "NTW_QUANTUM_STORAGE_2");
        itemMap.put(ChatColor.translateAlternateColorCodes('&', "&f超級&8儲存單元"), "NTW_QUANTUM_STORAGE_3");
        itemMap.put(ChatColor.translateAlternateColorCodes('&', "&8虛空&8儲存單元"), "NTW_QUANTUM_STORAGE_4");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return true; // return if the item is null or does not have a display name
        }

        String itemName = item.getItemMeta().getDisplayName();
        String itemId = itemMap.get(itemName);

        if (itemId == null) {
            return true; // return if the item's name is not in the map
        }

        // check if the player has enough items
        int amount = item.getAmount();
        if (amount < 1) { // replace 1 with the amount you want to check
            player.sendMessage(ChatColor.RED + "You do not have enough items!");
            return true;
        }

        // remove the item from the player's hand completely
        player.getInventory().setItemInMainHand(null);

        // execute the command
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "sf give " + player.getName() + " " + itemId + " " + amount);
        return true;
    }
}