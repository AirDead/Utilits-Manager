package me.airdead.ru.utils.game.players

import org.bukkit.entity.Player
import java.util.UUID

object CooldownManager {
    private val cooldowns: MutableMap<UUID, MutableMap<String, Long>> = HashMap()

    fun Player.hasCooldown(key: String): Boolean {
        val playerCooldowns = cooldowns[player?.uniqueId] ?: return false
        val currentTime = System.currentTimeMillis()
        return playerCooldowns[key]?.let { it > currentTime } ?: false
    }

    fun Player.getCooldown(key: String): Long {
        val playerCooldowns = cooldowns[player?.uniqueId] ?: return 0
        val currentTime = System.currentTimeMillis()
        return playerCooldowns[key]?.let { it - currentTime } ?: 0
    }

    fun Player.putCooldown(key: String, duration: Long) {
        val expirationTime = System.currentTimeMillis() + duration
        cooldowns.computeIfAbsent(player?.uniqueId!!) { HashMap() }[key] = expirationTime
    }
}