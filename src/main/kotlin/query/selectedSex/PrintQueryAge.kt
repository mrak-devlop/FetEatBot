package query.selectedSex

import FeatEatBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class PrintQueryAge(private val update: Update, private val bot: FeatEatBot) {
    fun printQuery() {
        val message = SendMessage()
        message.chatId = update.callbackQuery.message.chatId.toString()
        message.text = "Сколько вам полных лет?"
        try {
            bot.execute(message)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
}