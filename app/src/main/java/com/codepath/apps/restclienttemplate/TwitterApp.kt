package com.codepath.apps.restclienttemplate

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.codepath.oauth.OAuthBaseClient
import com.facebook.stetho.Stetho

class TwitterApp : Application() {
    lateinit var twitterDatabase: TwitterDatabase

    override fun onCreate() {
        super.onCreate()
        twitterDatabase = Room.databaseBuilder(this,
            TwitterDatabase::class.java,
            TwitterDatabase.NAME)
            .fallbackToDestructiveMigration()
            .build()

        Stetho.initializeWithDefaults(this)
    }

    fun getTweetDatabase() : TwitterDatabase  {
        return twitterDatabase
    }

    companion object {
        fun getRestClient(context: Context): TwitterClient {
            return OAuthBaseClient.getInstance(TwitterClient::class.java, context) as TwitterClient
        }
    }
}