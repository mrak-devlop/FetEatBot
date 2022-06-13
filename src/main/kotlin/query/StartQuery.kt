package query

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class StartQuery(private val update: Update, private val bot: TelegramLongPollingBot) {
    fun runCheck() {
        if (update.hasMessage() && (update.message.text == "/start")) {
            val message = SendMessage()
            message.chatId = update.message.chatId.toString()
            message.text = "Привет я Ви. И я помогу тебе правильно расчитать питание по КБЖУ"
            try {
                bot.execute(message)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }

            message.text = "Для этого я задам пару вопросов"

            try {
                bot.execute(message)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }
            message.text = "У мужчин и женщин метаболизм различается, для кого будем считать?"

            val keyboardButton = mutableListOf(
                mutableListOf(
                    InlineKeyboardButton("Мужчина").apply { callbackData = "man" },
                    InlineKeyboardButton("Женщина").apply { callbackData = "woman" }
                )
            )
            val sexKeyboard = InlineKeyboardMarkup()
            sexKeyboard.keyboard = keyboardButton
            message.replyMarkup = sexKeyboard
            try {
                bot.execute(message)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }

        }
    }
}