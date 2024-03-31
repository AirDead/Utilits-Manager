package ru.airdead.utilitmanager.game.players

import org.bukkit.entity.Player
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * A global map that tracks cooldowns for each player using their UUID. The inner map associates a cooldown key with its expiration time in milliseconds.
 */
val cooldowns: MutableMap<UUID, MutableMap<String, Long>> = ConcurrentHashMap()

/**
 * Checks if this player has an active cooldown for a specified key.
 *
 * @param key The identifier for the cooldown to check.
 * @return `true` if the cooldown is active, `false` otherwise.
 */
fun Player.hasCooldown(key: String): Boolean {
    val playerCooldowns = cooldowns[this.uniqueId] ?: return false
    val currentTime = System.currentTimeMillis()
    val cooldownEndTime = playerCooldowns[key] ?: return false
    return cooldownEndTime > currentTime
}

/**
 * Retrieves the remaining cooldown time in milliseconds for a specified key for this player.
 *
 * @param key The identifier for the cooldown whose remaining time is to be retrieved.
 * @return The remaining cooldown time in milliseconds, or `0` if there's no active cooldown for the specified key.
 */
fun Player.getCooldown(key: String): Long {
    val playerCooldowns = cooldowns[this.uniqueId] ?: return 0
    val currentTime = System.currentTimeMillis()
    val cooldownEndTime = playerCooldowns[key] ?: return 0
    return (cooldownEndTime - currentTime).coerceAtLeast(0)
}

/**
 * Sets or updates a cooldown for a specified key for this player. If a cooldown already exists for the given key, it will be overwritten.
 *
 * @param key The identifier for the cooldown to set or update.
 * @param duration The duration of the cooldown in milliseconds.
 */
fun Player.putCooldown(key: String, duration: Long) {
    val playerUUID = this.uniqueId
    val expirationTime = System.currentTimeMillis() + duration
    cooldowns.computeIfAbsent(playerUUID) { ConcurrentHashMap() }[key] = expirationTime
}
