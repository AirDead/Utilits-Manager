package ru.airdead.UtilitManager.utils.game.players

import org.bukkit.ChatColor
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

object ItemBuilder {

    fun ItemStack.setName(name: String) {
        val meta = this.itemMeta
        meta?.setDisplayName(ChatColor.translateAlternateColorCodes('&', name))
        this.itemMeta = meta
    }

    fun ItemStack.setLore(lore: List<String>) {
        val meta = this.itemMeta
        meta?.lore = lore.map { ChatColor.translateAlternateColorCodes('&', it) }
        this.itemMeta = meta
    }

    fun <T, Z : Any> ItemStack.addPersistentDataContainer(key: String, type: PersistentDataType<T, Z>, value: Z) {
        val meta = this.itemMeta
        NamespacedKey.fromString(key)?.let {
            meta?.persistentDataContainer?.set(it, type, value)
        }
        this.itemMeta = meta
    }

    fun ItemStack.removePersistentDataContainer(key: String) {
        val meta = this.itemMeta
        NamespacedKey.fromString(key)?.let {
            meta?.persistentDataContainer?.remove(it)
        }
        this.itemMeta = meta
    }

    fun <T, Z : Any> ItemStack.hasPersistentDataContainer(key: String, type: PersistentDataType<T, Z>): Boolean {
        val meta = this.itemMeta
        return NamespacedKey.fromString(key)?.let { namespacedKey ->
            meta?.persistentDataContainer?.has(namespacedKey, type) ?: false
        } ?: false
    }

    fun ItemStack.setCustomModelData(value: Int) {
        val meta = this.itemMeta
        meta?.setCustomModelData(value)
        this.itemMeta = meta
    }

    fun ItemStack.addEnchant(enchant: Enchantment, level: Int, isUnsafe: Boolean = false) {
        if (isUnsafe) {
            addUnsafeEnchantment(enchant, level)
        } else {
            val meta = this.itemMeta
            if (meta != null && (meta.hasConflictingEnchant(enchant) || enchant.maxLevel < level)) {
                println("Cannot add enchantment due to conflict or level being too high.")
                return
            }
            addEnchantment(enchant, level)
        }
    }

    fun ItemStack.removeEnchant(enchant: Enchantment) {
        removeEnchantment(enchant)
    }
}
