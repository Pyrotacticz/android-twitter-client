package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ComposeActivity : AppCompatActivity() {
    lateinit var etTweetCompose: EditText
    lateinit var btnTweet: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etTweetCompose = findViewById(R.id.etTweetCompose)
        btnTweet = findViewById(R.id.btnTweet)

        btnTweet.setOnClickListener {
            val tweetContent = etTweetCompose.text.toString()

            if (tweetContent.isEmpty()) {
                Toast.makeText(this, "Empty tweets not allowed", Toast.LENGTH_SHORT).show()
                // snackbar display
            } else {
                if (tweetContent.length > 140) {
                    Toast.makeText(
                        this,
                        "Tweet is too long! Limit is 140 characters",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(this, tweetContent, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}