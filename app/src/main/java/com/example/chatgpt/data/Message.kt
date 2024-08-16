package com.example.chatgpt.data

data class Message(val text: String, val sender: Sender)

enum class Sender {
    AIBot,
    User;

    operator fun not(): Sender {
        return when(this) {
            AIBot -> User
            User -> AIBot
        }
    }
}