package com.codepath.apps.restclienttemplate.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.json.JSONArray
import org.json.JSONObject

@Entity
@Parcelize
class User(
    @ColumnInfo @PrimaryKey var id: String = "",
    @ColumnInfo var name: String = "",
    @ColumnInfo var screenName: String = "",
    @ColumnInfo var publicImageUrl: String = ""
) : Parcelable {

    companion object {
        fun fromJson(jsonObject: JSONObject): User {
            val user = User()
            user.id = jsonObject.getString("id")
            user.name = jsonObject.getString("name")
            user.screenName = jsonObject.getString("screen_name")
            user.publicImageUrl = jsonObject.getString("profile_image_url_https")
            return user
        }

        fun fromJsonArray(jsonArray: JSONArray): List<User> {
            val users = ArrayList<User>()
            for (i in 0 until jsonArray.length()) {
                val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                users.add(User.fromJson(jsonObject.getJSONObject("user")))
            }
            return users
        }
    }
}