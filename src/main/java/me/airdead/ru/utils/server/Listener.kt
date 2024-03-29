package me.airdead.ru.utils.server

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Listener(plugin: JavaPlugin): Listener {
    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }
}