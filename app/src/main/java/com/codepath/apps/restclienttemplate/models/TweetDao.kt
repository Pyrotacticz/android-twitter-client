package com.codepath.apps.restclienttemplate.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TweetDao {
    @Query("SELECT * FROM Tweet ORDER BY createdAt DESC")
    fun getTweets(): List<Tweet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModel(vararg tweet: Tweet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertModel(vararg user: User)

    @Query(
        "SELECT Tweet.body AS tweet_body, Tweet.createdAt as tweet_createdAt, " +
                "User.* FROM Tweet INNER JOIN User ON Tweet.userId = User.id " +
                "ORDER BY Tweet.id DESC"
    )
    fun recentItems(): List<TweetWithUser>
}