package ru.airdead.utilitmanager.game.messages

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

/**
 * A utility object that provides functionalities to manage chat messages within the game,
 * including sending colored messages and titles to players, and broadcasting them.
 */
object ChatUtility {

    /**
     * Extension property to convert strings with '&' color codes to Minecraft colored strings.
     */
    val String.colored: String
        get() = ChatColor.translateAlternateColorCodes('&', this)

    /**
     * Sends a colored message to this player.
     *
     * @param text The text of the message to send, '&' character is used for color codes.
     */
    fun Player.sendColorMessage(text: String) {
        sendMessage(text.colored)
    }

    /**
     * Broadcasts a colored message to all players.
     *
     * @param text The text of the message to broadcast, '&' character is used for color codes.
     */
    fun broadcastColorMessage(text: String) {
        Bukkit.broadcastMessage(text.colored)
    }

    /**
     * Sends a colored title and subtitle to this player with specified durations.
     *
     * @param title The title text to send, '&' character is used for color codes.
     * @param subtitle The subtitle text to send, '&' character is used for color codes.
     * @param fadeIn The duration in ticks for the title to fade in.
     * @param stay The duration in ticks for the title to stay visible.
     * @param fadeOut The duration in ticks for the title to fade out.
     */
    fun Player.sendColorTitle(title: String, subtitle: String, fadeIn: Int, stay: Int, fadeOut: Int) {
        sendTitle(title.colored, subtitle.colored, fadeIn, stay, fadeOut)
    }

    /**
     * Sends a colored title and subtitle to this player with default durations.
     *
     * @param title The title text to send, '&' character is used for color codes.
     * @param subtitle The subtitle text to send, '&' character is used for color codes.
     */
    fun Player.sendColorTitle(title: String, subtitle: String) {
        sendColorTitle(title, subtitle, 1, 1, 1)
    }

    /**
     * Broadcasts a colored title and subtitle to all players with specified durations.
     *
     * @param title The title text to broadcast, '&' character is used for color codes.
     * @param subtitle The subtitle text to broadcast, '&' character is used for color codes.
     * @param fadeIn The duration in ticks for the title to fade in.
     * @param stay The duration in ticks for the title to stay visible.
     * @param fadeOut The duration in ticks for the title to fade out.
     */
    fun broadcastColorTitle(title: String, subtitle: String, fadeIn: Int, stay: Int, fadeOut: Int) {
        Bukkit.getOnlinePlayers().forEach {
            it.sendColorTitle(title, subtitle, fadeIn, stay, fadeOut)
        }
    }

    /**
     * Broadcasts a colored title and subtitle to all players with default durations.
     *
     * @param title The title text to broadcast, '&' character is used for color codes.
     * @param subtitle The subtitle text to broadcast, '&' character is used for color codes.
     */
    fun broadcastColorTitle(title: String, subtitle: String) {
        broadcastColorTitle(title, subtitle, 1, 1, 1)
    }

    /**
     * Sends a colored action bar message to this player for a specified duration.
     *
     * @param instance The plugin instance required for scheduling tasks.
     * @param text The text of the action bar message to send, '&' character is used for color codes.
     * @param sec The duration in seconds for which the action bar message will be visible.
     */
    fun Player.sendColorActionBar(instance: Plugin, text: String, sec: Int) {
        with(player!!) {
            spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(text))

            val task: BukkitTask = object : BukkitRunnable() {
                override fun run() {
                    spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(""))
                }
            }.runTaskLater(instance, (sec * 20).toLong())
        }
    }

    /**
     * Broadcasts a colored action bar message to all players for a specified duration.
     *
     * @param instance The plugin instance required for scheduling tasks.
     * @param text The text of the action bar message to broadcast, '&' character is used for color codes.
     * @param sec The duration in seconds for which the action bar message will be visible to all players.
     */
    fun broadcastColorActionBar(instance: Plugin, text: String, sec: Int) {
        Bukkit.getOnlinePlayers().forEach { player ->
            with(player) {
                spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(text))

                val task: BukkitTask = object : BukkitRunnable() {
                    override fun run() {
                        spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(""))
                    }
                }.runTaskLater(instance, (sec * 20).toLong())
            }
        }
    }
}
