package ru.airdead.utilitmanager.game.players

import org.bukkit.ChatColor
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

/**
 * Sets the display name of this ItemStack, with support for color codes using the '&' character.
 *
 * @param name The new display name for the ItemStack, '&' character can be used for color codes.
 */
fun ItemStack.setName(name: String) {
    val meta = this.itemMeta
    meta?.setDisplayName(ChatColor.translateAlternateColorCodes('&', name))
    this.itemMeta = meta
}

/**
 * Sets the lore for this ItemStack, each string in the list represents a line. Supports color codes using the '&' character.
 *
 * @param lore A list of strings representing the lore lines, '&' character can be used for color codes.
 */
fun ItemStack.setLore(lore: List<String>) {
    val meta = this.itemMeta
    meta?.lore = lore.map { ChatColor.translateAlternateColorCodes('&', it) }
    this.itemMeta = meta
}

/**
 * Adds a custom data to this ItemStack's meta, using a namespaced key.
 *
 * @param key The string representation of the NamespacedKey for this data.
 * @param type The type of the data being stored.
 * @param value The value of the data to store.
 */
fun <T, Z : Any> ItemStack.addPersistentDataContainer(key: String, type: PersistentDataType<T, Z>, value: Z) {
    val meta = this.itemMeta
    NamespacedKey.fromString(key)?.let {
        meta?.persistentDataContainer?.set(it, type, value)
    }
    this.itemMeta = meta
}

/**
 * Removes a custom data from this ItemStack's meta, using a namespaced key.
 *
 * @param key The string representation of the NamespacedKey for this data.
 */
fun ItemStack.removePersistentDataContainer(key: String) {
    val meta = this.itemMeta
    NamespacedKey.fromString(key)?.let {
        meta?.persistentDataContainer?.remove(it)
    }
    this.itemMeta = meta
}

/**
 * Checks if this ItemStack's meta contains a specific custom data, using a namespaced key.
 *
 * @param key The string representation of the NamespacedKey for this data.
 * @param type The type of the data being checked.
 * @return `true` if the ItemStack contains the specified data, `false` otherwise.
 */
fun <T, Z : Any> ItemStack.hasPersistentDataContainer(key: String, type: PersistentDataType<T, Z>): Boolean {
    val meta = this.itemMeta
    return NamespacedKey.fromString(key)?.let { namespacedKey ->
        meta?.persistentDataContainer?.has(namespacedKey, type) ?: false
    } ?: false
}

/**
 * Sets custom model data for this ItemStack.
 *
 * @param value The custom model data identifier.
 */
fun ItemStack.setCustomModelData(value: Int) {
    val meta = this.itemMeta
    meta?.setCustomModelData(value)
    this.itemMeta = meta
}

/**
 * Adds an enchantment to this ItemStack, optionally allowing for "unsafe" enchantments (levels above the max, or conflicting enchantments).
 *
 * @param enchant The enchantment to add.
 * @param level The level of the enchantment.
 * @param isUnsafe Whether to bypass normal safety checks for enchantment compatibility and levels.
 */
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

/**
 * Removes a specific enchantment from this ItemStack.
 *
 * @param enchant The enchantment to remove.
 */
fun ItemStack.removeEnchant(enchant: Enchantment) {
    removeEnchantment(enchant)
}
