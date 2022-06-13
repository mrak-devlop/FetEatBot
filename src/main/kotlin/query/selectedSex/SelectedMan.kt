package query.selectedSex

import FeatEatBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class SelectedMan(private val update: Update, private val bot: FeatEatBot) {
    fun runCheck() {
        if (update.hasCallbackQuery() && (update.callbackQuery.data == "man")) {
            val userCallbackId = update.callbackQuery.from.id.toInt()
            bot.processingUsersAge.add(userCallbackId)
            val list = mutableListOf<String>()
            list.add(update.callbackQuery.data)
            bot.dataUsers[userCallbackId] = list
            PrintQueryAge(update, bot).printQuery()
        }
    }
}