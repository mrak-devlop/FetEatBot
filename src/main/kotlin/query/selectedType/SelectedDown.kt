package query.selectedType

import FeatEatBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import query.selectedType.calc.CalcPercent
import kotlin.math.round

class SelectedDown(private val update: Update, private val bot: FeatEatBot) {
    fun calcAndShow() {
        if (update.hasCallbackQuery() && (update.callbackQuery.data == "down")) {
            val userId = update.callbackQuery.from.id.toInt()
            val sex = bot.dataUsers[userId]?.get(0).toString()
            val age = bot.dataUsers[userId]?.get(1).toString().toInt()
            val height = bot.dataUsers[userId]?.get(2).toString().toFloat()
            val weight = bot.dataUsers[userId]?.get(3).toString().toFloat()
            val activity = bot.dataUsers[userId]?.get(4).toString()
            var activityFactor = 0F
            when (activity) {
                "minimum" -> activityFactor = 1.2F
                "low" -> activityFactor = 1.375F
                "normal" -> activityFactor = 1.55F
                "high" -> activityFactor = 1.725F
                "max" -> activityFactor = 1.9F
            }
            var basicMetabolism = if (sex == "woman") {
                (447.7 + (9.2 * weight) + (3.1 * height) - (4.3 * age)) * activityFactor
            } else {
                (88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age)) * activityFactor
            }
            basicMetabolism = round(basicMetabolism * 100.0) / 100.0
            var metabolismBottomLine =
                basicMetabolism - CalcPercent(basicMetabolism.toFloat(), 20).calc()
            metabolismBottomLine = round(metabolismBottomLine * 100.0) / 100.0
            var metabolismUpperLine =
                basicMetabolism - CalcPercent(basicMetabolism.toFloat(), 10).calc()
            metabolismUpperLine = round(metabolismUpperLine * 100.0) / 100.0

            val message = SendMessage()
            message.chatId = update.callbackQuery.message.chatId.toString()
            message.text =
                "Для похудения вам нообходимо потреблять \n от $metabolismBottomLine до $metabolismUpperLine ккал"
            try {
                bot.execute(message)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }

            val proteinsBottomLine = round(
                CalcPercent(metabolismBottomLine.toFloat(), 40).calc() * 100.0
            ) / 100.0
            val fatsBottomLine = round(
                CalcPercent(metabolismBottomLine.toFloat(), 35).calc() * 100.0
            ) / 100.0
            val carbohydratesBottomLine = round(
                CalcPercent(metabolismBottomLine.toFloat(), 25).calc() * 100.0
            ) / 100.0
            val proteinsInGramBottomLine = round((proteinsBottomLine / 4) * 100.0) / 100.0
            val fatsInGramBottomLine = round((fatsBottomLine / 9) * 100.0) / 100.0
            val carbohydratesInGramBottomLine = round((carbohydratesBottomLine / 4) * 100.0) / 100.0

            val proteinsUpperLine = round(
                CalcPercent(metabolismUpperLine.toFloat(), 40).calc() * 100.0
            ) / 100.0
            val fatsUpperLine = round(
                CalcPercent(metabolismUpperLine.toFloat(), 35).calc() * 100.0
            ) / 100.0
            val carbohydratesUpperLine = round(
                CalcPercent(metabolismUpperLine.toFloat(), 25).calc() * 100.0
            ) / 100.0
            val proteinsInGramUpperLine = round((proteinsUpperLine / 4) * 100.0) / 100.0
            val fatsInGramUpperLine = round((fatsUpperLine / 9) * 100.0) / 100.0
            val carbohydratesInGramUpperLine = round((carbohydratesUpperLine / 4) * 100.0) / 100.0

            message.text = "Белка 40% = от $proteinsBottomLine до $proteinsUpperLine ккал = \n" +
                    "от $proteinsInGramBottomLine до $proteinsInGramUpperLine г."
            try {
                bot.execute(message)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }

            message.text = "Жиров 35% = от $fatsBottomLine до $fatsUpperLine ккал = \n" +
                    "от $fatsInGramBottomLine до $fatsInGramUpperLine г."
            try {
                bot.execute(message)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }

            message.text = "Углеводов 25% = от $carbohydratesBottomLine до $carbohydratesUpperLine ккал = \n" +
                    "от $carbohydratesInGramBottomLine до $carbohydratesInGramUpperLine г."
            try {
                bot.execute(message)
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }
        }
    }
}