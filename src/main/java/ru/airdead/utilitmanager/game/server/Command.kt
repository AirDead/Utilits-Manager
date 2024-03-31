package ru.airdead.utilitmanager.server

import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

/**
 * Abstract class to handle creation and management of commands.
 * Includes features like player-only execution, permission checks, argument requirements, and cooldown management.
 */
abstract class Command(
    name: String,
    plugin: JavaPlugin,
    private val isPlayersOnly: Boolean, // Restricts command usage to players only.
    private val argsRequirement: Int, // Minimum number of arguments required to execute the command.
    private val permission: String?, // Permission required to execute the command. Null if no permission required.
    private val delayTicks: Int // Cooldown in ticks before command can be executed again.
) : CommandExecutor, TabCompleter {

    private val cooldowns: MutableMap<UUID, Long> = HashMap() // Tracks cooldowns for players.

    init {
        plugin.getCommand(name)?.setExecutor(this)
        plugin.getCommand(name)?.setTabCompleter(this)
    }

    /**
     * Handles command execution, including checks for player-only commands, permissions, arguments, and cooldowns.
     */
    override fun onCommand(commandSender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<String>): Boolean {
        if (isPlayersOnly && commandSender !is Player) {
            commandSender.sendMessage("§cЭту команду может использовать только игрок.")
            return true
        }

        if (permission != null && !commandSender.hasPermission(permission)) {
            commandSender.sendMessage("§cУ вас нет прав для использования этой команды.")
            return true
        }

        if (args.size < argsRequirement) {
            commandSender.sendMessage("§cНедостаточное количество аргументов.")
            return true
        }

        if (isOnCooldown(commandSender)) {
            val remainingTime = getRemainingCooldown(commandSender)
            commandSender.sendMessage("§cКоманда на кулдауне. Пожалуйста, подождите еще $remainingTime секунд.")
            return true
        }

        execute(commandSender, args, label)
        startCooldown(commandSender)

        return true
    }

    /**
     * Checks if the command sender is currently on cooldown.
     */
    private fun isOnCooldown(sender: CommandSender): Boolean {
        if (sender !is Player) return false

        val uuid = sender.uniqueId
        return cooldowns.containsKey(uuid) && cooldowns[uuid]!! > System.currentTimeMillis()
    }

    /**
     * Gets the remaining cooldown time in seconds for the command sender.
     */
    private fun getRemainingCooldown(sender: CommandSender): Long {
        if (sender !is Player) return 0

        val uuid = sender.uniqueId
        val remainingTime = (cooldowns[uuid]!! - System.currentTimeMillis()) / 1000
        return remainingTime
    }

    /**
     * Initiates or refreshes the cooldown for the command sender.
     */
    private fun startCooldown(sender: CommandSender) {
        if (sender !is Player) return

        val uuid = sender.uniqueId
        cooldowns[uuid] = System.currentTimeMillis() + delayTicks * 50
    }

    /**
     * Abstract method to define the execution logic of the command.
     */
    abstract fun execute(sender: CommandSender, args: Array<String>, label: String)

    /**
     * Handles tab completion for the command, providing a list of available options based on the current arguments.
     */
    override fun onTabComplete(sender: CommandSender, command: org.bukkit.command.Command, alias: String, args: Array<String>): List<String>? {
        return tabComplete(sender, args)
    }

    /**
     * Abstract method to define the tab completion logic of the command.
     */
    abstract fun tabComplete(sender: CommandSender, args: Array<String>): List<String>?
}
