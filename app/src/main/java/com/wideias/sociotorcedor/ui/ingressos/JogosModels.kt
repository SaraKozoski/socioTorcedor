
enum class StatusIngresso { DISPONIVEL, ESGOTADO, EM_BREVE }

data class JogoDisponivel(
    val id          : Int,
    val mandante    : String,
    val visitante   : String,
    val competicao  : String,
    val data        : String,
    val hora        : String,
    val estadio     : String,
    val precoMinimo : Double?,
    val precoOriginal: Double? = null,
    val status      : StatusIngresso,
    val dataAbertura: String? = null   // usado quando status == EM_BREVE
)
