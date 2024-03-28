package me.airdead.ru.utils.game.players

import org.bukkit.ChatColor
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

class ItemBuilder {


    fun ItemStack.setName(value: String?) {
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', value ?: "Error"))
    }

    fun ItemStack.setLore(value: List<String>) {
        val meta = itemMeta
        meta?.lore = value.map { ChatColor.translateAlternateColorCodes('&', it) }
        itemMeta = meta
    }

    fun ItemStack.addPersistent(key: String?, persistentDataType: PersistentDataType<*, *>?, value: Any) {
        itemMeta.persistentDataContainer.set<Any, Any>(NamespacedKey.fromString(key!!)!!,
            persistentDataType as PersistentDataType<Any, Any>, value)
    }

    fun ItemStack.removePersistent(key: String?) {
        itemMeta.persistentDataContainer.remove(NamespacedKey.fromString(key!!)!!)
    }

    fun ItemStack.addEnchant(enchantment: Enchantment, level: Int) {
        val meta = itemMeta
        meta?.addEnchant(enchantment, level, true)
        itemMeta = meta
    }

    fun ItemStack.removeEnchant(enchantment: Enchantment) {
        val meta = itemMeta
        meta?.removeEnchant(enchantment)
        itemMeta = meta
    }

    fun ItemStack.setCustomModelData(value: Int) {
        val meta = itemMeta
        meta?.setCustomModelData(value)
        itemMeta = meta
    }
}
