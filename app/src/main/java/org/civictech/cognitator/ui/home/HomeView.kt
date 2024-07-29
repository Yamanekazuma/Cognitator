package org.civictech.cognitator.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.civictech.cognitator.R
import org.civictech.cognitator.data.checklist.CheckItemView
import org.civictech.cognitator.ui.common.CustomAppBar
import org.civictech.cognitator.ui.theme.CognitatorTheme

@Composable
fun HomeView(
    vm: HomeViewModel = hiltViewModel()
) {
    val drawerState = vm.getDrawerState()
    val status = vm.check()
    val items = vm.getNotifiableItems()

    HomeViewContent(drawerState, status, items)
}

@Composable
private fun HomeViewContent(drawerState: DrawerState, status: Boolean, items: List<CheckItemView>) {
    Scaffold(
        topBar = {
            CustomAppBar(
                drawerState = drawerState,
                title = stringResource(R.string.screen_home)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(100.dp))

            Text(text = "Current status is", fontSize = 18.sp)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(0.dp, 4.dp, 0.dp, 0.dp)
            ) {
                if (status) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.Green)
                    )
                } else {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = Icons.Filled.Warning,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.Red)
                    )
                }
            }

            Text(
                text = if (status) {
                    "Safe"
                } else {
                    "Dangerous"
                }, fontSize = 20.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Serif
            )

            val riskListModifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .clip(CutCornerShape(8.dp))
                .border(2.dp, MaterialTheme.colorScheme.primaryContainer, CutCornerShape(8.dp))
            if (items.isEmpty()) {
                EmptyRiskNotifiableList(riskListModifier)
            } else {
                RiskNotifiableList(riskListModifier, items)
            }
        }
    }
}

@Composable
private fun EmptyRiskNotifiableList(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.home_green),
            fontSize = 24.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .clip(CutCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(2.dp, MaterialTheme.colorScheme.inversePrimary, CutCornerShape(8.dp))
                .padding(16.dp)
        )
    }
}

@Composable
private fun RiskNotifiableList(modifier: Modifier, checks: List<CheckItemView>) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        items(checks) { check ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.primaryContainer),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.padding(5.dp, 0.dp)
                )
                Column(modifier = Modifier.wrapContentHeight()) {
                    Text(
                        text = "Risk Lv: ${check.risk}",
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = check.label,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        check.category,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 0.dp)
                    )
                }
            }
        }
    }
}

@Preview(group = "safety")
@Composable
fun PreviewLightSafety() {
    CognitatorTheme {
        HomeViewContent(rememberDrawerState(initialValue = DrawerValue.Closed), true, emptyList())
    }
}

@Preview(group = "safety", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNightSafety() {
    PreviewLightSafety()
}

@Preview(group = "danger")
@Composable
fun PreviewLightDanger() {
    val list = listOf(
        CheckItemView(1, "Network", 3, "Connect to free Wi-Fi frequently"),
        CheckItemView(2, "System", 3, "Use easy password on your smartphone"),
        CheckItemView(3, "Application", 2, "Install apps from outside the App Store"),
        CheckItemView(4, "Network", 2, "Use HTTP frequently"),
        CheckItemView(5, "E-mail", 1, "Open a link in an email without checking"),
        CheckItemView(6, "Application", 1, "Can't found a MFA application")
    )
    CognitatorTheme {
        HomeViewContent(rememberDrawerState(initialValue = DrawerValue.Closed), false, list)
    }
}

@Preview(group = "danger", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNightDanger() {
    PreviewLightDanger()
}