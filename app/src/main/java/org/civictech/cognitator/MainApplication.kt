package org.civictech.cognitator

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: MainApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(ApplicationConstraints.CHANNEL_ID, ApplicationConstraints.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = ApplicationConstraints.CHANNEL_DESCRIPTION
        }
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
