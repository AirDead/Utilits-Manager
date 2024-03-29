package me.airdead.ru.utils.io

object ListUtility {

    fun listToString(list: List<Any>): String {
        return list.joinToString(", ")
    }

    fun arrayToList(array: Array<Any>): List<Any> {
        return array.toList()
    }

    fun stringToList(input: String, delimiter: String): List<String> {
        return input.split(delimiter)
    }

//
//    val exampleList = listOf("apple", "banana", "orange")
//    val listAsString = ListUtility.listToString(exampleList)
//    println("$listAsString") // Выведет: apple, banana, orange
//
//    val exampleArray = arrayOf("dog", "cat", "rabbit")
//    val arrayToList = ListUtility.arrayToList(exampleArray)
//    println("$arrayToList") // Выведет: [dog, cat, rabbit]
//
//    val exampleString = "apple,banana,orange"
//    val delimiter = ","
//    val stringToList = ListUtility.stringToList(exampleString, delimiter)
//    println("$stringToList") // Выведет:  [apple, banana, orange]
//

}
