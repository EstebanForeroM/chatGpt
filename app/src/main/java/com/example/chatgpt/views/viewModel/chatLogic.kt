package com.example.chatgpt.views.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatgpt.data.Message
import com.example.chatgpt.data.Result
import com.example.chatgpt.data.Sender
import com.example.chatgpt.data.clearAIChat
import com.example.chatgpt.data.getAIMessage
import com.example.chatgpt.data.getAIMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ChatApp: ViewModel() {
    val messageHandler: MessageHandler

    // This variable is initialized just for testing purposes
    private val messages = mutableStateListOf<Message>()

    private suspend fun getBotResponse(userPrompt: String): String {
        return getAIMessage(userPrompt)
    }

    private suspend fun clearBotChat() {
        clearAIChat()
    }

    init {
        messageHandler = MessageHandler(messages, ::getBotResponse, ::clearBotChat, viewModelScope)
    }

    fun getChatMessages(): List<Message> {
        return messages
    }
}

class MessageHandler(private val messages: MutableList<Message>,
                     val getBotResponse: suspend (String) -> String,
                     val clearBotResponse: suspend () -> Unit,
                     val coroutineScope: CoroutineScope
    ) {
    private var nextSender: Sender = if (messages.size == 0) {
        Sender.User
    } else {
        when (messages.last().sender) {
            Sender.User -> Sender.AIBot
            Sender.AIBot -> Sender.User
        }
    }

    init {
        if (nextSender == Sender.AIBot) {
            answerUserPrompt()
        }
    }

    fun clearMessages() {
        coroutineScope.launch {
            clearBotResponse()
            nextSender = Sender.User
            messages.clear()
        }
    }

    fun sendUserMessage(text: String) {
        sendMessage(text, Sender.User)
        answerUserPrompt()
    }

    private fun sendMessage(text: String, sender: Sender): Result<Unit, ErrorKind> {
        Log.d("sendMessage", "trying to send message with text {$text} and sender {$sender}, the expected sender is {$nextSender}")
        if (sender != nextSender) {
            return Result.Error(ErrorKind.InvalidSender)
        }
        nextSender = !nextSender
        messages.add(Message(text, sender))
        return Result.Success(Unit)
    }

    private fun answerUserPrompt() {
        if (messages.size == 0) {
            nextSender = Sender.User
            return Unit
        }
        val lastUserPrompt = messages[messages.size - 1]
        coroutineScope.launch {
            val botResponse = getBotResponse(lastUserPrompt.text);
            sendMessage(botResponse, Sender.AIBot)
        }
    }
}

enum class ErrorKind() {
    InvalidSender
}