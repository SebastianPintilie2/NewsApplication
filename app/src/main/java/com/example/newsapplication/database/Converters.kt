package com.example.newsapplication.database

import androidx.room.TypeConverter
import com.example.newsapplication.models.Source

class Converters {

    @TypeConverter
    fun fromSourceToString(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun fromStringToSource(name: String): Source {
        return Source(name, name)
    }
}