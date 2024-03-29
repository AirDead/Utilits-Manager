package ru.airdead.UtilitManager.utils.server

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Listener(plugin: JavaPlugin): Listener {
    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }
}