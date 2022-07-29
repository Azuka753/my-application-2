package com.azurebear.myapplication2.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "current_weather_table")
data class CurrentWeather(
    val code: String,
    val fxLink: String,
    @SerializedName("now")
    @Embedded(prefix = "now_")
    val nowWeather: NowWeather,
    val updateTime: String
){
    @PrimaryKey(autoGenerate = false)
    var id = 0
}