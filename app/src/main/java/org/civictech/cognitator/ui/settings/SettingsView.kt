package org.civictech.cognitator.ui.settings

import android.Manifest
import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.civictech.cognitator.R
import org.civictech.cognitator.ui.common.CustomAppBar
import org.civictech.cognitator.ui.theme.CognitatorTheme

@Composable
fun SettingsView(
    vm: SettingsViewModel = hiltViewModel()
) {
    val drawerState = vm.getDrawerState()

    SettingsViewContent(drawerState)
    DebugContent(vm)
}

@Composable
private fun SettingsLabelText(imageVector: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, fontSize = 24.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun NotificationSettingsContent() {
    var isNotify by remember { mutableStateOf(false) }
    var threshold by remember { mutableIntStateOf(100) }

    SettingsLabelText(imageVector = Icons.Filled.Notifications, text = "通知設定")

    Spacer(modifier = Modifier.height(4.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp, 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "高リスク時に通知を行う",
                modifier = Modifier
                    .weight(1f)
                    .clickable { isNotify = !isNotify },
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Checkbox(checked = isNotify, onCheckedChange = { b -> isNotify = b })
        }

        HorizontalDivider(modifier = Modifier.padding(0.dp, 8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "通知を行うリスクの閾値",
                modifier = Modifier.weight(1f),
                color = if (isNotify) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.outlineVariant
                }
            )
            TextField(
                value = "$threshold",
                onValueChange = { s -> threshold = s.toInt() },
                enabled = isNotify,
                textStyle = TextStyle(textAlign = TextAlign.End),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(rememberTextMeasurer().measure("000").size.width.dp)
            )
        }
    }
}

@Composable
private fun ColorSettingsViewContent() {
    var useSystemColor by remember { mutableStateOf(true) }
    var isDarkTheme by remember { mutableStateOf(true) }

    SettingsLabelText(ImageVector.vectorResource(id = R.drawable.baseline_palette_24), "カラー設定")

    Spacer(modifier = Modifier.height(4.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp, 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "システムのテーマ設定を使用する",
                modifier = Modifier
                    .weight(1f)
                    .clickable { useSystemColor = !useSystemColor },
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Checkbox(checked = useSystemColor, onCheckedChange = { b -> useSystemColor = b })
        }

        HorizontalDivider(modifier = Modifier.padding(0.dp, 8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "ライトテーマ", color = if (useSystemColor) {
                    MaterialTheme.colorScheme.outlineVariant
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = { b -> isDarkTheme = b },
                modifier = Modifier.weight(1f),
                enabled = !useSystemColor
            )
            Text(
                text = "ダークテーマ", color = if (useSystemColor) {
                    MaterialTheme.colorScheme.outlineVariant
                } else {
                    MaterialTheme.colorScheme.onPrimaryContainer
                }
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun DebugNotificationButton(vm: SettingsViewModel) {
    val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(
            Manifest.permission.POST_NOTIFICATIONS
        )
    } else {
        null
    }

    Button(
        onClick = {
            if (permissionState != null) {
                permissionState.launchPermissionRequest()
                if (permissionState.status.isGranted) {
                    vm.popupNotification()
                }
            } else {
                vm.popupNotification()
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Generate Notification")
    }
}

@Composable
private fun DebugGoodStatusButton(vm: SettingsViewModel) {
    Button(
        onClick = vm::showGoodIconOnStatusBar,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Show GOOD Status")
    }
}

@Composable
private fun DebugBadStatusButton(vm: SettingsViewModel) {
    Button(
        onClick = vm::showBadIconOnStatusBar,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Show BAD Status")
    }
}

@Composable
private fun DebugContent(vm: SettingsViewModel) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp, 0.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("デバッグ用ボタン")
        DebugNotificationButton(vm)
        DebugGoodStatusButton(vm)
        DebugBadStatusButton(vm)
    }
}

@Composable
private fun SettingsViewContent(drawerState: DrawerState) {
    Scaffold(topBar = {
        CustomAppBar(
            drawerState = drawerState, title = stringResource(R.string.screen_settings)
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            ColorSettingsViewContent()
            Spacer(modifier = Modifier.height(20.dp))
            NotificationSettingsContent()
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun PreviewLight() {
    CognitatorTheme {
        SettingsViewContent(rememberDrawerState(initialValue = DrawerValue.Closed))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNight() {
    PreviewLight()
}
