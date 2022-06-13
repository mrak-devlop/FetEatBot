package query.selectedType.calc

class CalcPercent(private val inputValue: Float, private val percent: Int){
    fun calc(): Float {
        return (inputValue * percent) / 100
    }
}