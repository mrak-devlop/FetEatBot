package query

import FeatEatBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class SaveAge(private val update: Update, private val bot: FeatEatBot) {
    fun runCheck() {
        if (update.hasMessage() && bot.processingUsersAge.contains(update.message.from.id.toInt())) {
            val userId = update.message.from.id.toInt()
            bot.processingUsersAge.remove(userId)
            bot.processingUsersHeight.add(userId)
            val list = mutableListOf<String>()
            list.add(update.message.text.toString())
            bot.dataUsers[userId]?.add(1, update.message.text.toString())
            val message = SendMessage()
            message.chatId = update.message.chatId.toString()
            message.text = "Какой у вас рост (в сантиметрах)?"
            try {
                bot.execute(message)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }
        }
    }
}