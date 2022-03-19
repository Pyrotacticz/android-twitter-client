package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.material.textfield.TextInputLayout
import okhttp3.Headers

class ComposeActivity : AppCompatActivity() {
    lateinit var etTweetWrapper: TextInputLayout
    lateinit var etTweetCompose: EditText
    lateinit var btnTweet: Button

    lateinit var client: TwitterClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etTweetWrapper = findViewById(R.id.etTweetWrapper)
        etTweetCompose = findViewById(R.id.etTweetCompose)
        btnTweet = findViewById(R.id.btnTweet)

        client = TwitterApp.getRestClient(this)

        etTweetWrapper.counterMaxLength = MAX_CHARACTER_COUNT
        etTweetCompose.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
                btnTweet.isEnabled = !(text?.length != null && text.length > MAX_CHARACTER_COUNT)
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        btnTweet.setOnClickListener {
            val tweetContent = etTweetCompose.text.toString()

            if (tweetContent.isEmpty()) {
                Toast.makeText(this, "Empty tweets not allowed", Toast.LENGTH_SHORT).show()
                // snackbar display
            } else {
                if (tweetContent.length > MAX_CHARACTER_COUNT) {
                    Toast.makeText(
                        this,
                        "Tweet is too long! Limit is 140 characters",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //Toast.makeText(this, tweetContent, Toast.LENGTH_SHORT).show()
                    client.publishTweet(tweetContent, object: JsonHttpResponseHandler() {
                        override fun onFailure(
                            statusCode: Int,
                            headers: Headers?,
                            response: String?,
                            throwable: Throwable?
                        ) {
                            Log.e(TAG, "Failed to publish tweet", throwable)
                        }

                        override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                            Log.i(TAG, "Successfully published tweet!")
                            val tweet = Tweet.fromJson(json.jsonObject)

                            val intent = Intent()
                            intent.putExtra("tweet", tweet)
                            setResult(RESULT_OK, intent)
                            finish()
                        }
                    })
                }
            }
        }
    }

    companion object {
        const val TAG = "ComposeActivity"
        const val MAX_CHARACTER_COUNT = 280
    }
}