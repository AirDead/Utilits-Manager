package me.airdead.utils

import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class Listener(val plugin: JavaPlugin) : Listener {
    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }
}
