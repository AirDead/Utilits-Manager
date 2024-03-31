package ru.airdead.utilitmanager.io

/**
 * Converts a list of any type to a single string, with elements separated by a comma and a space.
 *
 * @param list The list containing any type of elements to be converted to a string.
 * @return A string representation of the list elements, separated by ", ".
 */
inline fun listToString(list: List<Any>): String {
    return list.joinToString(", ")
}
