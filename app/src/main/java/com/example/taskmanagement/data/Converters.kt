package com.example.taskmanagement.data

import androidx.room.TypeConverter
import com.example.taskmanagement.data.entities.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun toJson(cryptoMarketDataModel: User?): String? {
        if (cryptoMarketDataModel == null) {
            return null
        }
        val type =
            object : TypeToken<User?>() {}.type
        return Gson().toJson(cryptoMarketDataModel, type)
    }

    @TypeConverter
    fun toDataClass(data: String?): User? {
        if (data == null) {
            return null
        }
        val type =
            object : TypeToken<User?>() {}.type
        return Gson().fromJson(data, type)
    }



//    @TypeConverter
//    fun stringToListUser(data: String?): List<User?>? {
//        if (data == null) {
//            return Collections.emptyList()
//        }
//        val listType: Type = object :
//            TypeToken<List<User?>?>() {}.type
//        return Gson().fromJson<List<User?>>(data, listType)
//    }
//
//    @TypeConverter
//    fun listUserToString(someObjects: List<User?>?): String? {
//        return Gson().toJson(someObjects)
//    }

//
//    @TypeConverter
//    fun listToJson(value: List<User>?) = Gson().toJson(value)
//
//    @TypeConverter
//    fun jsonToList(value: String) = Gson().fromJson(value, Array<User>::class.java).toList()

}