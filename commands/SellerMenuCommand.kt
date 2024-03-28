package me.airdead.ru.commands

import me.airdead.ru.Plugin
import me.airdead.ru.utils.game.players.InventoryBuilder
import me.airdead.ru.utils.Command
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class SellerMenuCommand() : Command(
    "seller", Plugin.getInstance(), true, 0, "seller.menu", 20
) {
    override fun execute(sender: CommandSender?, args: Array<String>?, label: String?) {
        val player = sender as Player


        var inventory: Inventory = Bukkit.createInventory(
            null,
            54,
            ChatColor.translateAlternateColorCodes(
                '&',
                Plugin.getGuiConfig().get("name", "&5Скупщик жизней").toString()
            )
        )

        
        player.openInventory(inventory)


    }

    override fun tabComplete(sender: CommandSender?, args: Array<String>?): List<String>? {
        return null
    }
}