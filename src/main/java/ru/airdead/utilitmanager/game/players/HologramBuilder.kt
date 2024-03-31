package ru.airdead.utilitmanager.game.players

import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.util.Vector

/**
 * A class for building and managing holograms in the game.
 * Holograms are created using ArmorStands with custom text.
 *
 * @property text The list of strings representing the lines of the hologram.
 * @property location The initial location where the hologram is to be created.
 */
class HologramBuilder(private val text: MutableList<String>, private val location: Location) {
    val entities = mutableListOf<ArmorStand>()

    init {
        // Adjust the initial location for the hologram.
        location.add(Vector(0, -2, 0))
        // Create or update the hologram.
        overrideHologram()
    }

    /**
     * Sets or updates the text of the hologram. Existing text is cleared and replaced with the new text.
     *
     * @param text The new list of strings for the hologram text.
     */
    fun setText(text: MutableList<String>) {
        this.text.clear()
        this.text.addAll(text)
        overrideHologram()
    }

    /**
     * Creates or updates the hologram based on the current text and location.
     * Existing lines are updated, new lines are added, and excess lines are removed if necessary.
     */
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

    /**
     * Destroys the hologram by removing all associated ArmorStand entities.
     */
    fun destroy() {
        entities.forEach { it.remove() }
    }
}
