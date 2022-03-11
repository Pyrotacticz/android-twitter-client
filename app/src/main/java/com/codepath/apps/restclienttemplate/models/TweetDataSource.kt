package com.codepath.apps.restclienttemplate.models

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import com.codepath.apps.restclienttemplate.TimelineActivity
import com.codepath.apps.restclienttemplate.TwitterClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray


class TweetDataSource(var mClient: TwitterClient) : ItemKeyedDataSource<Long, Tweet>() {

    override fun getKey(item: Tweet): Long {
        return item.id.toLong()
    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Tweet>
    ) {
        val jsonHttpResponseHandler: JsonHttpResponseHandler = createTweetHandler(callback, true)
        mClient.getTimeLine(jsonHttpResponseHandler)
    }

    private fun createTweetHandler(callback: ItemKeyedDataSource.LoadCallback<Tweet>, async: Boolean): JsonHttpResponseHandler {
        val handler: JsonHttpResponseHandler = object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.i("TweetDataSource", "onFailure $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                val tweets: List<Tweet> = Tweet.fromJsonArray(json.jsonArray)
                callback.onResult(tweets)
            }
        }

        if (async) {

        }
        return handler
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Tweet>) {
        val jsonHttpResponseHandler: JsonHttpResponseHandler = createTweetHandler(callback, false)
        mClient.getTimeLine(jsonHttpResponseHandler)
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Tweet>) {
        TODO("Not yet implemented")
    }
}