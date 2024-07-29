package org.civictech.cognitator.ui.listings

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.civictech.cognitator.R
import org.civictech.cognitator.data.checklist.CheckItemStatus
import org.civictech.cognitator.data.checklist.CheckItemWithStatus
import org.civictech.cognitator.ui.common.CustomAppBar
import org.civictech.cognitator.ui.theme.CognitatorTheme

@Composable
fun ListingsView(
    vm: ListingsViewModel = hiltViewModel()
) {
    val drawerState = vm.getDrawerState()

    ListingsViewContent(drawerState, vm.getAllItemsWithStatusMock())
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListingsViewItem(item: CheckItemWithStatus) {
    var isExpanded by remember { mutableStateOf(false) }
    var isEnable by remember { mutableStateOf(item.isEnable) }

    Column(
        modifier = Modifier.animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(
                    if (isEnable) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    }
                )
                .combinedClickable(onClick = { isExpanded = !isExpanded },
                    onLongClick = { isEnable = !isEnable }),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val iconData = if (isEnable) {
                when (item.status) {
                    CheckItemStatus.None -> Pair(Icons.Filled.Refresh, Color.Gray)
                    CheckItemStatus.Safe -> Pair(Icons.Filled.Check, Color.Green)
                    CheckItemStatus.Dangerous -> Pair(Icons.Filled.Clear, Color.Red)
                }
            } else {
                Pair(
                    ImageVector.vectorResource(id = R.drawable.baseline_notifications_off_24),
                    MaterialTheme.colorScheme.surfaceVariant
                )
            }

            Icon(
                imageVector = iconData.first,
                contentDescription = null,
                tint = iconData.second,
                modifier = Modifier
                    .padding(5.dp, 0.dp)
                    .size(32.dp)
            )

            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
            ) {
                Text("Risk Lv: ${item.risk}", fontSize = 14.sp)
                Text(
                    item.label,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            Icon(
                imageVector = if (isExpanded) {
                    Icons.Filled.KeyboardArrowUp
                } else {
                    Icons.Filled.KeyboardArrowDown
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(4.dp, 0.dp)
            )
        }

        if (isExpanded) {
            Text(
                text = item.description,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(12.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Composable
fun ListingsViewContent(drawerState: DrawerState, checkItems: List<CheckItemWithStatus>) {
    Scaffold(topBar = {
        CustomAppBar(
            drawerState = drawerState, title = stringResource(R.string.screen_listings)
        )
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(4.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            var lastCategory = ""
            items(checkItems) {
                if (lastCategory != it.category) {
                    lastCategory = it.category

                    Spacer(modifier = Modifier.height(10.dp))

                    Column(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant)) {
                        Row {
                            Spacer(modifier = Modifier.width(14.dp))

                            Text(
                                it.category,
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        HorizontalDivider(
                            thickness = 3.dp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(3.dp)
                        )
                    }
                }

                ListingsViewItem(it)
            }
        }
    }
}

@Preview
@Composable
fun PreviewLight() {
    CognitatorTheme {
        val items: List<CheckItemWithStatus> = listOf(
            CheckItemWithStatus(
                3,
                "Application",
                2,
                "Install apps from outside the App Store",
                "正規のアプリストア外からインストールされたアプリがあります。正規のアプリストア外で配布されているアプリは十分な検査が行われていないため、危険です。直ちにアンインストールしてください。",
                true,
                CheckItemStatus.None
            ), CheckItemWithStatus(
                6,
                "Application",
                1,
                "Can't found a MFA application",
                "多要素認証のためのアプリがインストールされていません。\n多要素認証を用いることで、不正ログインのリスクを大幅に減らすことができます。積極的に多要素認証を活用しましょう。",
                true,
                CheckItemStatus.Safe
            ), CheckItemWithStatus(
                5,
                "E-mail",
                1,
                "Open a link in an email without checking",
                "Eメール中のリンクのクリック前に十分な確認時間がみられません。\nEメールに含まれるURLはフィッシングサイトや危険なサイトである可能性があります。「https://～」で始まるか、正規のドメインかなどを確認したうえでクリックしてください。",
                true,
                CheckItemStatus.Dangerous
            ), CheckItemWithStatus(
                1,
                "Network",
                3,
                "Connect to free Wi-Fi frequently",
                "フリーWi-Fiを頻繁に利用しています。\nフリーWi-Fiには盗聴のリスクがあります。信頼のできる管理者の持つフリーWi-Fiにのみ接続するか、フリーWi-Fi利用中は貴重な情報は送信しないように注意してください。",
                false,
                CheckItemStatus.None
            ), CheckItemWithStatus(
                4,
                "Network",
                2,
                "Use HTTP frequently",
                "「http://～」から始まるサイトに頻繁にアクセスしています。これらのサイトへの通信は暗号化されません。\n通信内容の盗聴を防ぐため、日頃から「https://～」を使うよう意識しましょう。",
                false,
                CheckItemStatus.Safe
            ), CheckItemWithStatus(
                2,
                "System",
                3,
                "Use easy password on your smartphone",
                "使用中のスマートフォンはパスワード設定がされていないか、予測しやすいパスワードが設定されています。パスワードを簡単なものにしていると、端末紛失時の情報漏えいリスクが高まります。複雑なパスワードまたは生体認証の活用を検討してください。",
                false,
                CheckItemStatus.Dangerous
            )
        )

        ListingsViewContent(rememberDrawerState(initialValue = DrawerValue.Closed), items)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewNight() {
    PreviewLight()
}