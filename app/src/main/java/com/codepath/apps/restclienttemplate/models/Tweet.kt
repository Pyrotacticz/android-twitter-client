package com.codepath.apps.restclienttemplate.models

import android.os.Parcelable
import org.json.JSONArray
import org.json.JSONObject
import android.text.format.DateUtils
import androidx.room.*
import kotlinx.parcelize.Parcelize
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
@Entity(foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"])])
class Tweet(
    @ColumnInfo @PrimaryKey var id: String = "",
    @ColumnInfo var body: String = "",
    @ColumnInfo var createdAt: String = "",
    @ColumnInfo var userId: String? = null,
    @Ignore var user: User? = null
) : Parcelable {

    companion object {
        fun fromJson(jsonObject: JSONObject) : Tweet {
            val tweet = Tweet()
            tweet.id = jsonObject.getString("id")
            tweet.body = jsonObject.getString("text")
            tweet.createdAt = getRelativeTimeAgo(jsonObject.getString("created_at"))
            val user: User = User.fromJson(jsonObject.getJSONObject("user"))
            tweet.userId = user.id
            tweet.user = user
            return tweet
        }

        private fun getRelativeTimeAgo(rawJsonDate: String?): String {
            val twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy"
            val sf = SimpleDateFormat(twitterFormat, Locale.ENGLISH)
            sf.isLenient = true
            var relativeDate = ""
            try {
                val dateMillis: Long = sf.parse(rawJsonDate).time
                relativeDate = DateUtils.getRelativeTimeSpanString(
                    dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS
                ).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return relativeDate
        }

        fun fromJsonArray(jsonArray: JSONArray): List<Tweet> {
            val tweets = ArrayList<Tweet>()
            for (i in 0 until jsonArray.length()) {
                tweets.add(fromJson(jsonArray.getJSONObject(i)))
            }
            return tweets
        }
    }
}