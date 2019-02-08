package droidkaigi.github.io.challenge2019.domain.mapper

import droidkaigi.github.io.challenge2019.data.api.response.Item
import droidkaigi.github.io.challenge2019.domain.model.Comment

class CommentMapper {
    fun translate(item: Item): Comment {
        return Comment(
            auther = item.author,
            text = item.text
        )
    }
}