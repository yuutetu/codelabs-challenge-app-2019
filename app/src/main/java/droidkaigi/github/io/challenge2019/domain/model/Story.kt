package droidkaigi.github.io.challenge2019.domain.model

data class Story (
    val id: Long,
    val title: String,
    val url: String,
    val score: Int,
    val auther: String
)