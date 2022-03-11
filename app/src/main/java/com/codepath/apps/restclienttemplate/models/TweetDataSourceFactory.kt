package com.codepath.apps.restclienttemplate.models

import androidx.paging.DataSource
import com.codepath.apps.restclienttemplate.TwitterClient

class TweetDataSourceFactory(var client: TwitterClient): DataSource.Factory<Long, Tweet>() {
    override fun create(): DataSource<Long, Tweet> {
        return TweetDataSource(client)
    }
}