package ipca.study.gymrats

import android.text.TextUtils
import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun String.isPasswordValid()  : Boolean {
    return  this.length >= 6
}

fun  String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}


fun Date.toShortDateTime(): String {
    val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return format.format(this)
}


fun String.toDate(): Date {
    //2023-10-17T10:22:32Z
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return format.parse(this)
}