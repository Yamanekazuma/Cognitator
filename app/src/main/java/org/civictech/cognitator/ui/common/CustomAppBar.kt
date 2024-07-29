package org.civictech.cognitator.ui.common

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import org.civictech.cognitator.ui.theme.CognitatorTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(drawerState: DrawerState?, title: String) {
    val coroutineScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary, navigationIconContentColor = MaterialTheme.colorScheme.onPrimary),
        navigationIcon = {
            drawerState?.let {
                IconButton(onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "")
                }
            }
        },
        title = { Text(text = title, fontWeight = FontWeight.Bold) }
    )
}

@Preview
@Composable
fun PreviewLight() {
    CognitatorTheme {
        CustomAppBar(rememberDrawerState(initialValue = DrawerValue.Closed), "Sample")
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNight() {
    CognitatorTheme {
        CustomAppBar(rememberDrawerState(initialValue = DrawerValue.Closed), "Sample")
    }
}