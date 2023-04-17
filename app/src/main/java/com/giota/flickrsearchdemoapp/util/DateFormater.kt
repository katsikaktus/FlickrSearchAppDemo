package com.giota.flickrsearchdemoapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun formatUnixTimestamp(unixTimestamp: String): String {
    val instant = Instant.ofEpochSecond(unixTimestamp.toLong())
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy").withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}