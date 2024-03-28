package me.airdead.ru

import me.airdead.ru.commands.SellerMenuCommand
import me.cosmodev.utils.io.ConfigManager
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import sun.security.jgss.GSSUtil

class Plugin: JavaPlugin() {


    companion object {

        private lateinit var guiConfig: ConfigManager
        private lateinit var instane: Plugin

        fun getInstance(): Plugin {
            return instane
        }

        fun getGuiConfig(): FileConfiguration {
            return guiConfig.configuration
        }
    }
    override fun onEnable() {
        instane = this;
        SellerMenuCommand()
    }

    fun initConfigurations() {
        guiConfig = ConfigManager(this, "gui.yml")

        if(guiConfig.isNew) {
            val data = guiConfig.configuration

            data.set("name", "&5Скупщик жизней")


            guiConfig.save(data)
        }


    }


}