package query

class ValidationAfterSave(private val input: String) {
    fun validate(): Boolean {
        input.toFloatOrNull() ?: return false
        try {
            input.toFloat()
        } catch (e: NumberFormatException) {
            return false
        }
        return true
    }
}

