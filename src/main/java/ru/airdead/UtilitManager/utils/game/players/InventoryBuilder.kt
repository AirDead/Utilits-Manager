package ru.airdead.UtilitManager.utils.game.players

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object InventoryBuilder {
    fun Inventory.setRow(row: Int, item: ItemStack) {
        val start = (row - 1) * 9
        val end = start + 9
        for (i in start until end){
            setItem(i, item)
        }
    }
    fun Inventory.setColumn(colonium: Int, item: ItemStack) {
        for (i in 0 until 6) {
            setItem(i * 9 + colonium - 1, item)
        }
    }
}