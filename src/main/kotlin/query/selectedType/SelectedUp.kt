package query.selectedType

import FeatEatBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

class SelectedUp(private val update: Update, private val bot: FeatEatBot) {
    fun calcAndShow(){
        if (update.hasCallbackQuery() && (update.callbackQuery.data == "up")) {
            val userId = update.callbackQuery.from.id.toInt()
            val sex = bot.dataUsers[userId]?.get(0).toString()
            val age = bot.dataUsers[userId]?.get(1).toString().toInt()
            val height = bot.dataUsers[userId]?.get(2).toString().toFloat()
            val weight = bot.dataUsers[userId]?.get(3).toString().toFloat()
            val activity = bot.dataUsers[userId]?.get(4).toString()
            var activityFactor = 0F
            when (activity){
                "minimum" -> activityFactor = 1.2F
                "low" -> activityFactor = 1.375F
                "normal" -> activityFactor = 1.55F
                "high" -> activityFactor = 1.725F
                "max" -> activityFactor = 1.9F
            }
            val basicMetabolism = if (sex == "woman"){
                (447.7 + (9.2 * weight) + (3.1 * height) - (4.3 * age)) * activityFactor
            } else {
                (88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age)) * activityFactor
            }




            val message = SendMessage()
            message.chatId = update.callbackQuery.message.chatId.toString()
            message.text = basicMetabolism.toString()
            try {
                bot.execute(message)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }
        }
    }
}