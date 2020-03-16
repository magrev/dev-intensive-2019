package ru.skillbranch.devintensive.models

class UserView(
    val id: String,
    val fullName: String,
    val nickName: String,
    var avater: String? = null,
    var status: String? = "offline",
    val initials: String?
) {
    fun printMe() {
        println(
            """
            id:$id:
            fullName:$fullName:
            nickName:$nickName:
            avater:$avater:
            status:$status:
            initials:$initials:
    """.trimIndent()
        )
    }
}