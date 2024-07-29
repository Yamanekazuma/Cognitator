package org.civictech.cognitator.ui.settings

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.material3.DrawerState
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import org.civictech.cognitator.ApplicationConstraints
import org.civictech.cognitator.MainActivity
import org.civictech.cognitator.MainApplication
import org.civictech.cognitator.R

@HiltViewModel(assistedFactory = SettingsViewModelFactory::class)
class SettingsViewModel @AssistedInject constructor(
    @Assisted private val drawerState: DrawerState
) : ViewModel() {

    fun getDrawerState(): DrawerState = drawerState

    fun popupNotification(): Boolean {
        val context = MainApplication.applicationContext()

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, ApplicationConstraints.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getString(R.string.notif_title))
            .setContentText(context.getString(R.string.notif_content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }

            notify(1, builder.build())
            return true
        }
    }

    fun showGoodIconOnStatusBar() {
    }

    fun showBadIconOnStatusBar() {
    }
}
