package query

import FeatEatBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class SaveWeight(private val update: Update, private val bot: FeatEatBot) {
    fun runCheck() {
        if (update.hasMessage() && bot.processingUsersWeight.contains(update.message.from.id.toInt())) {
            val message = SendMessage()
            message.chatId = update.message.chatId.toString()
            if (ValidationAfterSave(update.message.text.toString()).validate()) {
                val userId = update.message.from.id.toInt()
                bot.processingUsersWeight.remove(userId)
                bot.dataUsers[userId]?.add(3, update.message.text.toString())
                message.text = "Теперь укажите насколько вы активны"
                val keyboardButton = mutableListOf(
                    mutableListOf(InlineKeyboardButton(
                        "минимальный (малоподвижный образ жизни)"
                    )
                        .apply { callbackData = "minimum" }),
                    mutableListOf(InlineKeyboardButton("низкий (легкая физическая нагрузка)")
                        .apply { callbackData = "low" }),
                    mutableListOf(InlineKeyboardButton(
                        "средний (тренировки, активные прогулки)"
                    )
                        .apply { callbackData = "normal" }),
                    mutableListOf(InlineKeyboardButton("высокий (тренировки 6-7 дней в неделю)")
                        .apply { callbackData = "high" }),
                    mutableListOf(InlineKeyboardButton(
                        "очень высокий (несколько тренировок в день)"
                    )
                        .apply { callbackData = "max" })
                )
                val activityKeyboard = InlineKeyboardMarkup()
                activityKeyboard.keyboard = keyboardButton
                message.replyMarkup = activityKeyboard
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