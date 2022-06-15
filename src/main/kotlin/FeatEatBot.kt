import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import query.*
import query.selectedActivity.*
import query.selectedSex.SelectedMan
import query.selectedSex.SelectedWoman
import query.selectedType.SelectedDown
import query.selectedType.SelectedStable
import query.selectedType.SelectedUp


const val FET_EAT_BOT_TOKEN = ""
const val BOT_USER_NAME = ""

class FeatEatBot : TelegramLongPollingBot() {
    val processingUsersAge = arrayListOf<Int>()
    val processingUsersHeight = arrayListOf<Int>()
    val processingUsersWeight = arrayListOf<Int>()

    // [0] пол, [1] возраст, [2] рост, [3] вес, [4] активность
    val dataUsers = mutableMapOf<Int, MutableList<String>>()


    override fun onUpdateReceived(update: Update) {

        SelectedDown(update, this).calcAndShow()
        SelectedStable(update, this).calcAndShow()
        SelectedUp(update, this).calcAndShow()

        // обработка выбора активности и запуск выбора худеем или толстеем
        SelectedActivityMax(update, this).runCheck()
        SelectedActivityHigh(update, this).runCheck()
        SelectedActivityNormal(update, this).runCheck()
        SelectedActivityLow(update, this).runCheck()
        SelectedActivityMinimum(update, this).runCheck()

        // если выбран мужчина запрос возраста сохраняем это и запраштваем рост возраст
        SelectedMan(update, this).runCheck()
        // если выбрана женщина запрос возраста сохраняем это и запрашиваем возраст
        SelectedWoman(update, this).runCheck()

        // если введен вес сохраняем его и выводим кнопки для выбора активности
        SaveWeight(update, this).runCheck()

        // если введен рост сохраняем его и запрашиваем вес
        SaveHeight(update, this).runCheck()

        // если введен возраст сохраняем его и запрашиваем рост
        SaveAge(update, this).runCheck()

        // ответ на /start и показ двух кнопок выбора мужчины или женщины
        StartQuery(update, this).runCheck()

    }


    override fun getBotUsername(): String {
        return BOT_USER_NAME
    }

    override fun getBotToken(): String {
        return FET_EAT_BOT_TOKEN
    }

}
