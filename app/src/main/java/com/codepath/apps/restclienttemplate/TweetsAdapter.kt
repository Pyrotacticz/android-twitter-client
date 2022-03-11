package com.codepath.apps.restclienttemplate

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.models.Tweet

class TweetsAdapter(val tweets: LiveData<PagedList<Tweet>>?) : PagedListAdapter<Tweet, TweetsAdapter.ViewHolder>(DIFF_CALLBACK) {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivProfileImage = itemView.findViewById<ImageView>(R.id.ivProfileImage)
        val tvUserName = itemView.findViewById<TextView>(R.id.tvUsername)
        val tvTweetBody = itemView.findViewById<TextView>(R.id.tvTweetBody)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.item_tweet, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet: Tweet = getItem(position) ?: return

        holder.tvUserName.text = tweet.user?.name
        holder.tvTweetBody.text = tweet.body
        Glide.with(holder.itemView).load(tweet.user?.publicImageUrl).into(holder.ivProfileImage)

    }

    // Clean all elements of the recycler
    fun clear() {
        tweets?.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(tweetList: List<Tweet>) {
        tweets?.addAll(tweetList)
        notifyDataSetChanged()
    }

    fun addMoreTweets(newTweets: PagedList<Tweet>) {
        tweets?.addAll(newTweets)
        submitList(tweets)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Tweet> = object : DiffUtil.ItemCallback<Tweet>() {
            override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
                return oldItem.body == newItem.body && oldItem.user?.name == newItem.user?.name
            }
        }
    }
}