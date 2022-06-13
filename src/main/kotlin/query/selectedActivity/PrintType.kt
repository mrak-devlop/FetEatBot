package query.selectedActivity

import FeatEatBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class PrintType(private val update: Update, private val bot: FeatEatBot) {
    fun printButton() {
        val message = SendMessage()
        message.chatId = update.callbackQuery.message.chatId.toString()
        message.text = "Какова ваша цель?"
        val keyboardButton = mutableListOf(
            mutableListOf(
                InlineKeyboardButton("Поддержание веса").apply { callbackData = "stable" }),
            mutableListOf(
                InlineKeyboardButton("Похудение").apply { callbackData = "down" }),
            mutableListOf(
                InlineKeyboardButton("Набор массы").apply { callbackData = "up" })
        )
        val typeKeyboard = InlineKeyboardMarkup()
        typeKeyboard.keyboard = keyboardButton
        message.replyMarkup = typeKeyboard
        try {
            bot.execute(message)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
}