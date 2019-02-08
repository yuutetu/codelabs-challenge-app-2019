package droidkaigi.github.io.challenge2019.domain.model

data class Comment (
    val id: Long,
    val auther: String,
    val text: String?,
    val url: String,
    val kids: List<Long>
)