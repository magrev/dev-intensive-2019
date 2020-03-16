package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        //TODO FIX ME
//        var firstName = null
//        var lastName = null
        return if (fullName == null || fullName.isEmpty() || fullName == " ") {
            null to null
        } else {
            val parts: List<String>? = fullName.split(" ")
            var firstName = parts?.getOrNull(0)
            var lastName = parts?.getOrNull(1)
            firstName to lastName
        }
//        return Pair(firstName, lastName)

    }

    fun transliteration(payload: String, divider: String = " "): String {
        var fullName = transliteration_(payload.toLowerCase())
        val parts: List<String>? = fullName.split(" ")
        var firstName = parts?.getOrNull(0)?.capitalize()
        var lastName = parts?.getOrNull(1)?.capitalize()
        return firstName + divider + lastName
//        return fullName
    }

    private fun transliteration_(phrase: String) =
        phrase.replace(Regex("[абвгдеёжзийклмнопрстуфхцчшщьыъэюя]")) {
            when (it.value) {
                "а" -> "a"
                "б" -> "b"
                "в" -> "v"
                "г" -> "g"
                "д" -> "d"
                "е" -> "e"
                "ё" -> "e"
                "ж" -> "zh"
                "з" -> "z"
                "и" -> "i"
                "й" -> "i"
                "к" -> "k"
                "л" -> "l"
                "м" -> "m"
                "н" -> "n"
                "о" -> "o"
                "п" -> "p"
                "р" -> "r"
                "с" -> "s"
                "т" -> "t"
                "у" -> "u"
                "ф" -> "f"
                "х" -> "h"
                "ц" -> "c"
                "ч" -> "ch"
                "ш" -> "sh"
                "щ" -> "sh"
                "ъ" -> ""
                "ы" -> "i"
                "ь" -> ""
                "э" -> "e"
                "ю" -> "yu"
                "я" -> "ya"
                else -> it.value
            }
        }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return if (firstName != null && lastName != null) {
            val fn = firstName.substring(0, 1)
            val ln = lastName.substring(0, 1)
            fn + ln
        } else if (firstName == null && lastName == null) {
            null
        } else firstName?.substring(0, 1) ?: lastName?.substring(0, 1)
    }

}