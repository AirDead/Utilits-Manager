package me.airdead.ru.utils.io

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class ConfigManager {
    var file: File? = null
        private set
    private var name: String
    var isYmlFormat: Boolean = false
        private set
    var isNew: Boolean = false
        private set

    constructor(folder: File, name: String) {
        if (!folder.exists()) {
            folder.mkdirs()
        }

        this.name = name

        if (name.endsWith(".yml")) {
            isYmlFormat = true
        }

        try {
            val file = File(folder, name)
            this.file = file
            if (!file.exists()) {
                file.createNewFile()
                isNew = true
            }
        } catch (e: IOException) {
            Bukkit.getLogger().warning(e.message)
            e.printStackTrace()
        }
    }

    constructor(plugin: JavaPlugin, name: String) {
        val folder = plugin.dataFolder

        if (!folder.exists()) {
            folder.mkdirs()
        }

        this.name = name

        if (name.endsWith(".yml")) {
            isYmlFormat = true
        }

        try {
            val file = File(folder, name)
            this.file = file
            if (!file.exists()) {
                file.createNewFile()
                isNew = true
            }
        } catch (e: IOException) {
            Bukkit.getLogger().warning(e.message)
            e.printStackTrace()
        }
    }

    fun save(data: FileConfiguration) {
        try {
            data.save(file!!)
        } catch (e: IOException) {
            Bukkit.getLogger().warning(e.message)
            e.printStackTrace()
        }
    }

    val configuration: FileConfiguration
        get() = YamlConfiguration.loadConfiguration(file!!)
// Example:
    //    public static ConfigManager config;
    //
    //    config = new ConfigManager(this, "config.yml");
    //        if(config.isNew()){
    //        FileConfiguration data = config.getConfiguration();
    //        data.set("example.section.in.example.config.with.example.value", "ExampleValue");
    //        data.getString("example.section.in.example.config.with.example.value");
    //        config.save(data);
    //    }
}