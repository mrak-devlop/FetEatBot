package query.selectedSex

import FeatEatBot
import org.telegram.telegrambots.meta.api.objects.Update

class SelectedWoman(private val update: Update, private val bot: FeatEatBot) {
    fun runCheck() {
        if (update.hasCallbackQuery() && (update.callbackQuery.data == "woman")) {
            val userCallbackId = update.callbackQuery.from.id.toInt()
            val list = mutableListOf<String>()
            list.add(update.callbackQuery.data)
            bot.dataUsers[userCallbackId] = list
            bot.processingUsersAge.add(userCallbackId)
            PrintQueryAge(update, bot).printQuery()
        }
    }
}