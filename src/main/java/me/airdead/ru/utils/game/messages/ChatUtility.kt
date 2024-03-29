package me.airdead.ru.utils.game.messages

import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

object ChatUtils {

    fun format(message: String): String {
        return ChatColor.translateAlternateColorCodes('&', message)
    }

    fun Player.sendColorMessage(text: String) {
        sendMessage(ChatColor.translateAlternateColorCodes('&', text))
    }

    fun broadcastMessage(text: String) {
        Bukkit.getOnlinePlayers().forEach {
            it.sendMessage(ChatColor.translateAlternateColorCodes('&', text))
        }
    }

    fun Player.sendColorTitle(title: String, subtitle: String, fadeIn: Int, stay: Int, fadeOut: Int) {
        sendTitle(
            ChatColor.translateAlternateColorCodes('&', title),
            ChatColor.translateAlternateColorCodes('&', subtitle),
            fadeIn,
            stay,
            fadeOut
        )
    }

    fun Player.sendColorTitle(title: String, subtitle: String) {
        sendColorTitle(title, subtitle, 1, 1, 1)
    }

    fun broadcastTitle(title: String, subtitle: String, fadeIn: Int, stay: Int, fadeOut: Int) {
        Bukkit.getOnlinePlayers().forEach {
            it.sendColorTitle(title, subtitle, fadeIn, stay, fadeOut)
        }
    }

    fun broadcastTitle(title: String, subtitle: String) {
        broadcastTitle(title, subtitle, 1, 1, 1)
    }

    fun Player.sendActionBar(instance: JavaPlugin, text: String, sec: Int) {
        with(player!!) {
            spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(text))

            val task: BukkitTask = object : BukkitRunnable() {
                override fun run() {
                    spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(""))
                }
            }.runTaskLater(instance, (sec * 20).toLong())
        }
    }

    fun broadcastActionBar(instance: JavaPlugin, text: String, sec: Int) {
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
