package com.codepath.apps.restclienttemplate.models

import androidx.room.Embedded

class TweetWithUser {
    @Embedded
    var user: User? = null

    @Embedded(prefix = "tweet_")
    var tweet: Tweet? = null

    companion object {
        fun getTweetList(tweetWithUserList: List<TweetWithUser>): List<Tweet> {
            val tweets: MutableList<Tweet> = ArrayList()
            for (i in tweetWithUserList.indices) {
                val tweetWithUser = tweetWithUserList[i]
                val tweet = tweetWithUser.tweet
                tweet!!.user = tweetWithUser.user
                tweets.add(tweet)
            }
            return tweets
        }
    }
}