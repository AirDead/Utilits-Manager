package me.airdead.ru.utils.io

import java.util.*
import kotlin.collections.ArrayList

object ListUtils {

    fun listToString(list: List<Any>): String {
        val stb = StringBuilder()

        for (i in list.indices) {
            stb.append(list[i])
            if (i != list.size - 1) {
                stb.append(", ")
            }
        }

        return stb.toString()
    }

    fun arrayToList(array: Array<Any>): List<Any> {
        val rt = ArrayList<Any>()
        Collections.addAll(rt, *array)
        return rt
    }
}
