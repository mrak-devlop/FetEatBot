package query

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class HelpQuery (private val update: Update, private val bot: TelegramLongPollingBot) {
    fun runCheck() {
        if(update.hasMessage() && (update.message.text == "/help")){
            val message = SendMessage()
            message.chatId = update.message.chatId.toString()
            message.text = "чтобы запустить бота используйте команду /start \n \n" +
                    "если у вас возникли какие нибудь броблемы перезапустите бота командой /start"
            try {
                bot.execute(message)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }
            }
    }
}