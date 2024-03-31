package ru.airdead.utilitmanager.io

import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

/**
 * Manages configuration files for Bukkit plugins. Supports both custom file locations and plugin data folders.
 */
class ConfigManager {
    /**
     * The configuration file being managed.
     */
    lateinit var file: File
        private set

    private var name: String

    /**
     * Indicates if the configuration file is in YAML format.
     */
    var isYmlFormat: Boolean = false
        private set

    /**
     * Indicates if the configuration file was newly created during the instantiation of this manager.
     */
    var isNew: Boolean = false
        private set

    private var configCache: FileConfiguration? = null

    /**
     * Constructs a ConfigManager with a specific folder and file name.
     *
     * @param folder The folder where the configuration file is located or will be created.
     * @param name The name of the configuration file, including the extension.
     */
    constructor(folder: File, name: String) : this(name) {
        setupFile(folder)
    }

    /**
     * Constructs a ConfigManager using the plugin's data folder and a specific file name.
     *
     * @param plugin The JavaPlugin instance.
     * @param name The name of the configuration file, including the extension.
     */
    constructor(plugin: JavaPlugin, name: String) : this(name) {
        setupFile(plugin.dataFolder)
    }

    /**
     * Private constructor used by other constructors to initialize common properties.
     *
     * @param name The name of the configuration file, including the extension.
     */
    private constructor(name: String) {
        this.name = name
        if (name.endsWith(".yml")) {
            isYmlFormat = true
        }
    }

    /**
     * Sets up the configuration file within the specified folder. If the folder or file doesn't exist, they will be created.
     *
     * @param folder The folder where the configuration file should be located.
     */
    private fun setupFile(folder: File) {
        if (!folder.exists()) {
            folder.mkdirs()
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

    /**
     * Saves the provided FileConfiguration to the managed file.
     *
     * @param data The FileConfiguration to save.
     */
    fun save(data: FileConfiguration) {
        try {
            data.save(file)
            configCache = data
        } catch (e: IOException) {
            Bukkit.getLogger().warning(e.message)
            e.printStackTrace()
        }
    }

    /**
     * Lazily loads and caches the FileConfiguration from the managed file.
     */
    val configuration: FileConfiguration
        get() {
            if (configCache == null) {
                configCache = YamlConfiguration.loadConfiguration(file)
            }
            return configCache!!
        }
}
