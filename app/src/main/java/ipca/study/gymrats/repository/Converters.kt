package ipca.study.gymrats.repository

import androidx.room.TypeConverter
import java.util.Date

class Converters {
    @TypeConverter
    fun fromDate(value: Long): Date {
        return  Date(value)
    }

    @TypeConverter
    fun dateToLong(date: Date): Long {
        return date.time
    }
}