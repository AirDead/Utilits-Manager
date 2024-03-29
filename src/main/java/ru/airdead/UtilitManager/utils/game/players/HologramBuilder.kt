package ru.airdead.UtilitManager.utils.game.players

import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.util.Vector

class HologramBuilder(private val text: MutableList<String>, private val location: Location) {
    private val entities = mutableListOf<ArmorStand>()

    init {
        location.add(Vector(0, -2, 0))
        overrideHologram()
    }

    fun setText(text: MutableList<String>) {
        this.text.clear()
        this.text.addAll(text)
        overrideHologram()
    }

    private fun overrideHologram() {
        for (i in text.indices) {
            if (entities.size > i) {
                entities[i].customName = text[i]
            } else {
                val armorStand = location.world?.spawn(location.add(Vector(0.0, i * 0.3, 0.0)), ArmorStand::class.java)

                armorStand?.apply {
                    customName = text[i]
                    isCustomNameVisible = true
                    setGravity(false)
                    isVisible = false
                    isInvulnerable = true
                }

                armorStand?.let { entities.add(it) }
            }
        }

        entities.subList(text.size, entities.size).forEach { it.remove() }
    }

    fun destroy() {
        entities.forEach { it.remove() }
    }
//    // Создание экземпляра Hologram
//    val hologramText = mutableListOf("Line 1", "Line 2", "Line 3")
//    val hologramLocation = Location(Bukkit.getWorld("world"), 1.0,2.0,3.0)
//    val hologram = HologramBuilder(hologramText, hologramLocation)
//
//    // Изменение текста
//    val newText = mutableListOf("Updated Line 1", "Updated Line 2")
//    hologram.setText(newText)
//
//    // Уничтожение голограммы
//    hologram.destroy()
}
