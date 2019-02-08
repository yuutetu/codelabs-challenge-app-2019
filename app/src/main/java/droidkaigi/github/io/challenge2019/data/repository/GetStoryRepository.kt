package droidkaigi.github.io.challenge2019.data.repository

import droidkaigi.github.io.challenge2019.data.api.HackerNewsApi
import droidkaigi.github.io.challenge2019.data.api.response.Item
import droidkaigi.github.io.challenge2019.domain.mapper.StoryMapper
import droidkaigi.github.io.challenge2019.domain.model.Story
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.coroutines.Continuation

class GetStoryRepository {
    class APIRequestError(): Throwable()

    // TODO: APIClientにまとめたい
    fun createRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    val retrofit = createRetrofit("https://hacker-news.firebaseio.com/v0/")
    val hackerNewsApi = retrofit.create(HackerNewsApi::class.java)

    fun getStory(id: Long): Single<Story> {
        return Single.create<Story> { emitter ->
            hackerNewsApi.getItem(id).enqueue(object : Callback<Item> {
                override fun onResponse(call: Call<Item>, response: Response<Item>) {
                    val item = response.body()
                    if (item != null) {
                        return emitter.onSuccess(StoryMapper().translate(item))
                    } else {
                        return emitter.onError(APIRequestError())
                    }
                }

                override fun onFailure(call: Call<Item>, t: Throwable) {
                    emitter.onError(t)
                }
            })
        }
    }
}