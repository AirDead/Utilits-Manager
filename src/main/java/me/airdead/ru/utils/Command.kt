package me.airdead.ru.utils

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

abstract class Command(
    name: String?,
    plugin: JavaPlugin,
    isPlayerUse: Boolean,
    args: Int,
    perm: String?,
    delayTicks: Int
) :
    CommandExecutor, TabCompleter {

    private val cooldowns: MutableMap<UUID, Long> = HashMap()

    init {
        plugin.getCommand(name!!)!!.setExecutor(this)
        plugin.getCommand(name)!!.tabCompleter = this
        Companion.isPlayerUse = isPlayerUse
        Companion.args = args
        permission = perm
        Companion.delayTicks = delayTicks
    }

    override fun onCommand(commandSender: CommandSender, command: Command, s: String, strings: Array<String>): Boolean {
        if (isPlayerUse && commandSender !is Player) {
            commandSender.sendMessage("§cПисать эту команду может только игрок.")
            return true
        }

        if (!commandSender.hasPermission(permission!!)) {
            commandSender.sendMessage("§cYou do not have permission to use this command.")
            return true
        }

        if (strings.size != args) {
            commandSender.sendMessage("§cВы ввели недостаточное количество аргументов.")
            return true
        }

        if (isOnCooldown(commandSender)) {
            val remainingTime = getRemainingCooldown(commandSender)
            commandSender.sendMessage("§cКоманда на кулдауне. Пожалуйста, подождите еще $remainingTime секунд.")
            return true
        }

        execute(commandSender, strings, s)
        startCooldown(commandSender)

        return true
    }

    private fun isOnCooldown(sender: CommandSender): Boolean {
        val uuid = (sender as Player).uniqueId
        return cooldowns.containsKey(uuid) && cooldowns[uuid]!! > System.currentTimeMillis()
    }

    private fun getRemainingCooldown(sender: CommandSender): Int {
        val uuid = (sender as Player).uniqueId
        val remainingTime = (cooldowns[uuid]!! - System.currentTimeMillis()) / 1000
        return remainingTime.toInt()
    }

    private fun startCooldown(sender: CommandSender) {
        val uuid = (sender as Player).uniqueId
        cooldowns[uuid] = System.currentTimeMillis() + delayTicks * 50 // 20 тиков = 1 секунда
    }

    abstract fun execute(sender: CommandSender?, args: Array<String>?, label: String?)

    override fun onTabComplete(
        commandSender: CommandSender,
        command: Command,
        s: String,
        strings: Array<String>
    ): List<String>? {
        return tabComplete(commandSender, strings)
    }

    abstract fun tabComplete(sender: CommandSender?, args: Array<String>?): List<String>?

    companion object {
        private var isPlayerUse = false
        private var args = 0
        private var permission: String? = null
        private var delayTicks = 0
    }
//    import org.bukkit.command.CommandSender
//    import org.bukkit.entity.Player
//    import org.bukkit.plugin.java.JavaPlugin
//
//    class ExampleCommand(plugin: JavaPlugin) : Command(
//        "example", plugin, true, 0, "example.permission", 20 // Задержка в 20 тиков
//    ) {
//        override fun execute(sender: CommandSender?, args: Array<String>?, label: String?) {
//            val player = sender as Player
//
//            player.sendMessage("§aПример сообщения")
//
//            if (player.name == "Server") {
//                return
//            }
//
//            player.sendMessage("§cВаш ник не Server")
//        }
//
//        override fun tabComplete(sender: CommandSender?, args: Array<String>?): List<String>? {
//            return null
//        }
//    }
//
//    // Регистрация команды
//// В вашем плагине (в методе onEnable() или где-то подобном):
//    val exampleCommand = ExampleCommand(this)

}
