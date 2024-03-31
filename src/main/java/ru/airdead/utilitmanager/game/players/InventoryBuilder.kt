package ru.airdead.utilitmanager.game.players

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * Sets all slots of a specified row in the inventory to a given item.
 * Rows are 1-indexed and the function accounts for this by adjusting the starting index.
 *
 * @param row The row number where the item should be placed, starting from 1.
 * @param item The item to set in each slot of the row.
 */
fun Inventory.setRow(row: Int, item: ItemStack) {
    val start = (row - 1) * 9
    val end = start + 9
    for (i in start until end) {
        setItem(i, item)
    }
}

/**
 * Sets all slots of a specified column in the inventory to a given item.
 * Columns are 1-indexed and the function adjusts for this by modifying the index calculation.
 *
 * @param column The column number where the item should be placed, starting from 1.
 * @param item The item to set in each slot of the column.
 */
fun Inventory.setColumn(column: Int, item: ItemStack) {
    for (i in 0 until 6) {
        setItem(i * 9 + column - 1, item)
    }
}
