package query.selectedActivity

import FeatEatBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.exceptions.TelegramApiException


class SelectedActivityNormal(private val update: Update, private val bot: FeatEatBot) {
    fun runCheck() {
        if (update.hasCallbackQuery() && (update.callbackQuery.data == "normal")) {
            val userCallbackId = update.callbackQuery.from.id.toInt()
            bot.dataUsers[userCallbackId]?.add(4, update.callbackQuery.data)
            PrintType(update, bot).printButton()
        }
    }
}