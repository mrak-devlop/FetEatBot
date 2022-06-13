package query.selectedActivity

import FeatEatBot
import org.telegram.telegrambots.meta.api.objects.Update

class SelectedActivityMinimum(private val update: Update, private val bot: FeatEatBot) {
    fun runCheck() {
        if (update.hasCallbackQuery() && (update.callbackQuery.data == "minimum")) {
            val userCallbackId = update.callbackQuery.from.id.toInt()
            bot.dataUsers[userCallbackId]?.add(4, update.callbackQuery.data)
            PrintType(update, bot).printButton()
        }
    }
}