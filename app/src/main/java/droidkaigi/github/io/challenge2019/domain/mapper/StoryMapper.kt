package droidkaigi.github.io.challenge2019.domain.mapper

import droidkaigi.github.io.challenge2019.data.api.response.Item
import droidkaigi.github.io.challenge2019.domain.model.Story

class StoryMapper {
    fun translate(item: Item): Story {
        return Story(
            title = item.title,
            url = item.url,
            score = item.score,
            auther = item.author
        )
    }
}