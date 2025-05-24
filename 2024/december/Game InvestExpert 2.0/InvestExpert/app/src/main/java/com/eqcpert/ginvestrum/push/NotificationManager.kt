package com.eqcpert.ginvestrum.push

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.eqcpert.ginvestrum.GameActivity
import com.eqcpert.ginvestrum.R

object NotificationManager {

    private const val NOTIFICATION_ID = 123
    private const val CHANNEL_ID      = "daily_app_channel"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name            = context.resources.getString(R.string.daily_notifications)
            val descriptionText = context.resources.getString(R.string.daily_notifications_discription)
            val importance      = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply { description = descriptionText }

            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    fun buildWithIntent(context: Context, push: Push, pendingIntent: PendingIntent? = null): Notification {
        val defaultIntent = Intent(context, GameActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val defaultPendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            defaultIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(push.icon)
            .setContentTitle(push.title)
            .setContentText(push.text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle().bigText(push.text))
            .setContentIntent(pendingIntent ?: defaultPendingIntent)
            .setAutoCancel(true)

        return builder.build()
    }

    fun build(context: Context, push: Push): Notification {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(push.icon)
            .setContentTitle(push.title)
            .setContentText(push.text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle().bigText(push.text))
            .setAutoCancel(true)

        return builder.build()
    }

    fun send(context: Context, notification: Notification) {
        val notificationManager = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

}