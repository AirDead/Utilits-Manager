package me.airdead.ru.utils

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Listener(var plugin: JavaPlugin) : Listener {
    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }
}
