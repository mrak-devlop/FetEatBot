package query

import FeatEatBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class SaveHeight(private val update: Update, private val bot: FeatEatBot) {
    fun runCheck() {
        if (update.hasMessage() && bot.processingUsersHeight.contains(update.message.from.id.toInt())) {
            val message = SendMessage()
            message.chatId = update.message.chatId.toString()
            if (ValidationAfterSave(update.message.text.toString()).validate()) {
                val userId = update.message.from.id.toInt()
                bot.processingUsersHeight.remove(userId)
                bot.processingUsersWeight.add(userId)
                val list = mutableListOf<String>()
                list.add(update.message.text.toString())
                bot.dataUsers[userId]?.add(2, update.message.text.toString())
                message.text = "Какой у вас вес (в килограммах)?"
                try {
                    bot.execute(message)
                } catch (e: TelegramApiException) {
                    e.printStackTrace()
                }
            } else {

                message.text = "Я не могу понять это значение, \n" +
                        "введите целое число или дробное в формате 0.0"
                try {
                    bot.execute(message)
                } catch (e: TelegramApiException) {
                    e.printStackTrace()
                }
            }
        }
    }
}